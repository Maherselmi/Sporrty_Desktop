package services;

import java.sql.SQLException;
import java.util.List;

public interface Crud_serviceCabine<T> {
    void insert(T t) throws SQLException;
    void update(T t) throws SQLException;
    void Delete(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
}
