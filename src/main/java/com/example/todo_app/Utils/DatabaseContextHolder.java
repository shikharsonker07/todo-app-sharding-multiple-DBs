package com.example.todo_app.Utils;

public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType databaseType) {
        contextHolder.set(databaseType);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
