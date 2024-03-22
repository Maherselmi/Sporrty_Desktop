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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Materiel;
import models.Panier;
import models.PanierProduit;
import models.User;
import services.ServiceMateriel;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class userFrontGymStockMateriel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label userNameLabel;

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
    private Button btn_workbench112;

    @FXML
    private Button btn_workbench12;

    @FXML
    private Button btn_workbench131;

    @FXML
    private Button btn_workbench1311;

    @FXML
    private Button btn_workbench13111;

    @FXML
    private Pagination pagination;

    @FXML
    private Label nbrPannier;

    @FXML
    private Pane pane_1111;

    @FXML
    private Pane pane_11111;

    @FXML
    private Pane pane_111111;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;
    private User user;

    @FXML
    private ImageView userPhoto;
    @FXML
    void openStockMateriel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/StockMateriel/userFrontGymStockMateriel.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = side_ankerpane.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

            // Appeler showStock ici pour afficher la pagination
            showStock();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void initialize() {
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench131 != null : "fx:id=\"btn_workbench131\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench1311 != null : "fx:id=\"btn_workbench1311\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert btn_workbench13111 != null : "fx:id=\"btn_workbench13111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert pane_1111 != null : "fx:id=\"pane_1111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert pane_11111 != null : "fx:id=\"pane_11111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert pane_111111 != null : "fx:id=\"pane_111111\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";




        showStock();
    }

    private void showStock() {
        try {
            ServiceMateriel serviceMateriel = new ServiceMateriel();
            List<Materiel> materielList = serviceMateriel.selectAll();

            int itemsPerPage = 3;
            int pageSize = (materielList.size() + itemsPerPage - 1) / itemsPerPage;

            pagination.setPageCount(pageSize);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, materielList));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement du stock");
            alert.setContentText("Une erreur est survenue lors du chargement du stock de matériel. Veuillez réessayer plus tard.");
            alert.show();
        }
    }

    private AnchorPane createPage(int pageIndex, List<Materiel> materielList) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(952, 167);

        int itemsPerPage = 3;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, materielList.size());

        int offsetY = 0;
        for (int i = startIndex; i < endIndex; i++) {
            Materiel materiel = materielList.get(i);

            Pane materielPane = new Pane();
            materielPane.setLayoutY(offsetY);
            materielPane.setPrefSize(200, 167);
            materielPane.setStyle(
                    "-fx-background-color: #5bc0de;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-border-style: solid;");
            anchorPane.getChildren().add(materielPane);

            // Bouton Details
                Button detailsButton = new Button("Details");
            detailsButton.setLayoutX(20);
            detailsButton.setLayoutY(20);
            detailsButton.setOnAction(event -> showDetails(materiel));
            materielPane.getChildren().add(detailsButton);

            Label nameLabel = new Label(materiel.getNom());
            nameLabel.setLayoutX(240);
            nameLabel.setLayoutY(14);
            nameLabel.setPrefWidth(400);
            nameLabel.setStyle("-fx-text-fill: white;");
            nameLabel.setFont(Font.font("Century Gothic Bold", 17));
            materielPane.getChildren().add(nameLabel);

            Label descriptionLabel = new Label(materiel.getNom());
            descriptionLabel.setLayoutX(240);
            descriptionLabel.setLayoutY(40);
            descriptionLabel.setPrefWidth(400);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setFont(Font.font("Century Gothic Bold", 17));
            descriptionLabel.setWrapText(true);
            materielPane.getChildren().add(descriptionLabel);

            Label categoryLabel = new Label("Catégorie: " + materiel.getCategorie());
            categoryLabel.setLayoutX(240); // Adjust the X position as needed
            categoryLabel.setLayoutY(70); // Adjust the Y position as needed
            categoryLabel.setPrefWidth(400);
            categoryLabel.setStyle("-fx-text-fill: white;");
            categoryLabel.setFont(Font.font("Century Gothic Bold", 17));
            categoryLabel.setWrapText(true);
            materielPane.getChildren().add(categoryLabel);

            Label stockLabel = new Label("Stock: " + materiel.getId_stock());
            stockLabel.setLayoutX(680);
            stockLabel.setLayoutY(120);
            stockLabel.setPrefWidth(400);
            stockLabel.setStyle("-fx-text-fill: white;");
            stockLabel.setFont(Font.font("Century Gothic Bold", 17));
            stockLabel.setWrapText(true);
            materielPane.getChildren().add(stockLabel);

            Label qteLabel = new Label("Quantité: " + materiel.getQte());
            qteLabel.setLayoutX(680); // Adjust the X position as needed
            qteLabel.setLayoutY(70); // Adjust the Y position as needed
            qteLabel.setPrefWidth(400);
            qteLabel.setStyle("-fx-text-fill: white;");
            qteLabel.setFont(Font.font("Century Gothic Bold", 17));
            qteLabel.setWrapText(true);
            materielPane.getChildren().add(qteLabel);

            offsetY += 180;
        }

        return anchorPane;
    }

    // Méthode pour afficher les détails du matériel
    private void showDetails(Materiel materiel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/StockMateriel/userFrontGymCours.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage stage = (Stage) btn_workbench12.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur convenablement
        }
    }


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

