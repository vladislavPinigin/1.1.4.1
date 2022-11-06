package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Petr", "Ivanov", (byte) 40);
        userService.saveUser("Ivan", "Ivanov", (byte) 30);
        userService.saveUser("Ivan", "Petrov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 10);

        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
