package services;

import models.Produit;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements CRUD<Produit>{
    private Connection cnx ;

    public ServiceProduit() {
        cnx = DBConnection.getInstance().getCnx();;
    }

    @Override
    public void insertOne(Produit produit) throws SQLException {
        String req = "INSERT INTO `produit`(`id`, `nom`, `prix`, `qte`, `description`, `categorie`,`image`) VALUES (?, ?, ?, ?, ?, ?,?)";

        // Using PreparedStatement to safely handle parameters
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, produit.getId()); // Assuming id is a String, adjust accordingly
        pst.setString(2, produit.getNom()); // Assuming nom is a String, adjust accordingly
        pst.setDouble(3, produit.getPrix()); // Assuming prix is a double, adjust accordingly
        pst.setInt(4, produit.getQte()); // Assuming qte is an integer, adjust accordingly
        pst.setString(5, produit.getDescription()); // Assuming description is a String, adjust accordingly
        pst.setString(6, produit.getCategorie()); // Assuming categorie is a String, adjust accordingly
        pst.setString(7, produit.getImage());

        pst.executeUpdate();
        System.out.println("Produit Added !");

        // Close PreparedStatement
        pst.close();
    }

    @Override
    public void updateOne(Produit produit) throws SQLException {
        String req = "UPDATE `produit` SET `nom` = ? ,`prix` = ? ,`qte`= ? ,`description`= ? ,`categorie`= ?,`image`= ? WHERE `id` = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setString(1, produit.getNom());
        st.setFloat(2, produit.getPrix());
        st.setFloat(3, produit.getQte());
        st.setString(4, produit.getDescription());
        st.setString(5, produit.getCategorie());
        st.setString(6, produit.getImage());
        st.setInt(7, produit.getId());
        st.executeUpdate();
        System.out.println("Produit Updated !");
    }

    @Override
    public void deleteOne(Produit produit) throws SQLException {
        String req = "DELETE FROM `produit` WHERE `id` = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, produit.getId());
        st.executeUpdate();
        System.out.println("Produit Deleted !");
    }

    @Override
    public List<Produit> selectAll() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM `produit`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            float prix = rs.getFloat("prix");
            int qte = rs.getInt("qte");
            String description = rs.getString("description");
            String categorie = rs.getString("categorie");
            String image = rs.getString("image");
            produits.add(new Produit(id, nom, prix, qte,description,categorie,image));
        }
        return produits;
    }

    public Produit selectById(int id) throws SQLException {
        String req = "SELECT * FROM `produit` WHERE `id` = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            String nom = rs.getString("nom");
            float prix = rs.getFloat("prix");
            int qte = rs.getInt("qte");
            String description = rs.getString("description");
            String categorie = rs.getString("categorie");
            String image = rs.getString("image");
            return new Produit(id, nom, prix, qte,description,categorie,image);
        }
        return null;
    }

    public List<Produit> searchBy(String filterBy, String search) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM `produit` WHERE `"+filterBy+"` LIKE '%"+search+"%'";
        System.out.println(req);
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            float prix = rs.getFloat("prix");
            int qte = rs.getInt("qte");
            String description = rs.getString("description");
            String categorie = rs.getString("categorie");
            String image = rs.getString("image");
            produits.add(new Produit(id, nom, prix, qte,description,categorie,image));
        }
        return produits;
    }
}
