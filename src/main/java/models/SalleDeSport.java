package models;

public class SalleDeSport {
    private int id_salle , num_salle;
    private String nom_salle;
    private String lieu_salle;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    private String descr ;

    private String lienVideo;
    private String image;
    private String location;

    public SalleDeSport() {
    }

    public SalleDeSport(int id_salle, int num_salle, String nom_salle , String descr , String lieu_salle,String lienVideo,String image,String location) {
        this.id_salle = id_salle;
        this.num_salle = num_salle;
        this.nom_salle = nom_salle;
        this.descr=descr;
        this.lieu_salle = lieu_salle;
        this.lienVideo = lienVideo;
        this.image = image;
        this.location = location;
    }
    public SalleDeSport( String nom_salle, String descr,String lieu_salle ,int num_salle,String lienVideo,String image,String location) {
        this.nom_salle = nom_salle;
        this.descr = descr;
        this.lieu_salle = lieu_salle;
        this.num_salle = num_salle;
        this.lienVideo = lienVideo;
        this.image = image;
        this.location = location;
    }

    public String getLienVideo() {
        return lienVideo;
    }

    public void setLienVideo(String lienVideo) {
        this.lienVideo = lienVideo;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_salle() {
        return id_salle;
    }

    public int getNum_salle() {
        return num_salle;
    }

    public String getNom_salle() {
        return nom_salle;
    }

    public String getLieu_salle() {
        return lieu_salle;
    }


    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public void setNum_salle(int num_salle) {
        this.num_salle = num_salle;
    }

    public void setNom_salle(String nom_salle) {
        this.nom_salle = nom_salle;
    }

    public void setLieu_salle(String lieu_salle) {
        this.lieu_salle = lieu_salle;
    }

    @Override
    public String toString() {
        return "SalleDeSport{" +
                ", num_salle=" + num_salle +
                ", nom_salle='" + nom_salle + '\'' +
                ", lieu_salle='" + lieu_salle + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }
}
