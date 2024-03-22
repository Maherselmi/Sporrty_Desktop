package models;

public class Abonnement {
    private int id;
    private double prix;
    private String type;
    User id_user;
    private String description;


    public Abonnement() {
        // Appel du constructeur par défaut de la classe parente
        super();
    }
    public Abonnement(double prix, String type, User user) {
        this.prix = prix;
        this.type = type;
        this.id_user = user;
    }
    public Abonnement(int id ,double prix, String type,String description) {
        this.id=id;
        this.prix = prix;
        this.type = type;
        this.description=description;

    }
public Abonnement(int id, double prix, String type) {
       this.id = id;
       this.prix = prix;
       this.type = type;
   }
   /* public Abonnement(int id, double prix, String type,int id_user) {
        this.id = id;
        this.prix = prix;
        this.type = type;
        this.id_user=id_user;
    }*/
    public Abonnement(int id, double prix, String type, User user) {
        // Appel du constructeur de la classe parente avec l'ID
        super();
        this.prix = prix;
        this.type = type;
        this.id_user = user;
    }
    public Abonnement(double prix, String type,String description) {

        this.prix = prix;
        this.type = type;
        this.description=description;
    }


    public int getId() {
        return id;
    }

    public double getPrix() {
        return prix;
    }

    public String getType() {
        return type;
    }


    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", prix=" + prix +
                ", type=" + type +
                ", idUser=" + id_user +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }


    /*public int getIdUser() {
        return id_user;
    }*/

    public void setIdUser(User idUser) {
        this.id_user = idUser;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription (String description)
    {this.description=description;}

}
