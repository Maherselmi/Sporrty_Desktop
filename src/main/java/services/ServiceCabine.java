package services;

import models.Cabine;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCabine implements Crud_serviceCabine <Cabine> {
    private Connection cnx;

    public ServiceCabine() {
        cnx= DBConnection.getInstance().getCnx();
    }

    @Override
    public void insert(Cabine cabine) throws SQLException {
        String req ="INSERT INTO `cabine`( `nom_cabine`, `capacite`, `has_vr`,`id_salle`,`image`) VALUES " +
                "(?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,cabine.getNom_cabine());
        ps.setInt(2,cabine.getCapacite());
        ps.setBoolean(3,cabine.isHas_vr());
        ps.setInt(4,cabine.getId_salle().getId_salle());
        ps.setString(5,cabine.getImage());
        ps.executeUpdate();

    }

    @Override
    public void update(Cabine cabine) throws SQLException {
        try {
            String req = "UPDATE cabine SET nom_cabine=?, capacite=?, has_vr=? , image=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, cabine.getNom_cabine());
            ps.setInt(2, cabine.getCapacite());
            ps.setBoolean(3, cabine.isHas_vr());
            ps.setString(4, cabine.getImage());
            ps.setInt(5, cabine.getId());
            ps.executeUpdate();
            System.out.println("cabine modifié avec succès..");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @Override
    public void Delete(Cabine cabine) throws SQLException {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM cabine WHERE `id` = "+cabine.getId()+"";
            st.executeUpdate(req);
            System.out.println("Cabine  supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

    @Override
    public List<Cabine> selectAll() throws SQLException {
        List<Cabine> cabineList=new ArrayList<>();
        String req = "SELECT * FROM cabine";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        ServiceSalle serviceSalle = new ServiceSalle();
        while (rs.next()){
            Cabine c=new Cabine();
            c.setCapacite(rs.getInt("capacite"));
            c.setNom_cabine(rs.getString("nom_cabine"));
            c.setHas_vr(rs.getBoolean("has_vr"));
            c.setId(rs.getInt("id"));
            c.setId_salle(serviceSalle.selectById(rs.getInt("id_salle")));
            c.setImage(rs.getString("image"));

            cabineList.add(c);
        }

        return cabineList;
    }

    public List<Cabine> selectAllByIdSalle(int id) throws SQLException {
        System.out.println(id);
        List<Cabine> cabineList=new ArrayList<>();
        String req = "SELECT * FROM cabine WHERE `id_salle` = "+id+"";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        ServiceSalle serviceSalle = new ServiceSalle();
        while (rs.next()){
            Cabine c=new Cabine();
            c.setCapacite(rs.getInt("capacite"));
            c.setNom_cabine(rs.getString("nom_cabine"));
            c.setHas_vr(rs.getBoolean("has_vr"));
            c.setId(rs.getInt("id"));
            c.setId_salle(serviceSalle.selectById(rs.getInt("id_salle")));
            c.setImage(rs.getString("image"));
            cabineList.add(c);
        }
        System.out.println(cabineList);
        return cabineList;
    }
    public List<Cabine> searchByNom(String nom_cabine) throws SQLException {
        List<Cabine> cabineList = new ArrayList<>();
        String req = "SELECT * FROM cabine WHERE nom_cabine LIKE ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, nom_cabine.toUpperCase() + "%"); // Convertir la première lettre en majuscule et ajouter le symbole % pour rechercher les noms qui commencent par la lettre spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();
        ServiceSalle serviceSalle = new ServiceSalle();
        // Parcourir les résultats et ajouter chaque ligne à la liste de cabines
        while (rs.next()) {
            Cabine c = new Cabine();
            c.setId(rs.getInt("id"));
            c.setNom_cabine(rs.getString("nom_cabine"));
            c.setCapacite(rs.getInt("capacite"));
            c.setHas_vr(rs.getBoolean("has_vr"));
            c.setId_salle(serviceSalle.selectById(rs.getInt("id_salle")));
            c.setImage(rs.getString("image"));
            cabineList.add(c);
        }

        return cabineList;
    }


}
