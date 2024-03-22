package models;

public class Stock {
    private int  id;
    private String nom;
    private int quantite;
    private String lieu;
    private String cordonnet;

    public Stock() {
    }

    public Stock(int id, String nom, int quantite, String lieu, String cordonnet) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.lieu = lieu;
        this.cordonnet = cordonnet;
    }

    public String getCordonnet() {
        return cordonnet;
    }

    public void setCordonnet(String cordonnet) {
        this.cordonnet = cordonnet;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
