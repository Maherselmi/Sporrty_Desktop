package services;

import models.reclamation;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements CRUD_EVENTSREC<reclamation> {
    private Connection cnx;

    public ServiceReclamation() {
        cnx = DBConnection.getInstance().getCnx();
    }

   /* @Override
    public void insertOne(reclamation reclamation) throws SQLException {
        String req = "INSERT INTO reclamation (nom,id_user,description,date, nbr_etoile,statut) VALUES"+" (?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, reclamation.getType());
        ps.setInt(2,reclamation.getIdUser());
        ps.setString(3, reclamation.getDescriRec());
      ps.setDate(4, reclamation.getDate());
        ps.setString(4, reclamation.getNbrEt());
        ps.setString(5, reclamation.getStatut());


        ps.executeUpdate();
    }
    */
@Override
    public void insertOne(reclamation reclamation) throws SQLException {
        String req = "INSERT INTO reclamation (nom,id_user,description,date,nbr_etoile,statut) VALUES"+" (?,?,?,NOW(),?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, reclamation.getType());
        ps.setInt(2,reclamation.getIdUser().getId());
        ps.setString(3, reclamation.getDescriRec());
        ps.setString(4, reclamation.getNbrEt());
        ps.setString(5, reclamation.getStatut());


        ps.executeUpdate();
    }



    @Override
    public void updateOne(reclamation reclamation) throws SQLException {
        try {
            String req = "UPDATE reclamation SET reponse=?, statut=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            // Définition des valeurs des paramètres de substitution
            ps.setString(1, reclamation.getReponse());
            ps.setString(2, reclamation.getStatut());
            ps.setInt(3, reclamation.getIdRec());
            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("Réclamation modifiée avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteOne(reclamation reclamation) throws SQLException {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM reclamation WHERE id = " + reclamation.getIdRec() + "";
            st.executeUpdate(req);
            System.out.println("reclamation supperimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public List<reclamation> selectAll() throws SQLException {
       List<reclamation> reclamationList = new ArrayList<>();

        String req = "SELECT * FROM reclamation";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            reclamation r = new reclamation();

            // Utilisation des index de colonne à partir de 1
            r.setIdRec(rs.getInt(1));
            r.setType(rs.getString(2));
            ServiceUser serviceUser = new ServiceUser();
            r.setIdUser(serviceUser.selectById(rs.getInt(3)));
            r.setDescriRec(rs.getString(4));
            r.setDate(rs.getDate(5));
            r.setNbrEt(rs.getString(6));
            r.setReponse(rs.getString(7));
            r.setStatut(rs.getString(8));

            reclamationList.add(r);
        }
        return reclamationList;
    }

    public List<reclamation> searchByTState(String stat) throws SQLException {
        List<reclamation> rec = new ArrayList<>();
        String req = "SELECT * FROM reclamation WHERE LOWER(statut) LIKE LOWER(?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + stat + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
           reclamation r = new reclamation();
            r.setIdRec(rs.getInt(1));
            r.setType(rs.getString(2));
            ServiceUser serviceUser = new ServiceUser();
            r.setIdUser(serviceUser.selectById(rs.getInt(3)));
            r.setDescriRec(rs.getString(4));
            r.setDate(rs.getDate(5));
            r.setNbrEt(rs.getString(6));
            r.setReponse(rs.getString(7));
            r.setStatut(rs.getString(8));
            rec.add(r);
        }
        return rec;
    }

    public List<reclamation> searchBy(String filterBy, String search) throws SQLException {
        List<reclamation> rec = new ArrayList<>();
        String req = "SELECT * FROM `reclamation` WHERE `"+filterBy+"` LIKE '%"+search+"%'";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            reclamation r = new reclamation();
            r.setIdRec(rs.getInt(1));
            r.setType(rs.getString(2));
            ServiceUser serviceUser = new ServiceUser();
            r.setIdUser(serviceUser.selectById(rs.getInt(3)));
            r.setDescriRec(rs.getString(4));
            r.setDate(rs.getDate(5));
            r.setNbrEt(rs.getString(6));
            r.setReponse(rs.getString(7));
            r.setStatut(rs.getString(8));
            rec.add(r);
        }
        return rec;
    }

    public List<reclamation> selectByCategory(String category) throws SQLException {
        List<reclamation> reclamations = new ArrayList<>();

        // Écrire votre requête SQL pour sélectionner les réclamations par catégorie
        String query = "SELECT * FROM reclamation WHERE category = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcourir les résultats et créer des objets reclamation
            while (resultSet.next()) {
                reclamation rec = new reclamation();
                // Peupler l'objet reclamation avec les données de la base de données
                rec.setIdRec(resultSet.getInt("id"));
                rec.setType(resultSet.getString("type"));
                rec.setNomComplet(resultSet.getString("nomComplet"));
                rec.setDescriRec(resultSet.getString("descriRec"));
                // Set other attributes as needed

                // Ajouter la réclamation à la liste
                reclamations.add(rec);
            }
        }

        return reclamations;
    }

    public List<reclamation> selectByUserId(int id_user) throws SQLException {
        List<reclamation> reclamations = new ArrayList<>();

        // Écrire votre requête SQL pour sélectionner les réclamations par catégorie
        String query = "SELECT * FROM reclamation WHERE id_user = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id_user);
            ResultSet rs = preparedStatement.executeQuery();

            // Parcourir les résultats et créer des objets reclamation
            while (rs.next()) {
                reclamation r = new reclamation();
                // Peupler l'objet reclamation avec les données de la base de données

                r.setIdRec(rs.getInt(1));
                r.setType(rs.getString(2));
                ServiceUser serviceUser = new ServiceUser();
                r.setIdUser(serviceUser.selectById(rs.getInt(3)));
                r.setDescriRec(rs.getString(4));
                r.setDate(rs.getDate(5));
                r.setNbrEt(rs.getString(6));
                r.setReponse(rs.getString(7));
                r.setStatut(rs.getString(8));
                // Set other attributes as needed

                // Ajouter la réclamation à la liste
                reclamations.add(r);
            }
        }

        return reclamations;
    }
    public List<reclamation> selectByStatus(String status) throws SQLException {
        List<reclamation> reclamations = new ArrayList<>();

        // Écrire votre requête SQL pour sélectionner les réclamations par statut
        String query = "SELECT * FROM reclamation WHERE statut = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Parcourir les résultats et créer des objets reclamation
                while (resultSet.next()) {
                    reclamation rec = new reclamation();
                    // Peupler l'objet reclamation avec les données de la base de données
                    rec.setIdRec(resultSet.getInt("id")); // Correction du nom de la colonne
                    rec.setType(resultSet.getString("type"));
                    rec.setNomComplet(resultSet.getString("nomComplet"));
                    rec.setDescriRec(resultSet.getString("descriRec"));
                    // Set other attributes as needed

                    // Ajouter la réclamation à la liste
                    reclamations.add(rec);
                }
            }
        } catch (SQLException e) {
            // Gérer l'exception SQL
            e.printStackTrace(); // Affichez l'erreur pour le suivi
            throw e; // Propagez l'exception pour que le gestionnaire d'erreurs puisse la traiter
        }

        return reclamations;
}}
