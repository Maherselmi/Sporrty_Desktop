package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD_User<T> {
    void insertUser (T t) throws SQLException;
    void updateUser(T t) throws SQLException;
    void displayAllUser();
    void deleteUser(T t) throws SQLException;
    List<T> selectAll()throws SQLException;
}
