package com.example.todo_app.Utils;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class IDGenerator {

    Set<Integer> idsGenerated;

    IDGenerator() {
        idsGenerated = new HashSet<>();
    }

    public int getID() {
        Random rand = new Random();
        int id = rand.nextInt(1000);
        while (idsGenerated.contains(id)) {
            id = rand.nextInt(1000);
        }
        return id;
    }
}
