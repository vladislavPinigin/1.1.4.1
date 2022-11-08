package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        String SQL = "CREATE TABLE IF NOT EXISTS user" +
        "(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100) NOT NULL, " +
                "lastName VARCHAR(100) NOT NULL, age INT(3) NOT NULL, PRIMARY KEY (id))";
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session =  Util.getSessionFactory().openSession();
        String SQL = "DROP TABLE IF EXISTS user";
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("При удалении табоицы произошла ошибка");
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session =  Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        try {
            session.save(user);
            transaction.commit();
            System.out.println("User " + name + " успешно добавлен в таблицу");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("При создании user произошла ошибка");
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session =  Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("При удалении user произошла ошибка");
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        String SQL = "SELECT * FROM user";
        Session session =  Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> user = new ArrayList<>();
        try {
            user = session.createNativeQuery(SQL).list();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("Ошибка при возращении всех пользователей");
            transaction.rollback();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void cleanUsersTable() {
        Session session =  Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String SQL = "TRUNCATE TABLE user";
        try{
            session.createNativeQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("Ошибка");
            transaction.rollback();
        } finally {
            session.close();
        }

    }
}
