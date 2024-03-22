package services;

import models.Participation;
import models.User;
import models.evenements;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceParticipation  {
    private Connection cnx;

    public ServiceParticipation() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void insertOne(Participation par) throws SQLException {

        String req = "INSERT INTO participation (user , event) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, par.getUser().getId());
        ps.setInt(2,par.getEvenement().getId());

        ps.executeUpdate();
    }
/*
    public List<Participation> selectAll() throws SQLException {
        List<Participation> participations = new ArrayList<>();

        String req = "SELECT u.nom AS user_nom, e.nom_event  AS event_nom FROM participation p " +
                "JOIN user u ON p.user = u.id " +
                "JOIN evenements e ON p.event = e.id_event ";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Participation r = new Participation();*/

            // Utilisation des index de colonne à partir de 1
          //  r.setId(rs.getInt(1));

           // ServiceUser serviceUser = new ServiceUser();
           // r.setUser(serviceUser.selectById(rs.getInt(2)));
          //  ServiceEvenement se = new ServiceEvenement();
          //  r.setEvenement(se.selectByIdEV(rs.getInt(3)));


           // participations.add(r);

           /* User user = new User();
            user.setNom(rs.getString("user_nom"));
            r.setUser(user);

            evenements event = new evenements();
            event.setNom(rs.getString("event_nom"));
            r.setEvenement(event);

            participations.add(r);
        }
        return participations;
    }
*/
           public List<Participation> selectAll() throws SQLException {
               List<Participation> participations = new ArrayList<>();

               String req = "SELECT p.id, u.nom AS user_nom, e.nom_event AS event_nom, e.date_event AS event_date " +
                       "FROM participation p " +
                       "JOIN user u ON p.user = u.id " +
                       "JOIN evenements e ON p.event = e.id_event";

               Statement st = cnx.createStatement();
               ResultSet rs = st.executeQuery(req);

               while (rs.next()) {
                   // Récupérer les données de l'utilisateur et de l'événement
                   String userNom = rs.getString("user_nom");
                   String eventNom = rs.getString("event_nom");
                   Date eventDate = rs.getDate("event_date");

                   // Créer les objets User et evenements correspondants
                   User user = new User();
                   user.setNom(userNom);

                   evenements event = new evenements();
                   event.setNom(eventNom);
                   event.setDate(eventDate);

                   // Créer un nouvel objet Participation
                   Participation participation = new Participation(user, event);

                   // Ajouter la participation à la liste
                   participations.add(participation);
               }

               return participations;
           }


    public List<Participation> searchByUser(String userName) throws SQLException {
        List<Participation> participations = new ArrayList<>();

        // Requête SQL pour rechercher les participations par nom d'utilisateur
        String req = "SELECT p.id, u.nom AS user_nom, e.nom_event AS event_nom, e.date_event AS event_date " +
                "FROM participation p " +
                "JOIN user u ON p.user = u.id " +
                "JOIN evenements e ON p.event = e.id_event " +
                "WHERE LOWER(u.nom) LIKE LOWER(?)";

        // Préparation de la requête préparée
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom d'utilisateur
        ps.setString(1, "%" + userName + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque participation à la liste
        while (rs.next()) {
            // Récupérer les données de l'utilisateur et de l'événement
            String userNom = rs.getString("user_nom");
            String eventNom = rs.getString("event_nom");
            Date eventDate = rs.getDate("event_date");

            // Créer les objets User et evenements correspondants
            User user = new User();
            user.setNom(userNom);

            evenements event = new evenements();
            event.setNom(eventNom);
            event.setDate(eventDate);

            // Créer un nouvel objet Participation
            Participation participation = new Participation(user, event);

            // Ajouter la participation à la liste
            participations.add(participation);
        }

        return participations;
    }


}
