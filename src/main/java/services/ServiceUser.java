package services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.Role;
import models.User;
import utils.DBConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceUser implements CRUD_User <User>{
    private Connection cnx;
    public  ServiceUser() {
        cnx = DBConnection.getInstance().getCnx();
    }
    // Méthode pour hacher un mot de passe avec SHA-256
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    @Override
    public void insertUser(User u) throws SQLException {
        String checkReq = "SELECT COUNT(*) FROM user WHERE email=?";
        PreparedStatement checkPs = cnx.prepareStatement(checkReq);
        checkPs.setString(1, u.getEmail());
        ResultSet rs = checkPs.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count > 0) {
            System.out.println("L'utilisateur avec cet email existe déjà. Insertion annulée.");
            return; // Quitter la méthode sans effectuer l'insertion
        }

        System.out.println("Insertion de l'utilisateur : " + u);

        String req = "INSERT INTO `user` (`nom`, `prenom`, `email`, `password`, `role`,`image_user`) VALUES (?, ?, ?, ?, ?,?)";

        try {
            // Utilisation de PreparedStatement pour éviter les problèmes de sécurité et les erreurs de syntaxe SQL
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            String hashedPassword = hashPassword(u.getPassword());
            ps.setString(4, hashedPassword);
            ps.setString(5, u.getRole().toString()); // Conversion de l'énumération Role en chaîne de caractères
            ps.setString(6, u.getImage_user());
            ps.executeUpdate();
            System.out.println("Insertion réussie.");
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {

        String req = "UPDATE user SET nom=?, prenom=?, email=?, password=? WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, user.getNom());
        ps.setString(2, user.getPrenom());
        ps.setString(3, user.getEmail());
        String hashedPassword = null;
        try {
            hashedPassword = hashPassword(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        ps.setString(4, hashedPassword);

        ps.setInt(5, user.getId());
        ps.executeUpdate();

    }
    @Override
    public void displayAllUser() {
        try {
            List<User> users = selectAll();
            if (users.isEmpty()) {
                System.out.println("Aucune personne trouvée dans la base de données.");
            } else {
                System.out.println("Liste des personnes :");
                for (User user : users) {
                    System.out.println( user.getNom() + ", " + user.getPrenom() + ", " + user.getEmail() + ", "+user.getPassword() + user.getRole());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }
    }

    @Override
    public List<User> selectAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        String req = "SELECT * FROM `user`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setRole(Role.valueOf(rs.getString("role")));
            u.setImage_user(rs.getString("image_user"));

            userList.add(u);
        }
        return userList;
    }

        @Override
    public void deleteUser(User u) throws SQLException
    {
        String selectQuery = "SELECT * FROM user WHERE id=?";
        PreparedStatement selectStatement = cnx.prepareStatement(selectQuery);
        selectStatement.setInt(1, u.getId());
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) { // Si une personne avec cet ID est trouvée
            // Supprimer la personne de la base de données
            String deleteQuery = "DELETE FROM user WHERE id=?";
            PreparedStatement deleteStatement = cnx.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, u.getId());
            deleteStatement.executeUpdate();
            System.out.println("Personne avec l'ID " + u.getId() + " a été supprimée avec succès.");
        }
        else
        {
            // Afficher un message indiquant que la personne n'a pas été trouvée
            System.out.println("Aucune personne avec l'ID " + u.getId() + " n'a été trouvée dans la base de données.");
        }
    }
    public List<User> selectByRole(Role role) throws SQLException {
        List<User> userList = new ArrayList<>();
        String req = "SELECT * FROM `user` WHERE role=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, role.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setRole(Role.valueOf(rs.getString("role")));
            u.setImage_user(rs.getString("image_user"));

            userList.add(u);
        }
        return userList;
    }


    public  User selectById(int id) throws SQLException {
        String req = "SELECT * FROM `user` WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setRole(Role.valueOf(rs.getString("role")));
            u.setImage_user(rs.getString("image_user"));

            return u;
        } else {
            return null; // Retourner null si aucun utilisateur n'est trouvé avec cet ID
        }
    }

    public void deleteCompte(User u) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ServiceUser serviceUser = new ServiceUser();
                serviceUser.deleteUser(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
    public List<Integer> selectAbonnementIdsForUser(int userId) throws SQLException {
        List<Integer> abonnementIds = new ArrayList<>();
        String req = "SELECT id_abonnement FROM abonnement_utilisateur WHERE id_utilisateur=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int abonnementId = rs.getInt("id_abonnement");
            abonnementIds.add(abonnementId);
        }
        return abonnementIds;
    }
    public boolean userExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM user WHERE email=?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    public void updatePassword(int userId, String newPassword) throws SQLException {
        // Récupérer l'utilisateur à partir de l'ID
        User existingUser = selectById(userId);

        // Mettre à jour le mot de passe de l'utilisateur
        existingUser.setPassword(newPassword);
        updateUser(existingUser);
    }
    public User selectByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DBConnection.getInstance().getCnx();
            statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("nom");
                String lastName = resultSet.getString("prenom");
                String userEmail = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String image_user = resultSet.getString("image_user");

                user = new User(id,firstName, lastName, userEmail, password, Role.valueOf(role),image_user);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return user;
    }
    public List<User> searchByName(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try {
            connection = DBConnection.getInstance().getCnx();
            statement = connection.prepareStatement("SELECT * FROM user WHERE LOWER(nom) LIKE LOWER(?)");
            statement.setString(1, name.toLowerCase() + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("nom");
                String lastName = resultSet.getString("prenom");
                String userEmail = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String image_user = resultSet.getString("image_user");

                User user = new User(id,firstName, lastName, userEmail, password, Role.valueOf(role),image_user);
                userList.add(user);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return userList;
    }


}
