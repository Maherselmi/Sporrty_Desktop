package services;

import models.cours;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicesCours implements CRUDCours<cours> {

    private static servicesCours instance;

    private Connection cnx;
    private List<cours> eventsForMonth;

    public servicesCours() {
        cnx = DBConnection.getInstance().getCnx();
    }


    // Autres méthodes de la classe

    public static servicesCours getInstance() {
        if (instance == null) {
            instance = new servicesCours();
        }
        return instance;
    }


    //@Override


    public void insertOne(cours cours) throws SQLException {
        String req = "INSERT INTO cours(nom, coach, jours, duree, type, prix,id_programme,image,lienVideo) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, cours.getNom());
            ps.setString(2, cours.getCoach());
            ps.setString(3, cours.getJours());
            ps.setInt(4, cours.getDuree());
            ps.setString(5, cours.getType());
            ps.setInt(6, cours.getPrix());
            ps.setInt(7, cours.getId_programme().getId());
            ps.setString(8, cours.getImage());
            ps.setString(9, cours.getLienVideo());
            ps.executeUpdate();
            System.out.println("Cours inséré avec succès...");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion du cours: " + ex.getMessage());
            throw ex; // Relancer l'exception pour qu'elle puisse être traitée ailleurs si nécessaire
        }
    }

    public void updateOne(cours cours) throws SQLException {
        String req = "UPDATE cours SET nom=?, coach=?, jours=?  , duree=? , type=?, prix=? , id_programme=? , image=? , lienVideo= ? WHERE id_cours=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, cours.getNom());
            ps.setString(2, cours.getCoach());
            ps.setString(3, cours.getJours());
            ps.setInt(4, cours.getDuree());
            ps.setString(5, cours.getType());
            ps.setInt(6, cours.getPrix());
            ps.setInt(7, cours.getId_programme().getId());
            ps.setString(8, cours.getImage());
            ps.setString(9, cours.getImage());
            ps.setInt(10, cours.getId_cours());


            ps.executeUpdate();
            System.out.println("Cours modifié avec succès...");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du cours: " + ex.getMessage());
            throw ex; // Relancer l'exception pour qu'elle puisse être traitée ailleurs si nécessaire
        }
    }


    @Override
    public void deleteOne(cours cours) throws SQLException {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM cours WHERE id_cours = " + cours.getId_cours() + "";
            st.executeUpdate(req);
            System.out.println("cours supprimer  avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public List<cours> selectAll() throws SQLException {
        List<cours> coursList = new ArrayList<>();

        String req = "SELECT * FROM cours";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                cours e = new cours();
                e.setId_cours(rs.getInt("id_cours"));
                e.setNom(rs.getString("nom"));
                e.setCoach(rs.getString("coach"));
                e.setJours(rs.getString("jours"));
                e.setDuree(rs.getInt("duree"));
                e.setType(rs.getString("type"));
                e.setPrix(rs.getInt("prix"));
                servicesProgramme servicesProgramme = new servicesProgramme();

                e.setId_programme(servicesProgramme.selectById(rs.getInt("id_programme")));
                e.setImage(rs.getString("image"));
                e.setLienVideo(rs.getString("lienVideo"));


                coursList.add(e);
            }
        }

        return coursList;
    }



        // Méthode pour rechercher les cours par nom


    public List<cours> searchByNom(String nom) throws SQLException {
        List<cours> coursList = new ArrayList<>();
        String req = "SELECT * FROM cours WHERE LOWER(nom) LIKE LOWER(?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + nom + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
            cours s = new cours();
            s.setId_cours(rs.getInt(("id_cours")));
            s.setNom(rs.getString(("nom")));

            s.setCoach(rs.getString(("coach")));
            s.setJours(rs.getString(("jours")));
            s.setDuree(rs.getInt(("duree")));
            s.setType(rs.getString(("type")));
            s.setPrix(rs.getInt(("prix")));
            servicesProgramme servicesProgramme = new servicesProgramme();

            s.setId_programme(servicesProgramme.selectById(rs.getInt("id_programme")));
            s.setImage(rs.getString("image"));
            s.setLienVideo(rs.getString("lienVideo"));
            coursList.add(s);


        }

        return coursList;
    }


    public List<cours> searchByTitre(String titre) throws SQLException {
        List<cours> cours = new ArrayList<>();
        String req = "SELECT * FROM cours WHERE LOWER(nom) LIKE LOWER(?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + titre + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
        cours c = new cours();
            c.setId_cours(rs.getInt("id_cours"));
            c.setNom(rs.getString("nom"));
            c.setCoach(rs.getString("coach"));
            c.setJours(rs.getString("jours"));
            c.setDuree(rs.getInt("duree"));
            c.setType(rs.getString("type"));
            c.setPrix(rs.getInt("prix"));
            servicesProgramme servicesProgramme = new servicesProgramme();

            c.setId_programme(servicesProgramme.selectById(rs.getInt("id_programme")));
            cours.add(c);
        }
        return cours;
    }


    }





