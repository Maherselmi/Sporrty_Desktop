package controllers.Front.GymCabine;

import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.ProduitPanier.userFrontGymProduit;
import controllers.Front.UserAbonement.userFrontProfile;
import controllers.Front.UserFrontHome;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Panier;
import models.PanierProduit;
import models.SalleDeSport;
import models.User;
import net.glxn.qrgen.QRCode;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceSalle;
import services.ServiceUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserGym {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<String> filterr;
    @FXML
    private URL location;

    @FXML
    private Label userNameLabel;
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
    private TextField searcch;

    @FXML
    private Pagination pagination;

    @FXML
    private Pane pane_1111;

    @FXML
    private Pane pane_11111;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;
    @FXML
    private Pane pane_111111;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;
    private User user;

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
            showSalle();

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
        showSalle();
    }

    private AnchorPane createPage(int pageIndex, List<SalleDeSport> sallelist) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(952, 167);
        int itemsPerPage = 3;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, sallelist.size());
        int offsetY = 0;
        for (int i = startIndex; i < endIndex; i++) {
            SalleDeSport s = sallelist.get(i);
            Pane sallePane = new Pane();
            sallePane.setLayoutY(offsetY);
            sallePane.setPrefSize(952, 167);
            sallePane.setStyle(
                    "-fx-background-color: #5bc0de;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-border-style: solid;");
            anchorPane.getChildren().add(sallePane);
            if(!Objects.equals(s.getLienVideo(), ""))
            {
                String qrText = s.getLienVideo();
                byte[] qrCodeBytes = QRCode.from(qrText).stream().toByteArray();
                try {
                    File tempFile = File.createTempFile("qrcode", ".png");
                    FileOutputStream outputStream = new FileOutputStream(tempFile);
                    outputStream.write(qrCodeBytes);
                    outputStream.close();
                    Image qrImage = new Image(tempFile.toURI().toString());

                    ImageView imageViewQr = new ImageView(qrImage);
                    imageViewQr.setLayoutX(780);
                    imageViewQr.setLayoutY(80);
                    imageViewQr.setFitWidth(100);
                    imageViewQr.setFitHeight(80);
                    sallePane.getChildren().add(imageViewQr);
                    tempFile.deleteOnExit();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

            // Bouton Details
            Button detailsButton = new Button("View More");
            detailsButton.setLayoutX(760);
            detailsButton.setLayoutY(20);
            detailsButton.setStyle("-fx-border-width: 2px 2px 2px 2px;" +
                    "    -fx-border-radius: 50px;" +
                    "    -fx-background-radius: 50px;" +
                    "    -fx-border-style: solid;" +
                    "    -fx-border-color: black;" +
                    "-fx-min-width: 132;" +
                    "-fx-min-height: 43");
            detailsButton.setOnMouseClicked(event->{
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/GymCabine/userFrontGymCours.fxml"));
                    Parent parent = loader.load(); // charger l interface dans la variable parent
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) btn_workbench111.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    userFrontGymCours controller = loader.getController();
                    controller.setGym(s);
                    controller.setUserModel(user);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            sallePane.getChildren().add(detailsButton);
            Label nameLabel = new Label(s.getNom_salle());
            nameLabel.setLayoutX(240);
            nameLabel.setLayoutY(14);
            nameLabel.setPrefWidth(200);
            nameLabel.setStyle("-fx-text-fill: white;");
            nameLabel.setFont(Font.font("Century Gothic Bold", 17));
            sallePane.getChildren().add(nameLabel);

            Label LieuLabel = new Label(s.getLieu_salle());
            LieuLabel.setLayoutX(240);
            LieuLabel.setLayoutY(40);
            LieuLabel.setPrefWidth(400);
            LieuLabel.setStyle("-fx-text-fill: white;");
            LieuLabel.setFont(Font.font("Century Gothic Bold", 17));
            LieuLabel.setWrapText(true);
            sallePane.getChildren().add(LieuLabel);

            Label DescriptionLabel = new Label("description: " + s.getDescr());
            DescriptionLabel.setLayoutX(240); // Adjust the X position as needed
            DescriptionLabel.setLayoutY(70); // Adjust the Y position as needed
            DescriptionLabel.setPrefWidth(400);
            DescriptionLabel.setStyle("-fx-text-fill: white;");
            DescriptionLabel.setFont(Font.font("Century Gothic Bold", 17));
            DescriptionLabel.setWrapText(true);
            sallePane.getChildren().add(DescriptionLabel);

            Label NumLabel = new Label("numero: " + s.getNum_salle());
            NumLabel.setLayoutX(240);
            NumLabel.setLayoutY(100);
            NumLabel.setPrefWidth(400);
            NumLabel.setStyle("-fx-text-fill: white;");
            NumLabel.setFont(Font.font("Century Gothic Bold", 17));
            NumLabel.setWrapText(true);
            sallePane.getChildren().add(NumLabel);


            ImageView imageView = new ImageView(new Image("@../../assets/images/gymIllustration.png"));
            if(!Objects.equals(s.getImage(), ""))
            {
                imageView = new ImageView(new Image("@../../upload/"+s.getImage()));
            }
            imageView.setLayoutX(50);
            imageView.setLayoutY(5);
            imageView.setFitWidth(139);
            imageView.setFitHeight(141);
            sallePane.getChildren().add(imageView);
            offsetY += 180;

        }
        return anchorPane;
    }

    private void showSalle() {
        try {
            ServiceSalle  s= new ServiceSalle();
            List<SalleDeSport> salleDeSportList = s.selectAll();

            int itemsPerPage = 3;
            int pageSize = (salleDeSportList.size() + itemsPerPage - 1) / itemsPerPage;

            pagination.setPageCount(pageSize);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, salleDeSportList));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement du stock");
            alert.setContentText("Une erreur est survenue lors du chargement du stock de matériel. Veuillez réessayer plus tard.");
            alert.show();
        }
    }

    @FXML
    void rechercheGym(ActionEvent event) {
        String nomRecherche = searcch.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                ServiceSalle service = new ServiceSalle();
                List<SalleDeSport> sallelist = service.searchByNom(nomRecherche); // Appeler une méthode de service pour rechercher par nom

                // Vérifier si la liste des cabines est vide
                if (sallelist.isEmpty()) {
                    // Afficher une alerte indiquant que la cabine n'existe pas
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Salle non trouvée");
                    alert.setHeaderText(null);
                    alert.setContentText("Aucune salle ne correspond au nom saisi.");
                    alert.show();
                } else {
                    // Mise à jour de la pagination avec les résultats de la recherche
                    int itemsPerPage = 3;
                    int pageSize = (sallelist.size() + itemsPerPage - 1) / itemsPerPage;
                    pagination.setPageCount(pageSize);
                    pagination.setPageFactory(pageIndex -> createPage(pageIndex, sallelist));
                }
            } catch (SQLException e) {
                // Gérer les erreurs de manière appropriée
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la recherche");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de la recherche. Veuillez réessayer plus tard.");
                alert.show();
            }
        } else {
            // Si le champ de recherche est vide, afficher un message à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ de recherche vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir le nom de la cabine à rechercher.");
            alert.show();
        }
    }

    @FXML
    void openhome(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                UserFrontHome controller = loader.getController();
                controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

                // Récupérer la scène actuelle
                Scene scene =btn_workbench1.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }





    }

    @FXML
    void openproduct(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/ProduitPanier/userFrontGymProduit.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                userFrontGymProduit controller = loader.getController();
                controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

                // Récupérer la scène actuelle
                Scene scene =btn_workbench111.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }
    @FXML
    void openevent(ActionEvent event) {
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



    @FXML
    void openraclamation(ActionEvent event) {
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
    public void setUserModel(User user) {
        this.user = user;

        // Utilisez l'utilisateur chargé pour initialiser votre interface utilisateur
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