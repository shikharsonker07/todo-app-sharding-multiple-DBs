package com.example.todo_app.Repository;

import com.example.todo_app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
