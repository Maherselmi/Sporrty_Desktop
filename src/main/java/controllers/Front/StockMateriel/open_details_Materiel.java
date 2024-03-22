package controllers.Front.StockMateriel;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Materiel;
import models.Panier;
import models.PanierProduit;
import models.User;
import net.glxn.qrgen.QRCode;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class open_details_Materiel {


    @FXML
    private ResourceBundle resources;

    @FXML
    private Label userNameLabel;
    @FXML
    private Label sub;
    @FXML
    private Label title;
    @FXML
    private URL location;

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
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111111;

    @FXML
    private Button btn_workbench112;

    @FXML
    private Button btn_workbench12;

    @FXML
    private ImageView imageMateriel;

    @FXML
    private ImageView qrCodeMateriel;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;
    private User user;

    @FXML
    void initialize() {
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'Details.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'Details.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'Details.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'Details.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'Details.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'Details.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'Details.fxml'.";
        assert imageMateriel != null : "fx:id=\"imageMateriel\" was not injected: check your FXML file 'Details.fxml'.";
        assert qrCodeMateriel != null : "fx:id=\"qrCode\" was not injected: check your FXML file 'Details.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'Details.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'Details.fxml'.";

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
    // Méthode pour recevoir les détails du matériel et les afficher dans les étiquettes
    public void setMateriel(Materiel materiel) {
        Image image = null;
        if(!Objects.equals(materiel.getImage(), ""))
         image = new Image("/upload/"+materiel.getImage());
        if (image != null) {
            imageMateriel.setImage(image);
        }

        imageMateriel.setImage(image);
        title.setText(materiel.getNom());
        sub.setText(materiel.getCategorie());
        createQrCode(materiel);
    }

    public void createQrCode (Materiel materiel){
        if(!Objects.equals(materiel.getVideo(), ""))
        {
            String qrText = materiel.getVideo();
            byte[] qrCodeBytes = QRCode.from(qrText).stream().toByteArray();
            try {
                File tempFile = File.createTempFile("qrcode", ".png");
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                outputStream.write(qrCodeBytes);
                outputStream.close();
                Image qrImage = new Image(tempFile.toURI().toString());

                qrCodeMateriel.setImage(qrImage);
                tempFile.deleteOnExit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


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
    private void openHome(ActionEvent event) {
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

