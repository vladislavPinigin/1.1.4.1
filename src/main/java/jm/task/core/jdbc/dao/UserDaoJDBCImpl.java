package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS usersTable" +
                "(id INT primary key auto_increment," +
                "name VARCHAR(50)," +
                "lastname VARCHAR(50)," +
                "age TINYINT)";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate(SQL);
            Savepoint savepoint = connection.setSavepoint();
            connection.rollback(savepoint);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS usersTable";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
            Savepoint savepoint = connection.setSavepoint();
            connection.rollback(savepoint);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO usersTable (name, lastname, age)VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            Savepoint savepoint = connection.setSavepoint();
            connection.rollback(savepoint);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM usersTable WHERE id = ?";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            Savepoint savepoint = connection.setSavepoint();
            connection.rollback(savepoint);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM usersTable";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(SQL);


            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            Savepoint savepoint = connection.setSavepoint();
            connection.rollback(savepoint);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE usersTable";
        try (Connection connection = Util.getConnection()) {
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.executeUpdate();
            Savepoint savepoint = connection.setSavepoint();
            connection.rollback(savepoint);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
