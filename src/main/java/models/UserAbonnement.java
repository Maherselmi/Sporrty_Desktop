package models;

public class UserAbonnement {
    private User idUtilisateur;
    private Abonnement idAbonnement;

    public UserAbonnement(User idUtilisateur, Abonnement idAbonnement) {
        this.idUtilisateur = idUtilisateur;
        this.idAbonnement = idAbonnement;
    }

    public User getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(User idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Abonnement getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(Abonnement idAbonnement) {
        this.idAbonnement = idAbonnement;
    }


}
