package models;

public class Produit {
    private int id;
    private String nom;
    private float prix;
    private int qte;
    private String description;
    private String categorie;

    private String image;
    public Produit() {
    }

    public Produit(int id, String nom, float prix, int qte, String description, String categorie,String image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
        this.description = description;
        this.categorie = categorie;
        this.image = image;
    }

    public Produit(String nom, float prix, int qte, String description, String categorie, String image) {
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
        this.description = description;
        this.categorie = categorie;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
