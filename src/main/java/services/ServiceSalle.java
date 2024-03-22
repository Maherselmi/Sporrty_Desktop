package services;

import models.SalleDeSport;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSalle implements Crud_serviceCabine<SalleDeSport> {
    private Connection cnx;

    public ServiceSalle() {
        cnx= DBConnection.getInstance().getCnx();
    }

    @Override
    public void insert(SalleDeSport salleDeSport) throws SQLException {
        String req ="INSERT INTO sale_de_sport (`num_salle`,`nom_salle` ,`descr`, `lieu_salle`,`lienVideo`,`image`,`location`) VALUES " +
                "(?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,salleDeSport.getNum_salle());
        ps.setString(2,salleDeSport.getNom_salle());
        ps.setString(3,salleDeSport.getDescr());
        ps.setString(4,salleDeSport.getLieu_salle());
        ps.setString(5,salleDeSport.getLienVideo());
        ps.setString(6,salleDeSport.getImage());
        ps.setString(7,salleDeSport.getLocation());
        ps.executeUpdate();

    }

    @Override
    public void update(SalleDeSport salleDeSport) throws SQLException {
        try {
            String req = "UPDATE sale_de_sport SET nom_salle=?, num_salle=?,descr=?, lieu_salle=?,lienVideo=?,image=?,location=? WHERE id_salle=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,salleDeSport.getNom_salle());
            ps.setInt(2, salleDeSport.getNum_salle());
            ps.setString(3,salleDeSport.getDescr());
            ps.setString(4, salleDeSport.getLieu_salle());
            ps.setString(5, salleDeSport.getLienVideo());
            ps.setString(6, salleDeSport.getImage());
            ps.setString(7, salleDeSport.getLocation());
            ps.setInt(8, salleDeSport.getId_salle());
            ps.executeUpdate();
            System.out.println("salle modifié avec succès..");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Delete(SalleDeSport salleDeSport) throws SQLException {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM sale_de_sport WHERE id_salle = "+salleDeSport.getId_salle()+"";
            st.executeUpdate(req);
            System.out.println("salle  supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

    @Override
    public List<SalleDeSport> selectAll() throws SQLException {

        List<SalleDeSport> sallelist=new ArrayList<>();
        String req = "SELECT * FROM sale_de_sport";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
           SalleDeSport s =new SalleDeSport();
            s.setId_salle(rs.getInt(("id_salle")));
            s.setLieu_salle(rs.getString(("Lieu_salle")));
            s.setNum_salle(rs.getInt(("Num_salle")));
            s.setDescr(rs.getString(("Descr")));
            s.setNom_salle(rs.getString(("Nom_salle")));
            s.setLienVideo(rs.getString(("lienVideo")));
            s.setImage(rs.getString(("image")));
            s.setLocation(rs.getString(("location")));

            sallelist.add(s);
        }

        return sallelist;
    }
    public List<SalleDeSport> searchByNom(String nom_salle) throws SQLException {
        List<SalleDeSport> sallelist = new ArrayList<>();
        String req = "SELECT * FROM sale_de_sport WHERE nom_salle LIKE ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, nom_salle.toUpperCase() + "%"); // Convertir la première lettre en majuscule et ajouter le symbole % pour rechercher les noms qui commencent par la lettre spécifiée
        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();
        // Parcourir les résultats et ajouter chaque ligne à la liste de salles de sport
        while (rs.next()) {
            SalleDeSport s = new SalleDeSport();
            s.setId_salle(rs.getInt("id_salle"));
            s.setNum_salle(rs.getInt("num_salle"));
            s.setNom_salle(rs.getString("nom_salle"));
            s.setDescr(rs.getString("descr"));
            s.setLieu_salle(rs.getString("lieu_salle"));
            s.setLienVideo(rs.getString(("lienVideo")));
            s.setImage(rs.getString(("image")));
            s.setLocation(rs.getString(("location")));
            sallelist.add(s);
        }

        return sallelist;
    }
    public SalleDeSport selectById(int id) throws SQLException {
        SalleDeSport salle = null;
        String req = "SELECT * FROM sale_de_sport WHERE id_salle = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            salle = new SalleDeSport();
            salle.setId_salle(rs.getInt("id_salle"));
            salle.setLieu_salle(rs.getString("Lieu_salle"));
            salle.setNum_salle(rs.getInt("Num_salle"));
            salle.setDescr(rs.getString("Descr"));
            salle.setNom_salle(rs.getString("Nom_salle"));
            salle.setLienVideo(rs.getString("lienVideo"));
            salle.setImage(rs.getString("image"));
            salle.setLocation(rs.getString("location"));
        }
        return salle;
    }

}
