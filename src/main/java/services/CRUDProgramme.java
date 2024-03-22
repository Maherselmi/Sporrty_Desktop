package services;

import java.sql.SQLException;
import java.util.List;


public interface CRUDProgramme <T>{
    void insertOneP(T t) throws SQLException;
    void updateOneP(T t) throws SQLException;

    void deleteOneP(T t) throws SQLException;

    List<T> selectAll() throws SQLException;
}