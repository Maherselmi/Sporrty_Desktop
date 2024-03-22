package services;

import models.Stock;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceStock implements CRUD_materiel<Stock> {
    private Connection cnx;

    public ServiceStock() {
        cnx= DBConnection.getInstance().getCnx();
    }

    @Override
    public void insert(Stock stock) throws SQLException {
        String req ="INSERT INTO `stock` (`nom`,`quantite`,`lieu`,`cordonnet`) VALUES " +
                "(?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition des valeurs des paramètres de substitution
        ps.setString(1,stock.getNom());
        ps.setInt(2,stock.getQuantite());
        ps.setString(3,stock.getLieu());
        ps.setString(4,stock.getCordonnet());
        // Exécution de la requête
        ps.executeUpdate();


    }

    @Override
    public boolean update(Stock stock) throws SQLException {
        try {
            String req = "UPDATE stock SET nom=?, quantite=?, lieu=?, cordonnet=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            // Définition des valeurs des paramètres de substitution
            ps.setString(1, stock.getNom());
            ps.setInt(2, stock.getQuantite());
            ps.setString(3, stock.getLieu());
            ps.setString(4, stock.getCordonnet());
            ps.setInt(5, stock.getId()); // Assumant que getId_materiel() retourne l'identifiant de l'objet Materiel
            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("stock modifié avec succès..");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public void Delete(Stock stock) throws SQLException {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM stock WHERE id = "+stock.getId()+"";
            st.executeUpdate(req);
            System.out.println("evenement  supperimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Stock> selectAll() throws SQLException {
        List<Stock> stockList=new ArrayList<>();
        String req = "SELECT * FROM `stock`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Stock s =new Stock();
            s.setId(rs.getInt(("id")));
            s.setNom(rs.getString(("nom")));
            s.setQuantite(rs.getInt(("quantite")));
            s.setLieu(rs.getString(("lieu")));
            s.setCordonnet(rs.getString(("cordonnet")));

            stockList.add(s);
        }

        return stockList;
    }

    public Stock selectByName(String name) throws SQLException {
        List<Stock> stockList=new ArrayList<>();
        String req = "SELECT * FROM `stock` WHERE `nom` = '"+name+"' ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Stock s =new Stock();
            s.setId(rs.getInt(("id")));
            s.setNom(rs.getString(("nom")));
            s.setQuantite(rs.getInt(("quantite")));
            s.setLieu(rs.getString(("lieu")));
            s.setCordonnet(rs.getString(("cordonnet")));
            stockList.add(s);
        }

        return stockList.get(0);
    }


    public Stock selectById(int id) throws SQLException {
        List<Stock> stockList=new ArrayList<>();
        String req = "SELECT * FROM `stock` WHERE `id` = '"+id+"' ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Stock s =new Stock();
            s.setId(rs.getInt(("id")));
            s.setNom(rs.getString(("nom")));
            s.setQuantite(rs.getInt(("quantite")));
            s.setLieu(rs.getString(("lieu")));
            s.setCordonnet(rs.getString(("cordonnet")));
            stockList.add(s);
        }

        return stockList.get(0);
    }
    public List<Stock> searchByNom(String nom) throws SQLException {
        List<Stock> stocks = new ArrayList<>();
        String req = "SELECT * FROM `stock` WHERE `nom` LIKE ? ORDER BY `nom` ASC"; // Ajout de l'ordre alphabétique dans la requête SQL
        PreparedStatement ps = cnx.prepareStatement(req);
        // Définition de la valeur du paramètre de substitution pour le nom
        ps.setString(1, "%" + nom + "%"); // Le symbole % est utilisé pour rechercher les noms qui contiennent la chaîne spécifiée

        // Exécution de la requête préparée
        ResultSet rs = ps.executeQuery();

        // Parcourir les résultats et ajouter chaque ligne à la liste de matériel
        while (rs.next()) {
            Stock s = new Stock();
            s.setId(rs.getInt(("id")));
            s.setNom(rs.getString(("nom")));
            s.setQuantite(rs.getInt(("Quantite")));
            s.setLieu(rs.getString(("Lieu")));
            s.setCordonnet(rs.getString(("cordonnet")));
            stocks.add(s);
        }

        return stocks;
    }

}
