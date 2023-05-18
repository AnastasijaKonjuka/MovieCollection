package com.moviecollection.repositories;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseRepository {

    private Environment env;
    private static Connection connection;
    public DatabaseRepository(Environment env) {
        this.env=env;
        this.setupDatabase();
    }

    private void setupDatabase() {
        try {
            String userName = env.getProperty("database.username");
            String password = env.getProperty("database.password");
            String connectionUrl = env.getProperty("database.url");
            System.out.println(userName);
            System.out.println(password);
            System.out.println(connectionUrl);
            this.connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException exception) {
            exception.printStackTrace();

        }
    }
    public Connection getConnection() {
        return connection;
    }
}


