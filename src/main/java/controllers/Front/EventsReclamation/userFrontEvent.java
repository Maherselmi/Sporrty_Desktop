package controllers.Front.EventsReclamation;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.*;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class userFrontEvent {

    @FXML
    private Button btnCalendar;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_workbench1;

    @FXML
    private Button btn_workbench11;

    @FXML
    private Button btn_workbench111;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111111;

    @FXML
    private Button RecMan;


    @FXML
    private Button btn_workbench12;

    @FXML
    private Button btn_workbench131;

    @FXML
    private Button btnHome;
    @FXML
    private Pagination pagination;

    @FXML
    private HBox root;
    @FXML
    private Label  userNameLabel;
    private int userId;


    @FXML
    private AnchorPane side_ankerpane;
    private List<evenements> events;

    int currentUserId = 0;
    @FXML
    private Label idU;
    private User user;

    @FXML
    void CalendarClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EventsReclamation/calendar1.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent


            // Récupérer la scène actuelle
            Scene scene = new Scene(parent);
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






    final int EVENTS_PER_PAGE = 6;

  /*  private AnchorPane createPage(int pageIndex) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(900);
        anchorPane.setPrefHeight(600);

        int startIndex = pageIndex * EVENTS_PER_PAGE;
        int endIndex = Math.min(startIndex + EVENTS_PER_PAGE, events.size());

        int xOffset = 0;
        int yOffset = 30;
        int xIncrement = 330;
        int yIncrement = 260;

        for (int i = startIndex; i < endIndex; i++) {
            evenements ev = events.get(i);

            AnchorPane productPane = new AnchorPane();
            productPane.setLayoutX(xOffset);
            productPane.setLayoutY(yOffset);
            productPane.setPrefSize(307, 234);
            productPane.setStyle(
                    "-fx-background-color: #5bc0de;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-border-style: solid;");

            Label nameLabel = new Label(ev.getNom());
            nameLabel.setLayoutX(18);
            nameLabel.setLayoutY(12);
            nameLabel.setPrefWidth(170);
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Nom en gras
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            nameLabel.setWrapText(true);
            productPane.getChildren().add(nameLabel);
            Label nbrParLabel = new Label("Participants: " + ev.getNbrP());
            nbrParLabel.setLayoutX(18); // Positionner le nombre de participants
            nbrParLabel.setLayoutY(130);
            nbrParLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Participants en gras
            nbrParLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add(nbrParLabel);



            Label descriptionLabel = new Label(ev.getDescri());
            descriptionLabel.setLayoutX(18);
            descriptionLabel.setLayoutY(50);
            descriptionLabel.setPrefWidth(270);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setFont(Font.font("Arial", 14)); // Augmenter la taille de la police
            descriptionLabel.setWrapText(true);
            productPane.getChildren().add(descriptionLabel);

            Label locDateLabel = new Label("Location: " + ev.getLieu() + " | Date: " + ev.getDate());
            locDateLabel.setLayoutX(18);
            locDateLabel.setLayoutY(90);
            locDateLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Location et Date en gras
            locDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add(locDateLabel);

            Button addButton = new Button("Participate");
            addButton.setLayoutX(200); // Positionner le bouton à droite du nom de l'événement
            addButton.setLayoutY(12);
            addButton.setPrefWidth(100); // Ajuster la largeur du bouton
            addButton.setStyle(
                    "-fx-background-color: #f3fafa;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 50px;" +
                            "-fx-background-radius: 50px;" +
                            "-fx-border-style: solid;" +
                            "-fx-border-color: black;");
            productPane.getChildren().add(addButton);
            // Récupérer l'événement actuel
            evenements currentEvent = ev;

            addButton.setOnAction(event -> {
                // Désactiver le bouton après le premier clic
                addButton.setDisable(true);
                // Appeler la méthode pour incrémenter le nombre de participants
                currentEvent.participateEvent();
                // Mettre à jour l'affichage du nombre de participants
                nbrParLabel.setText("Participants: " + currentEvent.getNbrP());
            });

            Label categoryLabel = new Label("Category: " + ev.getCategorie());
            categoryLabel.setLayoutX(18);
            categoryLabel.setLayoutY(170); // Positionner la catégorie sous les participants
            categoryLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Category en gras
            categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add(categoryLabel);

            xOffset += xIncrement;
            if ((i + 1) % 3 == 0) {
                xOffset = 0;
                yOffset += yIncrement;
            }

            anchorPane.getChildren().add(productPane);
        }
        return anchorPane;
    }*/

    private AnchorPane createPage(int pageIndex) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(900);
        anchorPane.setPrefHeight(600);

        int startIndex = pageIndex * EVENTS_PER_PAGE;
        int endIndex = Math.min(startIndex + EVENTS_PER_PAGE, events.size());

        int xOffset = 0;
        int yOffset = 30;
        int xIncrement = 330;
        int yIncrement = 260;

        for (int i = startIndex; i < endIndex; i++) {
            evenements ev = events.get(i);

            AnchorPane productPane = new AnchorPane();
            productPane.setLayoutX(xOffset);
            productPane.setLayoutY(yOffset);
            productPane.setPrefSize(307, 234);
            productPane.setStyle(
                    "-fx-background-color: #5bc0de;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-border-style: solid;");

            Label nameLabel = new Label(ev.getNom());
            nameLabel.setLayoutX(18);
            nameLabel.setLayoutY(12);
            nameLabel.setPrefWidth(170);
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Nom en gras
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            nameLabel.setWrapText(true);
            productPane.getChildren().add(nameLabel);

            Label nbrParLabel = new Label("Participants: " + ev.getNbrP());
            nbrParLabel.setLayoutX(18); // Positionner le nombre de participants
            nbrParLabel.setLayoutY(130);
            nbrParLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Participants en gras
            nbrParLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add(nbrParLabel);

            Label descriptionLabel = new Label(ev.getDescri());
            descriptionLabel.setLayoutX(18);
            descriptionLabel.setLayoutY(50);
            descriptionLabel.setPrefWidth(270);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setFont(Font.font("Arial", 14)); // Augmenter la taille de la police
            descriptionLabel.setWrapText(true);
            productPane.getChildren().add(descriptionLabel);

            Label locDateLabel = new Label("Location: " + ev.getLieu() + " | Date: " + ev.getDate());
            locDateLabel.setLayoutX(18);
            locDateLabel.setLayoutY(90);
            locDateLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Location et Date en gras
            locDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add(locDateLabel);
            /*
            if (ev.getDate().toLocalDate().isAfter(LocalDate.now())) {
                Button addButton = new Button("Participate");
                addButton.setLayoutX(200); // Positionner le bouton à droite du nom de l'événement
                addButton.setLayoutY(12);
                addButton.setPrefWidth(100); // Ajuster la largeur du bouton
                addButton.setStyle(
                        "-fx-background-color: #f3fafa;" +
                                "-fx-border-width: 2px 2px 2px 2px;" +
                                "-fx-border-radius: 50px;" +
                                "-fx-background-radius: 50px;" +
                                "-fx-border-style: solid;" +
                                "-fx-border-color: black;");
                productPane.getChildren().add(addButton);

                final evenements currentEvent = ev; // Variable finale pour stocker l'événement actuel

                addButton.setOnAction(event -> {
                    try {
                        // Créer une nouvelle instance de Participation avec les informations nécessaires
                        Participation participation = new Participation();
                        participation.setUser(user);
                        participation.setEvenement(ev);


                        // Insérer la participation dans la base de données
                        ServiceParticipation sp = new ServiceParticipation();
                        sp.insertOne(participation);
                        currentEvent.participateEvent(); // Met à jour le nombre de participants dans l'objet evenements
                        ServiceEvenement serviceEvenement = new ServiceEvenement(); // Créez une instance de ServiceEvenement
                        serviceEvenement.updateNbrParticipants(currentEvent.getId(), currentEvent.getNbrP()); // Met à jour la base de données
                        nbrParLabel.setText("Participants: " + currentEvent.getNbrP());
                        addButton.setDisable(true);
                    } catch (SQLException e) {
                        // Gérer les erreurs de base de données
                    }
                });

            }*/
            // Vérifier si l'événement est à venir et afficher le bouton Participate si c'est le cas
            if (ev.getDate().toLocalDate().isAfter(LocalDate.now())) {
                Button participateButton = new Button("Participate");
                participateButton.setLayoutX(200); // Positionner le bouton à droite du nom de l'événement
                participateButton.setLayoutY(12);
                participateButton.setPrefWidth(100); // Ajuster la largeur du bouton
                participateButton.setStyle(
                        "-fx-background-color: #f3fafa;" +
                                "-fx-border-width: 2px 2px 2px 2px;" +
                                "-fx-border-radius: 50px;" +
                                "-fx-background-radius: 50px;" +
                                "-fx-border-style: solid;" +
                                "-fx-border-color: black;");

                final evenements currentEvent = ev; // Variable finale pour stocker l'événement actuel

                participateButton.setOnAction(event -> {
                    try {
                        // Créer une nouvelle instance de Participation avec les informations nécessaires
                        Participation participation = new Participation();
                        participation.setUser(user);
                        participation.setEvenement(ev);

                        // Insérer la participation dans la base de données
                        ServiceParticipation sp = new ServiceParticipation();
                        sp.insertOne(participation);
                        currentEvent.participateEvent(); // Met à jour le nombre de participants dans l'objet evenements
                        ServiceEvenement serviceEvenement = new ServiceEvenement(); // Créez une instance de ServiceEvenement
                        serviceEvenement.updateNbrParticipants(currentEvent.getId(), currentEvent.getNbrP()); // Met à jour la base de données
                        nbrParLabel.setText("Participants: " + currentEvent.getNbrP());
                        participateButton.setDisable(true);
                    } catch (SQLException e) {
                        // Gérer les erreurs de base de données
                    }
                });

                // Ajouter le bouton à votre interface utilisateur
                productPane.getChildren().add(participateButton);
            }


            Label categoryLabel = new Label("Category: " + ev.getCategorie());
            categoryLabel.setLayoutX(18);
            categoryLabel.setLayoutY(170); // Positionner la catégorie sous les participants
            categoryLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Category en gras
            categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add(categoryLabel);

            xOffset += xIncrement;
            if ((i + 1) % 3 == 0) {
                xOffset = 0;
                yOffset += yIncrement;
            }

            anchorPane.getChildren().add(productPane);
        }
        return anchorPane;
    }





    public void setId(int id) {
        userId = id;
        try {
            ServiceUser serviceUser = new ServiceUser();
            User user = serviceUser.selectById(userId); // Utilisez directement l'ID passé en paramètre
            if (user != null) {
                userNameLabel.setText(user.getNom()); // Afficher l'ID de l'utilisateur
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void recClick(ActionEvent event) {

        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EventsReclamation/userFrontReclamation.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = RecMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert btnCalendar != null : "fx:id=\"btnCalendar\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert w != null : "fx:id=\"w\" was not injected: check your FXML file 'userFrontEvent.fxml'.";

        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert btn_workbench131 != null : "fx:id=\"btn_workbench131\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontEvent.fxml'.";
        try {
            ServiceEvenement se = new ServiceEvenement();
            events = se.selectAll();
            int pageSize = events.size() / 6;
            if (events.size() % 6 != 0) {
                pageSize++;
            }
            pagination.setPageCount(pageSize);
            pagination.setPageFactory(this::createPage);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    @FXML
    private Button w;
    @FXML
    void weatherCliked(ActionEvent event) {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EventsReclamation/weather1.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            Scene scene = new Scene(parent);
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;
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
}
