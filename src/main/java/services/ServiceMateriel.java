package services;

import models.Materiel;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMateriel implements CRUD_materiel<Materiel> {
    private Connection cnx;

    public ServiceMateriel() {
        cnx= DBConnection.getInstance().getCnx();

    }

    @Override
    public void insert(Materiel materiel) throws SQLException {
        String req ="INSERT INTO `materiel` (`nom`,`categorie`,`qte`,`id_stock`,`image`,`video`) VALUES " +
                "(?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition des valeurs des paramètres de substitution
        ps.setString(1,materiel.getNom());
        ps.setString(2, materiel.getCategorie());
        ps.setInt(3,materiel.getQte());
        ServiceStock serviceStock = new ServiceStock();
        ps.setInt(4,materiel.getId_stock().getId());
        ps.setString(5,materiel.getImage());
        ps.setString(6,materiel.getVideo());

        // Exécution de la requête
        ps.executeUpdate();




    }

    @Override
    public boolean update(Materiel materiel) throws SQLException {
        try {
            String req = "UPDATE materiel SET nom=?, categorie=?, qte=? , image=? , video=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            // Définition des valeurs des paramètres de substitution
            ps.setString(1, materiel.getNom());
            ps.setString(2, materiel.getCategorie());
            ps.setInt(3, materiel.getQte());
            ps.setString(4, materiel.getImage());
            ps.setString(5, materiel.getVideo());
            ps.setInt(6, materiel.getId());
            ps.executeUpdate();
            System.out.println("Materiel modifié avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public void Delete(Materiel materiel) throws SQLException {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM materiel WHERE id = "+materiel.getId()+"";
            st.executeUpdate(req);
            System.out.println("evenement  supperimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @Override
    public List<Materiel> selectAll() throws SQLException {
        List<Materiel> materielList=new ArrayList<>();
        String req = "SELECT * FROM `materiel`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
            while (rs.next()){
                Materiel m = new Materiel();
                m.setId(rs.getInt(("id")));
                m.setNom(rs.getString(("nom")));
                m.setCategorie(rs.getString(("categorie")));
                m.setQte(rs.getInt(("qte")));
                ServiceStock serviceStock = new ServiceStock();
                m.setId_stock(serviceStock.selectById(rs.getInt("id_stock")));
                m.setImage(rs.getString("image"));
                m.setVideo(rs.getString("video"));

            materielList.add(m);
        }

        return materielList;
    }
    public List<Materiel> searchByNom(String nom) throws SQLException {
        List<Materiel> materiels = new ArrayList<>();
        String req = "SELECT * FROM `materiel` WHERE `nom` LIKE ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + nom + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
            Materiel m = new Materiel();
            m.setId(rs.getInt(("id")));
            m.setNom(rs.getString(("nom")));
            m.setCategorie(rs.getString(("categorie")));
            m.setQte(rs.getInt(("qte")));
            ServiceStock serviceStock = new ServiceStock();
            m.setId_stock(serviceStock.selectById(rs.getInt("id_stock")));
            m.setImage(rs.getString("image"));
            m.setVideo(rs.getString("video"));
            materiels.add(m);
        }

        return materiels;
    }

}
