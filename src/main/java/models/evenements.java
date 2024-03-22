package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import java.sql.Date;
import java.sql.Timestamp;
public class evenements {

    private int id ;
    private  String nom;
    private String descri;
    private String categorie;
    private Date date;
    private String lieu;
    private int nbrP;

   /* private ObservableList<String> notificationsList = FXCollections.observableArrayList();
    public void ajouterNotification(String notification) {
        notificationsList.add(notification);
    }
    // Méthode pour afficher les notifications dans un ScrollPane
    public ScrollPane afficherNotificationsScrollPane() {
        VBox notificationsBox = new VBox();
        for (String notification : notificationsList) {
            Label label = new Label(notification);
            notificationsBox.getChildren().add(label);
        }

        ScrollPane scrollPane = new ScrollPane(notificationsBox);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }
    public void afficherNotification(String notification) {
        // Créer une notification
        Notifications.create()
                .title("Nouvel événement")
                .text(notification)
                .showInformation();

        // Ajouter la notification à la liste des notifications
        ajouterNotification(notification);
    }*/

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

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getNbrP() {
        return nbrP;
    }

   public void setNbrP(int nbrP) {
        this.nbrP = nbrP;
    }
 /*public void participateEvent() {
       // Incrémentation du nombre de participants pour l'événement
     this.nbrP++;
       // Mise à jour de l'affichage du nombre de participants sur l'interface utilisateur
       // Vous pouvez mettre à jour l'affichage ici
       System.out.println("Participant added to event: " + this.getNom());

   }*/
 public void participateEvent() {
     // Incrémentation du nombre de participants pour l'événement
     this.nbrP++;
 }

    public evenements() {}
    /*public evenements(String nom, String descri, String categorie, Date date, String lieu, int nbrP) {
        this.nom = nom;
        this.descri = descri;
        this.categorie = categorie;
        this.date = date;
        this.lieu = lieu;
        this.nbrP = nbrP;
    } */
    public evenements(String nom, String descri, String categorie, Date date, String lieu) {
        this.nom = nom;
        this.descri = descri;
        this.categorie = categorie;
        this.date = date;
        this.lieu = lieu;
       // this.nbrP = nbrP;
    }

    public evenements(int id, String nom, String descri, String categorie, Date date, String lieu, int nbrP) {
        this.id = id;
        this.nom = nom;
        this.descri = descri;
        this.categorie = categorie;
        this.date = date;
        this.lieu = lieu;
        this.nbrP = nbrP;

    }

    @Override
    public String toString() {
        return "evenements{" +
                "nom='" + nom + '\'' +
                ", descri='" + descri + '\'' +
                ", categorie='" + categorie + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", nbrP=" + nbrP +
                '}';
    }

}
