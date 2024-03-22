package models;

public class Participation {
    private int id;
    private User user;

    private evenements evenement;
    public Participation(User user, evenements evenement) {
        this.user = user;
        this.evenement = evenement;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public evenements getEvenement() {
        return evenement;
    }

    public void setEvenement(evenements evenement) {
        this.evenement = evenement;
    }

    public Participation() {
    }

    @Override
    public String toString() {
        return "Participation{" +
                "user=" + user +
                ", evenement=" + evenement +
                '}';
    }
}
