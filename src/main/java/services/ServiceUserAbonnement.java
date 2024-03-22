package services;

import models.Abonnement;
import models.Role;
import models.User;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUserAbonnement {
    private Connection cnx;

    public ServiceUserAbonnement() {
        cnx = DBConnection.getInstance().getCnx();
    }
    public List<User> getUsersWithAbonnements() throws SQLException {
        List<User> usersWithAbonnements = new ArrayList<>();
        String req = "SELECT DISTINCT u.* FROM `user` u JOIN `abonnement_utilisateur` au ON u.id = au.id_utilisateur";
        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(Role.valueOf(rs.getString("role"))); // Convertir la chaîne en valeur d'énumération
            usersWithAbonnements.add(user);
        }
        return usersWithAbonnements;
    }/*
    public List<Abonnement> selectAbonnementsForUser(int userId) throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String req = "SELECT a.* FROM abonnement a JOIN abonnement_utilisateur au ON a.id = au.id_abonnement WHERE au.id_utilisateur = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Abonnement abonnement = new Abonnement();
                    abonnement.setId(rs.getInt("id"));
                    abonnement.setType(rs.getString("type"));
                    abonnement.setPrix(rs.getDouble("prix"));
                    abonnements.add(abonnement);
                }
            }
        }
        return abonnements;
    }*/
    public List<Abonnement> selectAbonnementsForUser(int userId) throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String req = "SELECT a.* FROM abonnement a JOIN abonnement_utilisateur au ON a.id = au.id_abonnement WHERE au.id_utilisateur = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Abonnement abonnement = new Abonnement();
                    abonnement.setId(rs.getInt("id"));
                    abonnement.setType(rs.getString("type"));
                    abonnement.setPrix(rs.getDouble("prix"));
                    abonnements.add(abonnement);
                }
            }
        }

        // Afficher les abonnements récupérés
        for (Abonnement abonnement : abonnements) {
            System.out.println("Abonnement: " + abonnement.getType() + " - " + abonnement.getPrix());
        }

        return abonnements;
    }

    public List<Abonnement> getAbonnementsForUser(int userId) throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String req = "SELECT a.* FROM abonnement a JOIN abonnement_utilisateur au ON a.id = au.id_abonnement WHERE au.id_utilisateur = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Abonnement abonnement = new Abonnement();
            abonnement.setId(rs.getInt("id"));
            abonnement.setType(rs.getString("type"));
            // Autres attributs d'abonnement à initialiser si nécessaire
            abonnements.add(abonnement);
        }
        return abonnements;
    }
    public void deleteAbonnementUser(int userId, int abonnementId) throws SQLException {
        String req = "DELETE FROM abonnement_utilisateur WHERE id_utilisateur = ? AND id_abonnement = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            ps.setInt(2, abonnementId);
            ps.executeUpdate();
        }
    }
    public int getAbonnementIdForUser(int userId) throws SQLException {
        int abonnementId = -1; // Valeur par défaut si aucun abonnement n'est trouvé

        String req = "SELECT id_abonnement FROM abonnement_utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    abonnementId = rs.getInt("id_abonnement");
                }
            }
        }

        return abonnementId;
    }
    public void deleteAbonnementUserUser(int userId) throws SQLException {
        String req = "DELETE FROM abonnement_utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
    public void deleteAbonnementUserAbonnement(int userId) throws SQLException {
        String req = "DELETE FROM abonnement_utilisateur WHERE id_abonnement= ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
