package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        try (connection){
            Statement statement = connection.createStatement();
            String sql = "create table if not exists users(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "name char(30) not null, lastName char(30) not null, age smallint not null, primary key (id))";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        String SQL = "DROP TABLE IF EXISTS users";
        try (connection) {
            Statement statement = connection.createStatement();
            statement.execute(SQL);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();

        try (connection) {
            String SQL = "insert into users(name, lastname, age) values(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " успешно добавлен в таблицу");
            connection.commit();
        } catch (Exception e) {
            Assert.fail("При добавлении в таблицу User произошло исключение\n" + e);
            e.printStackTrace();
            connection.rollback();
        }

    }

    public void removeUserById(long id) throws SQLException {
        Statement statement;
        String SQL = "DELETE FROM users WHERE id = id;";
        Connection connection = Util.getConnection();

        try (connection) {
            statement = connection.createStatement();
            statement.executeUpdate(SQL);

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        Connection connection = Util.getConnection();

        try (connection) {
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String SQL = "TRUNCATE TABLE users";
        Connection connection = Util.getConnection();


        try (connection) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }

    }
}
