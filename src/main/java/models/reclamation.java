package models;

import java.sql.Date;

public class reclamation {
    private int idRec;
    private  String type;

    private  String descriRec;
    private Date date;
    private  User idUser;

    public reclamation(int idRec, String type,User idUser, String nomComplet, String descriRec, Date date,  String nbrEt, String reponse, String statut) {
        this.idRec = idRec;
        this.type = type;
        this.descriRec = descriRec;
        this.date = date;
        this.idUser = idUser;
        this.nomComplet = nomComplet;
        this.nbrEt = nbrEt;
        this.reponse = reponse;
        this.statut = statut;
    }

    public User getIdUser() {
    return idUser;
}

    public void setIdUser(User idUser) {
    this.idUser = idUser;
}

     public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }


    private String nomComplet;


    private  String nbrEt;
    private String reponse;
    private  String statut;

    @Override
    public String toString() {
        return "reclamation{" +
                "Type='" + type + '\'' +

                ", descriRec='" + descriRec + '\'' +
                ", date=" + date +
                ", nbrEt=" + nbrEt +
                '}';
    }

    public reclamation() {
    }

    public reclamation(int idRec, String type, String descriRec, User idUser, String nomComplet, String nbrEt, String statut) {
        this.idRec = idRec;
        this.type = type;
        this.descriRec = descriRec;
        this.idUser = idUser;
        this.nomComplet = nomComplet;
        this.nbrEt = nbrEt;
        this.statut = statut;
    }

   /* public reclamation(String type, int idUser, String descriRec, Date date,String nbrEt, String statut) {
        this.type = type;
        this.descriRec = descriRec;
        this.date = date;
        this.nbrEt = nbrEt;
        this.idUser = idUser;
        this.statut = statut;
    }
    */
    public reclamation(String type, User idUser, String descriRec,String nbrEt, String statut) {
        this.type = type;
        this.descriRec = descriRec;
        this.date = new Date(System.currentTimeMillis());
        this.nbrEt = nbrEt;
        this.idUser = idUser;
        this.statut = statut;

    }

    /*
public reclamation(String type,String nomComplet, String descriRec, Date date, int nbrEt,String statut) {
    this.type = type;
    this.descriRec = descriRec;
    this.date = date;
    this.nbrEt = nbrEt;
    this.nomComplet = nomComplet;
    this.statut = statut;
}*/

    public reclamation(int idRec, String type, String descriRec, Date date, String nbrEt) {
        this.idRec = idRec;
        this.type = type;
        this.descriRec = descriRec;
        this.date = date;
        this.nbrEt = nbrEt;
    }

    public reclamation(String reponse, String statut) {
        this.reponse = reponse;
        this.statut = statut;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getDescriRec() {
        return descriRec;
    }

    public void setDescriRec(String descriRec) {
        this.descriRec = descriRec;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNbrEt() {
        return nbrEt;
    }

    public void setNbrEt(String nbrEt) {
        this.nbrEt = nbrEt;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
