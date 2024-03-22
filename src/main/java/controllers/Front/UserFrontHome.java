package controllers.Front;

import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.GymCabine.UserGym;
import controllers.Front.ProduitPanier.userFrontGymProduit;
import controllers.Front.UserAbonement.FrontAbonnement;
import controllers.Front.UserAbonement.userFrontProfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.*;
import services.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserFrontHome {
    @FXML
    private Label idU;
    @FXML
    private Button profile;

    @FXML
    private Label titreReclamationText;

    @FXML
    private Label longestWorkoutDuration;

    @FXML
    private Label descriptionReclamation;

    @FXML
    private Label eventOneDateText;

    @FXML
    private Label nbReclamationText;
    @FXML
    private Label averageWorkoutDuration;
    @FXML
    private Label eventOneTitleText;

    @FXML
    private Label eventThreeDate;

    @FXML
    private Label eventThreeTitle;

    @FXML
    private Label eventTwoDateText;

    @FXML
    private Label eventTwoTitleText;
    @FXML
    private Label userNameLabel;
    private String userName;

    @FXML
    private Label currentUserField;

    @FXML
    private Label dailyAdvicesText;
    @FXML
    private Label nbrPannier;
    private User user;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label welcomeMessage;
    private int userId; // Champ pour stocker l'ID de l'utilisateur

    // Ajouter une méthode setId pour définir l'ID de l'utilisateur
   /* public void setId(int id) {
        idU.setText(Integer.toString(id));
        try {
            ServiceUser serviceUser = new ServiceUser();
            User user = serviceUser.selectById(id); // Utilisez directement l'ID passé en paramètre

            if (user != null) {
                setUserName(user.getNom()); // Appeler setUserName pour définir le nom d'utilisateur
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/



    public void setId(User user) {
        this.user = user;
        if (user != null) {
            System.out.println("Utilisateur non nul");
            System.out.println("Nom d'utilisateur : " + user.getNom());
            idU.setText(Integer.toString(user.getId()));
            setUserName(user.getNom()); // Appeler setUserName une seule fois après avoir vérifié que user n'est pas nul
        } else {
            System.out.println("Utilisateur nul");
            idU.setText(""); // Effacer le texte de idU si user est nul
            System.out.println("Nom d'utilisateur : " + userName);
            userNameLabel.setText(""); // Effacer le texte de userNameLabel si user est nul
        }
        // Autres initialisations si nécessaire
    }


    /*
       @FXML
        private void openProfile(ActionEvent event) {
            userId = Integer.parseInt(idU.getText()); // Récupérer l'ID de l'utilisateur à partir de l'interface
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/userFrontProfile.fxml"));
                Parent parent = loader.load();

                // Accéder au contrôleur de la nouvelle fenêtre
                userFrontProfile controller = loader.getController();
                controller.setId(userId); // Définir l'ID de l'utilisateur dans le contrôleur
                Scene scene = userNameLabel.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/
    @FXML
    private void openProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/userFrontProfile.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            userFrontProfile controller = loader.getController();
            controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

            System.out.println("User sent to userFrontProfile: " + user.getNom()); // Message de débogage pour vérifier l'envoi de l'utilisateur

            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void setUserModel(User user) {
        this.user = user;
        currentUserField.setText(user.getNom());
        ServiceUser serviceUser = new ServiceUser();
        try {
            ServiceReclamation serviceReclamation = new ServiceReclamation();
            List<reclamation> reclamations=serviceReclamation.selectByUserId(user.getId());
            nbReclamationText.setText(reclamations.size()+" Reclamations");
            if(reclamations.isEmpty())
            {
                titreReclamationText.setText("No Reclamation was found");
                descriptionReclamation.setText("We are happy that you found our application working perfectly");
            }else{
                titreReclamationText.setText(reclamations.get(reclamations.size()-1).getType());
                descriptionReclamation.setText(reclamations.get(reclamations.size()-1).getDescriRec());
                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            userPhoto.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));


    }


    @FXML
    private void openAbonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/FrontAbonnement.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            FrontAbonnement controller = loader.getController();
            controller.setUserModel(user); // Envoyer l'utilisateur au contrôleur de FrontAbonnement
            System.out.println("envoi succes");
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setUserName(String userName) {
        System.out.println("Nom d'utilisateur défini : " + userName);
        this.userNameLabel.setText(userName);

    }

    public void setUser(User user) {
        System.out.println(user);
        this.user = user;
        Advice advice = new Advice();
        try {
            dailyAdvicesText.setText(advice.callAdviceAPI());
            if (user != null) {
                System.out.println("trouve");
                userNameLabel.setText(user.getNom());
            } else {
                System.out.println("Utilisateur non trouvé");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ServiceUser serviceUser = new ServiceUser();

        try {
            userPhoto.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));
        try {
            ServiceReclamation serviceReclamation = new ServiceReclamation();
            List<reclamation> reclamations=serviceReclamation.selectByUserId(user.getId());
            System.out.println("User:"+user.getId());
            System.out.println(reclamations);
            nbReclamationText.setText(reclamations.size()+" Reclamations");
            if(reclamations.isEmpty())
            {
                titreReclamationText.setText("No Reclamation was found");
                descriptionReclamation.setText("We are happy that you found our application working perfectly");
            }else{
                titreReclamationText.setText(reclamations.get(reclamations.size()-1).getType());
                descriptionReclamation.setText(reclamations.get(reclamations.size()-1).getDescriRec());
                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int getNbrProductInPannier(int id_user) {
        int nbr = 0;

        ServicePanier sp = new ServicePanier();

        try {
            Panier panier = sp.selectByUserId(id_user).get(0);
            if(panier == null)
                return nbr;

            ServicePanierProduit Spp = new ServicePanierProduit();

            List<PanierProduit> panierProduits = Spp.selectByPanierId(panier.getId());

            for(PanierProduit panierProduit : panierProduits) {
                nbr += panierProduit.getQuantite();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException e){
            return 0;
        }

        return nbr;
    }

    public void openGym(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/GymCabine/userGym.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            UserGym controller = loader.getController();
            controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

            System.out.println("User sent to userFrontProfile: " + user.getNom()); // Message de débogage pour vérifier l'envoi de l'utilisateur

            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openProducts(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/ProduitPanier/userFrontGymProduit.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            userFrontGymProduit controller = loader.getController();
            controller.setUserModel(user); // Envoyer l'utilisateur au contrôleur de FrontAbonnement
            System.out.println("envoi succes");
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openEvents(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EventsReclamation/userFrontEvent.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            userFrontEvent controller = loader.getController();
            controller.setUserModel(user); // Envoyer l'utilisateur au contrôleur de FrontAbonnement
            System.out.println("envoi succes");
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openReclamation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EventsReclamation/userFrontReclamation.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            userFrontReclamationFxml controller = loader.getController();
            controller.setUserModel(user); // Envoyer l'utilisateur au contrôleur de FrontAbonnement
            System.out.println("envoi succes");
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification/Main.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            Stage currentScene = (Stage) userNameLabel.getScene().getWindow();
            currentScene.close();
            Stage stage = new Stage();
            stage.setScene(parent.getScene());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        assert currentUserField != null : "fx:id=\"currentUserField\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert dailyAdvicesText != null : "fx:id=\"dailyAdvicesText\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert descriptionReclamation != null : "fx:id=\"descriptionReclamation\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert eventOneDateText != null : "fx:id=\"eventOneDateText\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert eventOneTitleText != null : "fx:id=\"eventOneTitleText\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert eventThreeDate != null : "fx:id=\"eventThreeDate\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert eventThreeTitle != null : "fx:id=\"eventThreeTitle\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert eventTwoDateText != null : "fx:id=\"eventTwoDateText\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert eventTwoTitleText != null : "fx:id=\"eventTwoTitleText\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert idU != null : "fx:id=\"idU\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert longestWorkoutDuration != null : "fx:id=\"longestWorkoutDuration\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert nbrPannier != null : "fx:id=\"nbrPannier\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert titreReclamationText != null : "fx:id=\"titreReclamationText\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert userNameLabel != null : "fx:id=\"userNameLabel\" was not injected: check your FXML file 'UserFrontHome.fxml'.";
        assert userPhoto != null : "fx:id=\"userPhoto\" was not injected: check your FXML file 'UserFrontHome.fxml'.";

        try {
            servicesCours servicesCours = new servicesCours();
            List<cours> cours = servicesCours.selectAll();
            int totalDurationInMin = 0;
            int numberOfCours = cours.size();

            for(cours cour : cours) {
                totalDurationInMin += cour.getDuree();
            }

            double averageDurationInMin = (double) totalDurationInMin / numberOfCours;
            longestWorkoutDuration.setText(totalDurationInMin+" minutes");
            averageWorkoutDuration.setText(averageDurationInMin+" minutes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
