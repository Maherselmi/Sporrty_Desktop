package services;

import models.cours;
import models.programme;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicesProgramme {
    private Connection cnx;

    public servicesProgramme() {

        cnx = DBConnection.getInstance().getCnx();

    }

    public void insertOneP(programme programme) throws SQLException {
        String req = "INSERT INTO programme( nom, description, duree) VALUES (?,?,?)";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, programme.getNom());
            ps.setString(2, programme.getDescription());
            ps.setInt(3, programme.getDuree());
            ps.executeUpdate();

            System.out.println("Programme inséré avec succès...");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion du programme: " + ex.getMessage());
            throw ex; // Relancer l'exception pour qu'elle puisse être traitée ailleurs si nécessaire
        }
    }

    public void updateOneP(programme programme) throws SQLException {

        String req = "UPDATE programme SET nom=?, description=?, duree=? WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, programme.getNom());
            ps.setString(2, programme.getDescription());
            ps.setInt(3, programme.getDuree());
            ps.setInt(4,programme.getId());
            ps.executeUpdate();
            System.out.println("programme modifié avec succès...");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du programme: " + ex.getMessage());
            throw ex; // Relancer l'exception pour qu'elle puisse être traitée ailleurs si nécessaire
        }
    }



    public void deleteOneP(programme programme) throws SQLException {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM programme WHERE id = "+programme.getId()+"";
            st.executeUpdate(req);
            System.out.println("programme supprimè  avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public List<programme> selectAll() throws SQLException {
        List<programme> programmesList = new ArrayList<>();

        String req = "SELECT * FROM programme";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            programme p = new programme();
            p.setId(rs.getInt(1));
            p.setNom(rs.getString(2));
            p.setDescription(rs.getString(3));
            p.setDuree(rs.getInt(4));


            programmesList.add(p);
        }

        return programmesList;
    }
    public programme selectById(int id) throws SQLException {
        String req = "SELECT * FROM programme WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                programme p = new programme();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setDuree(rs.getInt(4));
                return p;
            }
        }
        return null; // Return null if no programme found with the given ID
    }

    public programme selectByName(String name) throws SQLException {
        String req = "SELECT * FROM programme WHERE nom LIKE ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                programme p = new programme();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setDuree(rs.getInt(4));
                return p;
            }
        }
        return null; // Return null if no programme found with the given name
    }



    public List<programme> searchByTitre(String titre) throws SQLException {
        List<programme>programmes = new ArrayList<>();
        String req = "SELECT * FROM programme WHERE LOWER(nom) LIKE LOWER(?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + titre + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
         programme p = new programme();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setDuree(rs.getInt("duree"));
            p.setDescription(rs.getString("description"));

           programmes.add(p);
        }
        return programmes;
    }

}

