package models;



public class cours {

    private int id_cours;
    private String nom;
    private String coach;
    private String jours;
    private int duree;
    private String type;
    private int prix;
    private programme id_programme;
    private String image;
    private String lienVideo;
    
    public cours() {
    }


    public cours(String nom, String coach, String jours, int duree, String type, int prix,String image,String lienVideo) {
        this.nom = nom;
        this.coach = coach;
        this.jours = jours;
        this.duree = duree;
        this.type = type;
        this.prix = prix;
        this.image=image;
        this.lienVideo = lienVideo;
    }

    public cours(int id_cours, String nom, String coach, String jours, int duree, String type, int prix, programme id_programme,String image,String lienVideo) {
        this.id_cours = id_cours;
        this.nom = nom;
        this.coach = coach;
        this.jours = jours;
        this.duree = duree;
        this.type = type;
        this.prix = prix;
        this.id_programme = id_programme;
        this.image=image;
        this.lienVideo = lienVideo;
    }

    public cours(String nom, String coach, String jours, int duree, String type, int prix, programme id_programme,String image,String lienVideo) {
        this.nom = nom;
        this.coach = coach;
        this.jours = jours;
        this.duree = duree;
        this.type = type;
        this.prix = prix;
        this.id_programme = id_programme;
        this.image=image;
        this.lienVideo = lienVideo;

    }

    public String getLienVideo() {
        return lienVideo;
    }

    public void setLienVideo(String lienVideo) {
        this.lienVideo = lienVideo;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getJours() {
        return jours;
    }

    public void setJours(String jours) {
        this.jours = jours;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }


    public programme getId_programme() {
        return id_programme;
    }

    public void setId_programme(programme id_programme) {
        this.id_programme = id_programme;
    }

    public cours(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id_cours=" + id_cours +
                ", nom='" + nom + '\'' +
                ", coach='" + coach + '\'' +
                ", jours='" + jours + '\'' +
                ", duree=" + duree +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                ", id_programme=" + id_programme +
                ", image='" + image + '\'' +
                '}';
    }
}


