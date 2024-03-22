
package services;

import models.evenements;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements CRUD_EVENTSREC<evenements> {
    private static ServiceEvenement instance;
    private Connection cnx;

    public ServiceEvenement() {
        cnx = DBConnection.getInstance().getCnx();
    }

    /*  @Override
      public void insertOne(evenements evenements) throws SQLException {
          String req = "INSERT INTO evenements (nom_event, descri_event, categorie_event, date_event, lieu_event, nbr_par) VALUES" + " (?, ?, ?, ?, ?, ?)";
          PreparedStatement ps = cnx.prepareStatement(req);
          ps.setString(1, evenements.getNom());
          ps.setString(2, evenements.getDescri());
          ps.setString(3, evenements.getCategorie());
          ps.setDate(4, evenements.getDate());
          ps.setString(5, evenements.getLieu());
          ps.setInt(6, evenements.getNbrP());
          ps.executeUpdate();
      }*/
    @Override
    public void insertOne(evenements evenements) throws SQLException {
        String req = "INSERT INTO evenements (nom_event, descri_event, categorie_event, date_event, lieu_event) VALUES" + " (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, evenements.getNom());
        ps.setString(2, evenements.getDescri());
        ps.setString(3, evenements.getCategorie());
        ps.setDate(4, evenements.getDate());
        ps.setString(5, evenements.getLieu());
        //  ps.setInt(6, evenements.getNbrP());
        ps.executeUpdate();
    }

    /*
        @Override
        public void updateOne(evenements evenements) throws SQLException {
            try {
                String req = "UPDATE evenements SET nom_event=?,descri_event=?,categorie_event=?,date_event=?,lieu_event=?,nbr_par=? WHERE id_event=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                // Définition des valeurs des paramètres de substitution
                ps.setString(1, evenements.getNom());
                ps.setString(2, evenements.getDescri());
                ps.setString(3, evenements.getCategorie());
                ps.setDate(4, evenements.getDate());
                ps.setString(5, evenements.getLieu());
                ps.setInt(6, evenements.getNbrP());
                ps.setInt(7, evenements.getId());
                // Assumant que getId_event() retourne l'identifiant de l'objet Evenement
                // Exécution de la requête préparée
                ps.executeUpdate();
                System.out.println("Evenement  modifié avec succès...");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }*/
    @Override
    public void updateOne(evenements evenements) throws SQLException {
        try {
            String req = "UPDATE evenements SET nom_event=?,descri_event=?,categorie_event=?,date_event=?,lieu_event=? WHERE id_event=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            // Définition des valeurs des paramètres de substitution
            ps.setString(1, evenements.getNom());
            ps.setString(2, evenements.getDescri());
            ps.setString(3, evenements.getCategorie());
            ps.setDate(4, evenements.getDate());
            ps.setString(5, evenements.getLieu());
            // ps.setInt(6, evenements.getNbrP());
            ps.setInt(6, evenements.getId());
            // Assumant que getId_event() retourne l'identifiant de l'objet Evenement
            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("Evenement  modifié avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteOne(evenements evenements) throws SQLException {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM evenements WHERE id_event = " + evenements.getId() + "";
            st.executeUpdate(req);
            System.out.println("evenement  supperimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<evenements> selectAll() throws SQLException {
        List<evenements> evenementsList = new ArrayList<>();

        String req = "SELECT * FROM evenements";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            evenements e = new evenements();

            e.setId(rs.getInt((1)));
            e.setNom(rs.getString((2)));
            e.setDescri(rs.getString((3)));
            e.setCategorie(rs.getString((4)));
            e.setDate(rs.getDate((5)));
            e.setLieu(rs.getString((6)));
            e.setNbrP(rs.getInt((7)));

            evenementsList.add(e);
        }

        return evenementsList;

    }

    public evenements searchByName(String name) throws SQLException {
        evenements e = null;

        String req = "SELECT * FROM evenements WHERE `nom_event` = ? LIMIT 1";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition des valeurs des paramètres de substitution
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery(); // Fix: Use ps.executeQuery() instead of st.executeQuery(req)

        if (rs.next()) {
            e = new evenements();

            e.setId(rs.getInt(1)); // Fix: Remove unnecessary parentheses
            e.setNom(rs.getString(2));
            e.setDescri(rs.getString(3));
            e.setCategorie(rs.getString(4));
            e.setDate(rs.getDate(5));
            e.setLieu(rs.getString(6));
            e.setNbrP(rs.getInt(7));
        }

        return e;
    }

    public static ServiceEvenement getInstance() {
        if (instance == null) {
            instance = new ServiceEvenement();
        }
        return instance;
    }

    public List<evenements> getEventsForMonth(int year, int month) throws SQLException {
        List<evenements> eventsForMonth = new ArrayList<>();

        // Créer une requête SQL pour récupérer les événements pour le mois spécifié
        String req = "SELECT * FROM evenements WHERE YEAR(date_event) = ? AND MONTH(date_event) = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, year);
        ps.setInt(2, month);

        // Exécuter la requête
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et créer des objets evenements correspondants
        while (rs.next()) {
            evenements e = new evenements();
            e.setId(rs.getInt("id_event"));
            e.setNom(rs.getString("nom_event"));
            e.setDescri(rs.getString("descri_event"));
            e.setCategorie(rs.getString("categorie_event"));
            e.setDate(rs.getDate("date_event"));
            e.setLieu(rs.getString("lieu_event"));
            e.setNbrP(rs.getInt("nbr_par"));
            eventsForMonth.add(e);
        }

        return eventsForMonth;
    }

    public void updateNbrParticipants(int eventId, int newNbrParticipants) throws SQLException {
        try {
            String query = "UPDATE evenements SET nbr_par = ? WHERE id_event = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, newNbrParticipants);
            ps.setInt(2, eventId);
            ps.executeUpdate();
            System.out.println("Nombre de participants mis à jour dans la base de données.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du nombre de participants dans la base de données : " + e.getMessage());
        }
    }

    // Méthode pour rechercher des événements par titre, date et catégorie
    public List<evenements> rechercher(String titre, LocalDate dateDebut, String categorie) throws SQLException {
        List<evenements> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenements WHERE LOWER(nom_event) LIKE LOWER(?) AND date >= ? AND LOWER(categorie_event) LIKE LOWER(?)";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setString(1, "%" + titre.toLowerCase() + "%");
        statement.setDate(2, Date.valueOf(dateDebut));
        statement.setString(3, "%" + categorie.toLowerCase() + "%");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            //evenements ev = mapResultSetToEvenement(resultSet);
            evenements e = new evenements();
            e.setId(rs.getInt("id_event"));
            e.setNom(rs.getString("nom_event"));
            e.setDescri(rs.getString("descri_event"));
            e.setCategorie(rs.getString("categorie_event"));
            e.setDate(rs.getDate("date_event"));
            e.setLieu(rs.getString("lieu_event"));
            e.setNbrP(rs.getInt("nbr_par"));
            evenements.add(e);
        }
        return evenements;
    }

    public List<evenements> searchByTitre(String titre) throws SQLException {
        List<evenements> evenements = new ArrayList<>();
        String req = "SELECT * FROM evenements WHERE LOWER(nom_event) LIKE LOWER(?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + titre + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
            evenements e = new evenements();
            e.setId(rs.getInt("id_event"));
            e.setNom(rs.getString("nom_event"));
            e.setDescri(rs.getString("descri_event"));
            e.setCategorie(rs.getString("categorie_event"));
            e.setDate(rs.getDate("date_event"));
            e.setLieu(rs.getString("lieu_event"));
            e.setNbrP(rs.getInt("nbr_par"));
            evenements.add(e);
        }
        return evenements;
    }

    public List<evenements> selectAllOrderedByDateDesc() throws SQLException {
        List<evenements> events = new ArrayList<>();
        String req = "SELECT * FROM evenements ORDER BY date_event DESC";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            evenements e = new evenements();
            e.setId(rs.getInt("id_event"));
            e.setNom(rs.getString("nom_event"));
            e.setDescri(rs.getString("descri_event"));
            e.setCategorie(rs.getString("categorie_event"));
            e.setDate(rs.getDate("date_event"));
            e.setLieu(rs.getString("lieu_event"));
            e.setNbrP(rs.getInt("nbr_par"));
            events.add(e);
        }
        return events;
    }

    public List<evenements> selectAllOrderedByNom() throws SQLException {
        List<evenements> events = new ArrayList<>();
        String req = "SELECT * FROM evenements ORDER BY nom_event";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            evenements e = new evenements();
            e.setId(rs.getInt("id_event"));
            e.setNom(rs.getString("nom_event"));
            e.setDescri(rs.getString("descri_event"));
            e.setCategorie(rs.getString("categorie_event"));
            e.setDate(rs.getDate("date_event"));
            e.setLieu(rs.getString("lieu_event"));
            e.setNbrP(rs.getInt("nbr_par"));
            events.add(e);
        }


        return events;
    }

    public List<evenements> selectAllOrderedByDate() throws SQLException {
        List<evenements> events = new ArrayList<>();
        String req = "SELECT * FROM evenements ORDER BY date_event DESC";
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            evenements e = new evenements();
            e.setId(rs.getInt("id_event"));
            e.setNom(rs.getString("nom_event"));
            e.setDescri(rs.getString("descri_event"));
            e.setCategorie(rs.getString("categorie_event"));
            e.setDate(rs.getDate("date_event"));
            e.setLieu(rs.getString("lieu_event"));
            e.setNbrP(rs.getInt("nbr_par"));
            events.add(e);
        }
        return events;


    }
    public List<evenements> trierParnbrP(int nbP) throws SQLException {
        List<evenements> Stocks = new ArrayList<>();
        String req = "SELECT * FROM evenements WHERE nbr_par LIKE ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + nbP + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
            evenements s = new evenements();
            s.setId(rs.getInt(("id")));
            s.setNom(rs.getString(("nom")));
            s.setDescri(rs.getString(("descri")));
            s.setCategorie(rs.getString(("categorie")));
            s.setDate(rs.getDate("date"));
            s.setLieu(rs.getString(("Lieu")));
            s.setNbrP(rs.getInt(("nbrP")));
            Stocks.add(s);

        }

        return Stocks;
    }

    public evenements selectByIdEV(int id) throws SQLException {
        String req = "SELECT * FROM `user` WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            evenements s = new evenements();
            s.setId(rs.getInt(("id")));
            s.setNom(rs.getString(("nom")));
            s.setDescri(rs.getString(("descri")));
            s.setCategorie(rs.getString(("categorie")));
            s.setDate(rs.getDate("date"));
            s.setLieu(rs.getString(("Lieu")));
            s.setNbrP(rs.getInt(("nbrP")));
            return s;
        } else {
            return null; // Retourner null si aucun utilisateur n'est trouvé avec cet ID
        }
    }
}


