package com.example.todo_app.Utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("Call Intercepted in ShardingRouting");
        return DatabaseContextHolder.getDatabaseType();
    }
}
