package controllers.Front.CoursProgram;


import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.GymCabine.UserGym;
import controllers.Front.ProduitPanier.userFrontGymProduit;
import controllers.Front.UserAbonement.FrontAbonnement;
import controllers.Front.UserAbonement.userFrontProfile;
import controllers.Front.UserFrontHome;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.*;
import org.controlsfx.control.Notifications;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceUser;
import services.servicesProgramme;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class userFrontGymCoursProgram {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox root;


    @FXML
    private Label userNameLabel;
    @FXML
    private AnchorPane side_ankerpane;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;
    @FXML
    private Button btn_workbench1;

    @FXML
    private Button btn_workbench11;

    @FXML
    private Button btn_workbench111;

    @FXML
    private Button btn_workbench12;

    @FXML
    private Button btn_workbench112;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111111;

    @FXML
    private Pane pane_132;

    @FXML
    private Button btn_workbench13;

    @FXML
    private Pagination paginationProg;
    private List<programme> programmeList;
    private List<cours> courslist;

    int currentUserId = 0;

    final int ProgPER_PAGE = 6;
    private User user;


    private AnchorPane createPage(int pageIndex) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(900);
        anchorPane.setPrefHeight(600);

        int startIndex = pageIndex * ProgPER_PAGE;
        int endIndex = Math.min(startIndex + ProgPER_PAGE, programmeList.size());
        int xOffset = 0;
        int yOffset = 30;
        int xIncrement = 300;
        int yIncrement = 260;

        for (int i = startIndex; i < endIndex; i++) {
           programme pv = programmeList.get(i);

            AnchorPane productPane = new AnchorPane();
            productPane.setLayoutX(xOffset);
            productPane.setLayoutY(yOffset);
            productPane.setPrefSize(267, 234);
            productPane.setStyle(
                    "-fx-background-color: #5bc0de;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-border-style: solid;");



            Label nameLabel = new Label(pv.getNom());
            nameLabel.setLayoutX(18);
            nameLabel.setLayoutY(12);
            nameLabel.setPrefWidth(170);
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Nom en gras
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Augmenter la taille de la police
            nameLabel.setWrapText(true);
            productPane.getChildren().add(nameLabel);



            Label descriptionLabel = new Label(pv.getDescription());
            descriptionLabel.setLayoutX(18);
            descriptionLabel.setLayoutY(50);
            descriptionLabel.setPrefWidth(270);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setFont(Font.font("Arial", 18)); // Augmenter la taille de la police
            descriptionLabel.setWrapText(true);
            productPane.getChildren().add(descriptionLabel);

            Label durationLabel = new Label(" Duration: " + pv.getDuree());
            durationLabel.setLayoutX(18);
            durationLabel.setLayoutY(180);
            durationLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Location et Date en gras
            durationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Augmenter la taille de la police
            productPane.getChildren().add(durationLabel);



            xOffset += xIncrement;
            if ((i + 1) % 3 == 0) {
                xOffset = 0;
                yOffset += yIncrement;
            }

            anchorPane.getChildren().add(productPane);
        }

        return anchorPane;



    }

        @FXML
        void initialize () {
            assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert pane_132 != null : "fx:id=\"pane_132\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";
            assert paginationProg != null : "fx:id=\"paginationProg\" was not injected: check your FXML file 'userFrontGymCoursProgram.fxml'.";

            try {
              servicesProgramme sc = new servicesProgramme();
               programmeList = sc.selectAll();
                int pageSize = programmeList.size() / 6;
                if (programmeList.size() % 6 != 0) {
                    pageSize++;
                }
                paginationProg.setPageCount(pageSize);
                paginationProg.setPageFactory(this::createPage);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

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
        if (user != null) {
            System.out.println("trouve");
            userNameLabel.setText(user.getNom());
        } else {
            System.out.println("Utilisateur non trouvé");
        }
        ServiceUser serviceUser = new ServiceUser();

        try {
            userPhoto.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));
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
    public void openGyms(ActionEvent actionEvent) {
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
    @FXML
    private void openHomeFront(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            UserFrontHome controller = loader.getController();
            controller.setUserModel(user);  // Définir l'ID de l'utilisateur dans le contrôleur

            // Passer le nom de l'utilisateur au contrôleur
            controller.setUserName(userNameLabel.getText());

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

    public void Participate(ActionEvent actionEvent) {
        String phoneNumber = "+21651252843";
        sms.sendSMS(phoneNumber, "Your registration has been recorded.Thank You!");

        Notifications.create()
                .title("Registration Success!")
                .text("Check your phone for the sms confirmation")
                .showInformation();
    }
}


