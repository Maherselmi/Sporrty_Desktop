package models;

public class Materiel {
    private int id;
    private String nom;
    private String categorie;
    private int qte;
    private Stock id_stock;
    private String image;
    private String video;


    public Materiel(int id, String nom, int qte, int id_Stock) {
    }

    public Materiel(int id, String nom, String categorie, int qte, Stock id_stock,String video,String image ) {
        this.id = id;

        this.nom = nom;
        this.categorie = categorie;
        this.qte = qte;
        this.id_stock = id_stock;
        this.video=video;
        this.image=image;
    }

    public Materiel() {


    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Stock getId_stock() {
        return id_stock;
    }

    public void setId_stock(Stock id_stock) {
        this.id_stock = id_stock;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", qte=" + qte +
                ", id_stock=" + id_stock +
                '}';
    }
}
