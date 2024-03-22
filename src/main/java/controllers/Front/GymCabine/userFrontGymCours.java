package controllers.Front.GymCabine;

import controllers.Front.CoursProgram.userFrontGymCoursProgram;
import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.ProduitPanier.userFrontGymProduit;
import controllers.Front.StockMateriel.open_details_Materiel;
import controllers.Front.UserAbonement.FrontAbonnement;
import controllers.Front.UserAbonement.userFrontProfile;
import controllers.Front.UserFrontHome;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import models.*;
import net.glxn.qrgen.QRCode;
import services.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class userFrontGymCours {

    @FXML
    private TextField searcch;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox root;
    @FXML
    private Pagination pagination;
    @FXML
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;
    private List<cours> coursList;

    @FXML
    private Pagination paginationCours;
    @FXML
    private Label userNameLabel;

    @FXML
    private AnchorPane side_ankerpane;

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
    private Pane pane_1111;

    @FXML
    private Pane pane_111111111;

    @FXML
    private Pane pane_132;

    @FXML
    private Label gymTitleText;

    @FXML
    private Label gymDescriptionText;

    @FXML
    private Label gymLocationText;

    @FXML
    private Button btn_workbench13;

    @FXML
    private Pane pane_11111;

    @FXML
    private Pane pane_13;

    @FXML
    private Pane pane_131;

    @FXML
    private Pane pane_1311;

    @FXML
    private Button btn_workbench131;
    private SalleDeSport salleDeSport;
    private User user;

    @FXML
    void openGymManament(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/GymCabine/userGym.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) userNameLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            UserGym controller = loader.getController();
            controller.setUserModel(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        anchorPane.setPrefSize(307, 78);

        int itemsPerPage = 3;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, materielList.size());

        int offsetY = 0;
        for (int i = startIndex; i < endIndex; i++) {
            Materiel materiel = materielList.get(i);

            Pane materielPane = new Pane();
            materielPane.setLayoutY(offsetY);
            materielPane.setPrefSize(307, 78);
            materielPane.setStyle(
                    "-fx-background-color: #5bc0de;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-border-style: solid;");
            anchorPane.getChildren().add(materielPane);

            Button detailsButton = new Button("Details");
            detailsButton.setLayoutX(20);
            detailsButton.setLayoutY(20);
            detailsButton.setUserData(materiel); // Associer le matériel au bouton
            detailsButton.setOnAction(this::handleDetailsButton); // Ajouter le gestionnaire d'événements
            materielPane.getChildren().add(detailsButton);
            Label nameLabel = new Label(materiel.getNom());
            nameLabel.setLayoutX(120);
            nameLabel.setLayoutY(20);
            nameLabel.setPrefWidth(400);
            nameLabel.setStyle("-fx-text-fill: white;");
            nameLabel.setFont(Font.font("Century Gothic Bold", 17));
            materielPane.getChildren().add(nameLabel);

            Label categoryLabel = new Label("Catégorie: " + materiel.getCategorie());
            categoryLabel.setLayoutX(160); // Adjust the X position as needed
            categoryLabel.setLayoutY(50); // Adjust the Y position as needed
            categoryLabel.setPrefWidth(400);
            categoryLabel.setStyle("-fx-text-fill: white;");
            categoryLabel.setFont(Font.font("Century Gothic Bold", 17));
            categoryLabel.setWrapText(true);
            materielPane.getChildren().add(categoryLabel);

            Label qteLabel = new Label("Quantité: " + materiel.getQte());
            qteLabel.setLayoutX(20); // Adjust the X position as needed
            qteLabel.setLayoutY(50); // Adjust the Y position as needed
            qteLabel.setPrefWidth(400);
            qteLabel.setStyle("-fx-text-fill: white;");
            qteLabel.setFont(Font.font("Century Gothic Bold", 17));
            qteLabel.setWrapText(true);
            materielPane.getChildren().add(qteLabel);

            offsetY += 80;
        }

        return anchorPane;
    }
    private void handleDetailsButton(ActionEvent event) {
        // Récupérer le matériel associé au bouton "Détails"
        Materiel materiel = (Materiel) ((Button) event.getSource()).getUserData();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/StockMateriel/Details.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = nbrPannier.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

            open_details_Materiel controller = loader.getController();
            controller.setMateriel(materiel);
            controller.setUserModel(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showCabine(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/GymCabine/userFrontCabine.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) btn_workbench13.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            userFrontCabine controller = loader.getController();
            controller.setGym(salleDeSport);
            controller.setUserModel(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
                    }

    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_1111 != null : "fx:id=\"pane_1111\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_111111111 != null : "fx:id=\"pane_111111111\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_132 != null : "fx:id=\"pane_132\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert gymTitleText != null : "fx:id=\"gymTitleText\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert gymDescriptionText != null : "fx:id=\"gymDescriptionText\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert gymLocationText != null : "fx:id=\"gymLocationText\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_11111 != null : "fx:id=\"pane_11111\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_13 != null : "fx:id=\"pane_13\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_131 != null : "fx:id=\"pane_131\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert pane_1311 != null : "fx:id=\"pane_1311\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        assert btn_workbench131 != null : "fx:id=\"btn_workbench131\" was not injected: check your FXML file 'userFrontGymCours.fxml'.";
        showStock();
        try {
            servicesCours sc = new servicesCours();
             coursList = sc.selectAll();
            int pageSize = coursList.size() / 3;
            if (coursList.size() % 3 != 0) {
                pageSize++;
            }
            paginationCours.setPageCount(pageSize);
            paginationCours.setPageFactory(this::createPageCours);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private Node createPageCours(Integer integer) {
        int pageIndex = integer;
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(952,167);

        int itemsPerPage = 1;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, coursList.size());

        int offsetY = 0;
        for(
                int i = startIndex;
                i<endIndex;i++)

        {
            cours s = coursList.get(i);

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

            if (!Objects.equals(s.getLienVideo(), "")) {
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
            Button detailsButton = new Button("consult");
            detailsButton.setLayoutX(760);
            detailsButton.setLayoutY(20);
            detailsButton.setStyle("-fx-border-width: 2px 2px 2px 2px;" +
                    "    -fx-border-radius: 50px;" +
                    "    -fx-background-radius: 50px;" +
                    "    -fx-border-style: solid;" +
                    "    -fx-border-color: black;" +
                    "-fx-min-width: 132;" +
                    "-fx-min-height: 43");
            detailsButton.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/CoursProgram/userFrontGymCoursProgram.fxml"));
                    Parent parent = loader.load(); // charger l interface dans la variable parent
                    userFrontGymCoursProgram controller = loader.getController();
                    controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

                    Scene scene = userNameLabel.getScene();
                    // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                    scene.setRoot(parent);

                    //controller.setCour(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            sallePane.getChildren().add(detailsButton);

            Label nameLabel = new Label("Name :" +s.getNom());
            nameLabel.setLayoutX(240);
            nameLabel.setLayoutY(14);
            nameLabel.setPrefWidth(200);
            nameLabel.setStyle("-fx-text-fill: white;");
            nameLabel.setFont(Font.font("Century Gothic Bold", 17));
            sallePane.getChildren().add(nameLabel);

            Label coachLabel = new Label("Coach :"+s.getCoach());
            coachLabel.setLayoutX(240);
            coachLabel.setLayoutY(40);
            coachLabel.setPrefWidth(400);
            coachLabel.setStyle("-fx-text-fill: white;");
            coachLabel.setFont(Font.font("Century Gothic Bold", 17));
            coachLabel.setWrapText(true);
            sallePane.getChildren().add(coachLabel);

            Label DayLabel = new Label("Day: " + s.getJours());
            DayLabel.setLayoutX(240); // Adjust the X position as needed
            DayLabel.setLayoutY(70); // Adjust the Y position as needed
            DayLabel.setPrefWidth(400);
            DayLabel.setStyle("-fx-text-fill: white;");
            DayLabel.setFont(Font.font("Century Gothic Bold", 17));
            DayLabel.setWrapText(true);
            sallePane.getChildren().add(DayLabel);

            Label TypeLabel = new Label("Type: " + s.getType());
            TypeLabel.setLayoutX(240);
            TypeLabel.setLayoutY(100);
            TypeLabel.setPrefWidth(400);
            TypeLabel.setStyle("-fx-text-fill: white;");
            TypeLabel.setFont(Font.font("Century Gothic Bold", 17));
            TypeLabel.setWrapText(true);
            sallePane.getChildren().add(TypeLabel);

            Label PriceLabel = new Label("Price: " + s.getPrix());
            PriceLabel.setLayoutX(240);
            PriceLabel.setLayoutY(130);
            PriceLabel.setPrefWidth(400);
            PriceLabel.setStyle("-fx-text-fill: white;");
            PriceLabel.setFont(Font.font("Century Gothic Bold", 17));
            PriceLabel.setWrapText(true);
            sallePane.getChildren().add(PriceLabel);


            ImageView imageView = new ImageView(new Image("@../../assets/images/gymIllustration.png"));

            if (!Objects.equals(s.getImage(), "")) {
                Image image = new Image("@../../uploads/" + s.getImage());
                imageView = new ImageView(image);

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
    public void setGym(SalleDeSport s) {
        salleDeSport = s;
        gymTitleText.setText(s.getNom_salle());
        gymDescriptionText.setText(s.getDescr());
        gymLocationText.setText(s.getLieu_salle());


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
    void rechercheGym(ActionEvent event) {
        String nomRecherche = searcch.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                servicesCours service = new servicesCours();
                List<cours> sallelist = service.searchByNom(nomRecherche); // Appeler une méthode de service pour rechercher par nom

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
                    paginationCours.setPageCount(pageSize);
                    paginationCours.setPageFactory(this::createPageCours);
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




