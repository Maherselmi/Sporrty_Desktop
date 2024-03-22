package models;

public class Cabine {
    private int id;
    private String nom_cabine ;
    private int capacite ;
    private boolean has_vr;
    private SalleDeSport id_salle;
    private String image;

    public Cabine() {
    }

    public Cabine(int id, String nom_cabine, int capacite, boolean has_vr,SalleDeSport id_salle,String image) {
        this.id = id;
        this.nom_cabine = nom_cabine;
        this.capacite = capacite;
        this.has_vr = has_vr;
        this.id_salle = id_salle;
        this.image = image;
    }
    public Cabine(String nom_cabine, int capacite, boolean has_vr,SalleDeSport id_salle,String image) {
        this.nom_cabine = nom_cabine;
        this.capacite = capacite;
        this.has_vr = has_vr;
        this.id_salle = id_salle;
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

    public String getNom_cabine() {
        return nom_cabine;
    }

    public int getCapacite() {
        return capacite;
    }

    public boolean isHas_vr() {
        return has_vr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom_cabine(String nom_cabine) {
        this.nom_cabine = nom_cabine;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setHas_vr(boolean has_vr) {
        this.has_vr = has_vr;
    }

    public SalleDeSport getId_salle() {
        return id_salle;
    }

    public void setId_salle(SalleDeSport id_salle) {
        this.id_salle = id_salle;
    }

    @Override
    public String toString() {
        return "Cabine{" +
                "id=" + id +
                ", nom_cabine='" + nom_cabine + '\'' +
                ", capacite=" + capacite +
                ", has_vr=" + has_vr +
                '}';
    }
}
