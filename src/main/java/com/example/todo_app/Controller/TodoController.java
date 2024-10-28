package com.example.todo_app.Controller;

import com.example.todo_app.Model.Todo;
import com.example.todo_app.Model.User;
import com.example.todo_app.Repository.TodoRepository;
import com.example.todo_app.Repository.UserRepository;
import com.example.todo_app.Service.ShardingService;
import com.example.todo_app.Utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShardingService shardingService;
    @Autowired
    private IDGenerator idGenerator;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.saveId(idGenerator.getID());
        shardingService.setDatabaseForUserId(user.getId());

        System.out.println("Create User --> Repository Call");
        User savedUser = userRepository.save(user);
        try {
            return savedUser;
        } finally {
            shardingService.clearDatabaseType();
        }
    }

    @PostMapping("/users/{userId}/todos")
    public ResponseEntity<Todo> createTodo(@PathVariable Long userId, @RequestBody Todo todo) {
        shardingService.setDatabaseForUserId(userId);
        System.out.println("Create Todo --> Repository Call");
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        todo.setUser(user);
                        return ResponseEntity.ok(todoRepository.save(todo));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } finally {
            shardingService.clearDatabaseType();
        }
    }

    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<?> getUserTodos(@PathVariable Long userId) {
        shardingService.setDatabaseForUserId(userId);
        try {
            System.out.println("Get Todo --> Repository Call");
            return userRepository.findById(userId)
                    .map(user -> ResponseEntity.ok(user.getTodos()))
                    .orElse(ResponseEntity.notFound().build());
        } finally {
            shardingService.clearDatabaseType();
        }
    }
}
