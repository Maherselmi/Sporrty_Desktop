package controllers.Front.EventsReclamation;

import controllers.Front.GymCabine.UserGym;
import controllers.Front.ProduitPanier.userFrontGymProduit;
import controllers.Front.UserAbonement.FrontAbonnement;
import controllers.Front.UserAbonement.userFrontProfile;
import controllers.Front.UserFrontHome;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Panier;
import models.PanierProduit;
import models.User;
import models.reclamation;
import org.controlsfx.control.Rating;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceReclamation;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class userFrontReclamationFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button RecMan;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;

    @FXML
    private Label errorGeneralLabel;

    @FXML
    private HBox root;
    @FXML
    private Button UserMan;
    @FXML
    private AnchorPane side_ankerpane;

  /*  @FXML
    private DatePicker tfDate;*/

    @FXML
    private TextArea tfDescri;

    @FXML
    private Rating tfEtoile;
    @FXML
    private TextField ratingId;

    @FXML
    private Label dateErrorLabel;
    @FXML
    private Label typeErrorLabel;
    @FXML
    private Label descriErrorLabel;
    @FXML
    private Label ratingErrorLabel;


    @FXML
    private Button tfSend;
    @FXML
    private Label idU;
@FXML
private Label  userNameLabel;
    @FXML
    private ComboBox<String> TypeCombo;
    private int userId;
    private User user;


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

    //avec controle de saisie
   @FXML
    void envoyer(ActionEvent event) {
        // Réinitialiser les messages d'erreur
        typeErrorLabel.setText("");
        descriErrorLabel.setText("");

        ratingErrorLabel.setText("");

        try {
            // Vérification des champs obligatoires
            if (TypeCombo.getValue() == null) {
                typeErrorLabel.setText("Please enter the type of complaint.");
            }
            if (tfDescri.getText().isEmpty()) {
                descriErrorLabel.setText("Please enter the description.");
            }

            if (ratingId.getText().isEmpty()) {
                ratingErrorLabel.setText("Please enter a rating between 1 and 5");
            }

            // Vérification s'il y a des messages d'erreur
            if (!typeErrorLabel.getText().isEmpty() || !descriErrorLabel.getText().isEmpty() || !ratingErrorLabel.getText().isEmpty()) {
                errorGeneralLabel.setText("Veuillez remplir tous les champs obligatoires.");
                return; // Arrête l'exécution de la méthode si des champs obligatoires sont vides
            } else {
                // Assurez-vous de vider le message d'erreur général s'il n'y a pas d'erreur
                errorGeneralLabel.setText("");
            }

           String  type=TypeCombo.getValue();
            String tfEtoile=ratingId.getText();
            String statut="en attente";
            //reclamation rec = new reclamation (type, iduser,tfDescri.getText(),Date.valueOf(LocalDate.now()),tfEtoile ,statut);
            ServiceUser serviceUser = new ServiceUser();

            reclamation rec = new reclamation (type, user,tfDescri.getText(),tfEtoile ,statut);

            ServiceReclamation sp = new ServiceReclamation();

            sp.insertOne(rec);
            // Affichage d'une boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Votre réclamation a été envoyée avec succès!");

            confirmationAlert.showAndWait(); // Attend que l'utilisateur clique sur OK

            openHomeFront(event);
        } catch (SQLException e) {
            // Affichage d'un message d'erreur en cas d'échec de l'opération d'insertion
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }
    @FXML
    public void logoutClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/loginFxml.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert ratingId != null : "fx:id=\"ratingId\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        assert tfEtoile != null : "fx:id=\"tfEtoile\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";


        assert RecMan != null : "fx:id=\"RecMan\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
       // assert tfDate != null : "fx:id=\"tfDate\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        //tfDate.setValue(LocalDate.now());
        assert tfDescri != null : "fx:id=\"tfDescri\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        assert tfEtoile != null : "fx:id=\"tfEtoile\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        assert tfSend != null : "fx:id=\"tfSend\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        assert TypeCombo != null : "fx:id=\"TypeCombo\" was not injected: check your FXML file 'userFrontReclamation.fxml'.";
        ObservableList<String> type = FXCollections.observableArrayList("Payment Issue",
                "Equipment Problem",
                "Discomfort in Facilities",
                "Security Issue",
                "Reservation Issue",
                "Capacity Issue in Class",
                "Improvement Suggestions",
                "Others");
        TypeCombo.setItems(type);


        tfEtoile.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                String colorStyle = newValue.intValue() >= 4 ? "-fx-text-fill: green;" : "-fx-text-fill: red;";
                ratingId.setStyle(colorStyle);
                ratingId.setText("Rate us " + newValue.intValue() + "/5");
            }
        });

    }

    public void eventsClick(ActionEvent event) {

        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/EventsReclamation/userFrontEvent.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void homeClick(ActionEvent event) {

       try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/user.fxml"));
        Parent parent = loader.load(); // Charger l'interface dans le parent

        // Récupérer la scène actuelle
        Scene scene = userNameLabel.getScene();
        // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
        scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gymsClick(ActionEvent event) {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/GymCabine/userFrontGym.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void produitClick(ActionEvent event) {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/ProduitPanier/userFrontGymProduit.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void profileClick(ActionEvent event) {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/userFrontProfile.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
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
            System.out.println("Send success");
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
