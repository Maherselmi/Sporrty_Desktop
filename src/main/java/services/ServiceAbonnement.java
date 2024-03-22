package services;

import models.Abonnement;

import models.User;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceAbonnement implements CRUD_Abonnement<Abonnement> {
    private final DBConnection dbConnection;

    public ServiceAbonnement() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public void insertAbonnement(Abonnement abonnement) throws SQLException {
        String req = "INSERT INTO abonnement (prix, type, description) VALUES (?, ?, ?)";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setDouble(1, abonnement.getPrix());
        ps.setString(2, abonnement.getType().toString());
        ps.setString(3, abonnement.getDescription());
        // ps.setInt(4, abonnement.getIdUser());
        ps.executeUpdate();
    }


    @Override
    public void updateAbonnement(Abonnement abonnement) throws SQLException {
        String req = "UPDATE abonnement SET prix=?, type=?, description=? WHERE id=?";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setDouble(1, abonnement.getPrix());
        ps.setString(2, abonnement.getType().toString());
        ps.setString(3, abonnement.getDescription());
        ps.setInt(4, abonnement.getId());
        ps.executeUpdate();
    }




    @Override
    public void deleteAbonnement(Abonnement abonnement) throws SQLException {
        String req = "DELETE FROM abonnement WHERE id=?";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setInt(1, abonnement.getId());
        ps.executeUpdate();
    }

    public List<Abonnement> selectAllAbonnements() throws SQLException {
        List<Abonnement> abonnementList = new ArrayList<>();
        String req = "SELECT * FROM abonnement";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            double prix = rs.getDouble("prix");
            String type = rs.getString("type");
            String description = rs.getString("description"); // Ajout de la récupération de la description
            Abonnement abonnement = new Abonnement(id, prix, type, description);
            abonnementList.add(abonnement);
        }
        return abonnementList;
    }

    public void displayAllAbonnements() throws SQLException {
        List<Abonnement> abonnementList = selectAllAbonnements();
        for (Abonnement abonnement : abonnementList) {
            System.out.println(abonnement); // Ou toute autre logique d'affichage que vous souhaitez
        }
    }

   /* public void inscrireUser(Abonnement abonnement, User user) throws SQLException {
        String req = "INSERT INTO abonnement (prix, type, description, id_user) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setDouble(1, abonnement.getPrix());
        ps.setString(2, abonnement.getType().toString());
        ps.setString(3, abonnement.getDescription());
        ps.setInt(4, user.getId());
        ps.executeUpdate();
    }*/

   public void inscrireUser(Abonnement abonnement, User user) throws SQLException {
       String req = "INSERT INTO abonnement_utilisateur (id_abonnement, id_utilisateur) VALUES (?, ?)";
       PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
       ps.setInt(1, abonnement.getId());
       ps.setInt(2, user.getId());
       ps.executeUpdate();
   }


    public User selectById(int id) throws SQLException {
        String req = "SELECT * FROM utilisateur WHERE id=?";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            // Définir d'autres attributs de l'utilisateur si nécessaire
            return user;
        }
        return null; // Retourner null si aucun utilisateur trouvé avec cet ID
    }

    public List<String> selectAllTypesAbonnements() throws SQLException {
        List<String> typesAbonnements = new ArrayList<>();
        String req = "SELECT DISTINCT type FROM abonnement";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            typesAbonnements.add(rs.getString("type"));
        }
        return typesAbonnements;
    }
    public Abonnement selectAbonnementByType(String type) throws SQLException {
        String req = "SELECT * FROM abonnement WHERE type=?";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            double prix = rs.getDouble("prix");
            String description = rs.getString("description");
            return new Abonnement(id, prix, type, description);
        }
        return null;
    }

    public Abonnement selectAbonnementById(int abonnementId) throws SQLException {
        String req = "SELECT * FROM abonnement WHERE id=?";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setInt(1, abonnementId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double prix = rs.getDouble("prix");
            String type = rs.getString("type");
            String description = rs.getString("description");
            return new Abonnement(abonnementId, prix, type, description);
        }
        return null;
    }
    public boolean typeExists(String type) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM abonnement WHERE type=?";
        try (PreparedStatement statement = dbConnection.getCnx().prepareStatement(query)) {
            statement.setString(1, type);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }
    public boolean userHasAbonnement(User user, Abonnement abonnement) throws SQLException {
        String req = "SELECT * FROM abonnement_utilisateur WHERE id_utilisateur=? AND id_abonnement=?";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setInt(1, user.getId());
        ps.setInt(2, abonnement.getId());
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public List<Abonnement> selectAbonnementsByType(String type) throws SQLException {
        List<Abonnement> abonnementList = new ArrayList<>();
        String req = "SELECT * FROM abonnement WHERE LOWER(type) LIKE LOWER(?)";
        PreparedStatement ps = dbConnection.getCnx().prepareStatement(req);
        ps.setString(1, type.toLowerCase() + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            double prix = rs.getDouble("prix");
            String description = rs.getString("description");
            Abonnement abonnement = new Abonnement(id, prix, type, description);
            abonnementList.add(abonnement);
        }
        return abonnementList;
    }

}
