package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD_Abonnement<T> {
    void insertAbonnement(T t) throws SQLException;
    void updateAbonnement(T t) throws SQLException;
    void deleteAbonnement(T t) throws SQLException;
    List<T> selectAllAbonnements() throws SQLException;
}
