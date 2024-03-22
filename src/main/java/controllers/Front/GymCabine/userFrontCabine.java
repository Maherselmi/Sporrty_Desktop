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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.*;
import services.ServiceCabine;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class userFrontCabine {


    @FXML
    private ResourceBundle resources;
    @FXML
    private Button btn_workbench14;

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
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;
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
    private Pane pane_1111;

    @FXML
    private Pane pane_11111;

    @FXML
    private Pane pane_111111;

    @FXML
    private TextField search;


    @FXML
    private AnchorPane side_ankerpane;

    private SalleDeSport salleDeSport;
    private User user;

    @FXML
    void openGymManament(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/GymCabine/userGym.fxml"));
            Parent parent = loader.load();
            UserGym controller = loader.getController();
            controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

            Scene scene = new Scene(parent);
            Stage stage = (Stage) btn_workbench11.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openStockMateriel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/StockMateriel/userFrontGymStockMateriel.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent
            // Récupérer la scène actuelle
            Scene scene = side_ankerpane.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

            // Appeler showSalle ici pour afficher la pagination
            showSalle();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontGymStockMateriel.fxml'.";
        // Assurez-vous de vérifier tous les autres éléments comme ci-dessus
    }

    private void showSalle() {
        try {
            ServiceCabine s= new ServiceCabine();
            List<Cabine> cabineList = s.selectAllByIdSalle(salleDeSport.getId_salle());

            int itemsPerPage = 3;
            int pageSize = (cabineList.size() + itemsPerPage - 1) / itemsPerPage;

            pagination.setPageCount(pageSize);
            pagination.setPageFactory(pageIndex -> createPage(pageIndex, cabineList));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement du stock");
            alert.setContentText("Une erreur est survenue lors du chargement du stock de matériel. Veuillez réessayer plus tard.");
            alert.show();
        }
    }

    private AnchorPane createPage(int pageIndex, List<Cabine> sallelist) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(952, 167);

        int itemsPerPage = 3;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, sallelist.size());

        int offsetY = 0;
        for (int i = startIndex; i < endIndex; i++) {
            Cabine s = sallelist.get(i);

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

            Label nameLabel = new Label(s.getNom_cabine());
            nameLabel.setLayoutX(240);
            nameLabel.setLayoutY(14);
            nameLabel.setPrefWidth(200);
            nameLabel.setStyle("-fx-text-fill: white;");
            nameLabel.setFont(Font.font("Century Gothic Bold", 17));
            sallePane.getChildren().add(nameLabel);

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

            Label descriptionLabel = new Label("Capacite: "+s.getCapacite());
            descriptionLabel.setLayoutX(240);
            descriptionLabel.setLayoutY(40);
            descriptionLabel.setPrefWidth(400);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setFont(Font.font("Century Gothic Bold", 17));
            descriptionLabel.setWrapText(true);
            sallePane.getChildren().add(descriptionLabel);

            Label categoryLabel = new Label("Has Vr: " + s.isHas_vr());
            categoryLabel.setLayoutX(240); // Adjust the X position as needed
            categoryLabel.setLayoutY(70); // Adjust the Y position as needed
            categoryLabel.setPrefWidth(400);
            categoryLabel.setStyle("-fx-text-fill: white;");
            categoryLabel.setFont(Font.font("Century Gothic Bold", 17));
            categoryLabel.setWrapText(true);
            sallePane.getChildren().add(categoryLabel);

            offsetY += 180;

        }

        return anchorPane;
    }

    public void setGym(SalleDeSport gym) {
        salleDeSport = gym;

        showSalle();
    }

    @FXML
    void rechercher(ActionEvent event) {
        String nomRecherche = search.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                ServiceCabine service = new ServiceCabine();
                List<Cabine> cabineList = service.searchByNom(nomRecherche); // Appeler une méthode de service pour rechercher par nom

                // Vérifier si la liste des cabines est vide
                if (cabineList.isEmpty()) {
                    // Afficher une alerte indiquant que la cabine n'existe pas
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cabine non trouvée");
                    alert.setHeaderText(null);
                    alert.setContentText("Aucune cabine ne correspond au nom saisi.");
                    alert.show();
                } else {
                    // Mise à jour de la pagination avec les résultats de la recherche
                    int itemsPerPage = 3;
                    int pageSize = (cabineList.size() + itemsPerPage - 1) / itemsPerPage;
                    pagination.setPageCount(pageSize);
                    pagination.setPageFactory(pageIndex -> createPage(pageIndex, cabineList));
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
            alert.setContentText("Veuillez saisir le nom de la cabine a rechercher.");
            alert.show();
        }
    }
    @FXML
    void OpenProductManagment(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/ProduitPanier/userFrontGymProduit.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene =btn_workbench14.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

            userFrontGymProduit controller = loader.getController();
            controller.setUserModel(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void openeventsmanagment(ActionEvent actionEvent) {
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
    public void openraclamation(ActionEvent actionEvent) {
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

    @FXML
    void openhome(ActionEvent event) {
                    try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                // Récupérer la scène actuelle
                Scene scene =btn_workbench12.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

                UserFrontHome controller = loader.getController();
                controller.setUserModel(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
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

