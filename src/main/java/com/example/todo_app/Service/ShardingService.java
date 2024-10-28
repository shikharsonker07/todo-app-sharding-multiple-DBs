package com.example.todo_app.Service;

import com.example.todo_app.Utils.DatabaseContextHolder;
import com.example.todo_app.Utils.DatabaseType;
import org.springframework.stereotype.Service;

@Service
public class ShardingService {
    public void setDatabaseForUserId(Long userId) {
        DatabaseType dbType = (userId % 2 == 0) ? DatabaseType.PRIMARY :
                DatabaseType.SECONDARY;
        DatabaseContextHolder.setDatabaseType(dbType);
    }

    public void clearDatabaseType() {
        DatabaseContextHolder.clear();
    }
}