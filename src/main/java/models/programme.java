package models;

public class programme {
    private int id;
    private String nom;
    private String description;
    private int duree;

    public programme() {
    }

    public programme(String nom, String description, int duree) {
        this.nom = nom;
        this.description= description;
        this.duree = duree;
    }

    public programme(int id, String nom, String description, int duree) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.duree = duree;
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
        this.nom= nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "programme{" +
                "id_prog=" + id +
                ", nom_prog='" + nom + '\'' +
                ", descrip_prog='" + description + '\'' +
                ", duree_prog=" + duree +
                '}';
    }
}
