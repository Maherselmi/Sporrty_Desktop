package controllers.Front.ProduitPanier;

import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.GymCabine.UserGym;
import controllers.Front.UserAbonement.FrontAbonnement;
import controllers.Front.UserAbonement.userFrontProfile;
import controllers.Front.UserFrontHome;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Panier;
import models.PanierProduit;
import models.Produit;
import models.User;
import netscape.javascript.JSObject;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class userFrontGymProduitPanier {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label TotalPriceField;

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
    private Button payNowButton;

    @FXML
    private ImageView userPhoto;

    @FXML
    private Label nbrPannier;


    @FXML
    private Pagination pagination;

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

    Stage paymentStage = new Stage();
    @FXML
    private Label userNameLabel;
    private User user;


    @FXML
    void payNow(ActionEvent event) {
        ServicePanier servicePanier = new ServicePanier();
        ServiceProduit serviceProduit = new ServiceProduit();
        ServicePanierProduit servicePanierProduit = new ServicePanierProduit();
        try {
            Panier panier = servicePanier.selectByUserId(user.getId()).get(0);

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            ArrayList<String> itemsList = new ArrayList<>();
            ArrayList<Integer> itemsQteList = new ArrayList<>();
            ArrayList<Float> pricesList = new ArrayList<>();

            for(PanierProduit panierProduit:servicePanierProduit.selectByPanierId(panier.getId()))
            {
                Produit produit = serviceProduit.selectById(panierProduit.getProduit_id().getId());
                itemsList.add(produit.getNom());
                itemsQteList.add(panierProduit.getQuantite());
                pricesList.add(produit.getPrix()*panierProduit.getQuantite());
            }

            float total = pricesList.stream().reduce(0f, Float::sum);

            webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    StringBuilder script = new StringBuilder();
                    for (int i = 0; i < pricesList.size(); i++) {
                        script.append("var row = document.createElement('tr');");
                        script.append("row.innerHTML = '<td>' + '").append(itemsList.get(i)).append(" (").append(itemsQteList.get(0)).append(")' + '</td>' + '<td>").append(String.format("%.2f", pricesList.get(i))).append("DT</td>';");
                        script.append("document.getElementById('receipt-table-body').appendChild(row);");
                    }
                    script.append("document.getElementById('total').innerText = '").append(String.format("%.2f", total)).append("DT';");
                    webEngine.executeScript(script.toString());
                    JSObject window = (JSObject) webView.getEngine().executeScript("window");
                    window.setMember("java", this);
                }
            });

            webEngine.load(Objects.requireNonNull(getClass().getResource("/payment_form.html")).toExternalForm());
            StackPane root = new StackPane();
            root.getChildren().add(webView);

            Scene scene = new Scene(root, 600, 600);
            paymentStage.setScene(scene);

            paymentStage.setTitle("Payment Details");
            paymentStage.show();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void payNowBtnClickedReceipt() {
        ServicePanierProduit servicePanierProduit = new ServicePanierProduit();
        ServiceProduit serviceProduit = new ServiceProduit();
        ServicePanier servicePanier = new ServicePanier();
        Payment p = new Payment();
        ArrayList<Float> pricesList = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Payment Confirmed");
        alert.setHeaderText(null);
        alert.setContentText("Payment confirmed, wait for delivery in approximately 48 hours.");

        alert.showAndWait();
        try {
            Panier panier = servicePanier.selectByUserId(user.getId()).get(0);
            for(PanierProduit panierProduit:servicePanierProduit.selectByPanierId(panier.getId())) {
                Produit produit = serviceProduit.selectById(panierProduit.getProduit_id().getId());
                produit.setQte(produit.getQte()-panierProduit.getQuantite());
                serviceProduit.updateOne(produit);
                pricesList.add(produit.getPrix()*panierProduit.getQuantite());
            }
            float total = pricesList.stream().reduce(0f, Float::sum);

            servicePanier.deleteOne(panier);
            long priceLong = (long) (total*0.32) *100;
            p.processPayment(priceLong);
            showBasket();

            paymentStage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printBtnClickedReceipt() {
        // Implement your printReceipt action here
        System.out.println("print");
        WebView webView = (WebView) paymentStage.getScene().getRoot().getChildrenUnmodifiable().get(0);

        // Create a PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            // Print the content of the WebView node
            boolean success = job.printPage(webView);

            if (success) {
                job.endJob();
            }
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


    @FXML
    void initialize() {
        assert TotalPriceField != null : "fx:id=\"TotalPriceField\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench131 != null : "fx:id=\"btn_workbench131\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench1311 != null : "fx:id=\"btn_workbench1311\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert btn_workbench13111 != null : "fx:id=\"btn_workbench13111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert payNowButton != null : "fx:id=\"payNowButton\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert nbrPannier != null : "fx:id=\"nbrPannier\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert pane_1111 != null : "fx:id=\"pane_1111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert pane_11111 != null : "fx:id=\"pane_11111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert pane_111111 != null : "fx:id=\"pane_111111\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontGymProduitPanier.fxml'.";
    }

    private void showBasket() {
        System.out.println("show basket running");
        int nbrProductInPannier = getNbrProductInPannier(user.getId());
        nbrPannier.setText(String.valueOf(nbrProductInPannier));

        try {
            if(nbrProductInPannier == 0)
            {
                TotalPriceField.setText("Total Price: N/a");
                payNowButton.setDisable(true);
                pagination.setPageCount(1);
                pagination.setPageFactory(null);
            }else{
                ServicePanierProduit Spp = new ServicePanierProduit();
                List<PanierProduit> panierProduits = Spp.selectAll();
                int pageSize = panierProduits.size() / 3;
                if (panierProduits.size() % 3 != 0) {
                    pageSize++;
                }
                pagination.setPageCount(pageSize);
                pagination.setPageFactory(this::createPage);

                float finalPrice = 0;

                ServiceProduit serviceProduit = new ServiceProduit();
                for(PanierProduit panierProduit : panierProduits){
                    finalPrice += serviceProduit.selectById(panierProduit.getProduit_id().getId()).getPrix() * panierProduit.getQuantite();
                    TotalPriceField.setText("Total Price: "+finalPrice+" DT");
                }

                payNowButton.setDisable(false);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private AnchorPane createPage(int pageIndex) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(952, 167);

        try {
            ServicePanierProduit spp = new ServicePanierProduit();
            ServicePanier servicePanier = new ServicePanier();
            List<Panier>  panierList = servicePanier.selectByUserId(user.getId());
            if(panierList.isEmpty())
            {
                return anchorPane;
            }
            Panier panier = panierList.get(0);

            List<PanierProduit> panierProduits = spp.selectByPanierId(panier.getId());
            int itemsPerPage = 3;
            int startIndex = pageIndex * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, panierProduits.size());

            int offsetY = 0;
            for (int i = startIndex; i < endIndex; i++) {

                PanierProduit panierProduit = panierProduits.get(i);
                ServiceProduit serviceProduit = new ServiceProduit();

                Produit produit = serviceProduit.selectById(panierProduit.getProduit_id().getId());
                System.out.println(panierProduit);

                Pane productPane = new Pane();
                productPane.setLayoutY(offsetY);
                productPane.setPrefSize(952, 167);
                productPane.setStyle(
                                "-fx-background-color: #5bc0de;" +
                                "-fx-border-color: #000000;" +
                                "-fx-border-width: 2px 2px 2px 2px;" +
                                "-fx-border-radius: 16px;" +
                                "-fx-background-radius: 16px;" +
                                "-fx-border-style: solid;");
                anchorPane.getChildren().add(productPane);

                Label productNameLabel = new Label(produit.getNom());
                productNameLabel.setLayoutX(240);
                productNameLabel.setLayoutY(14);
                productNameLabel.setPrefWidth(400);
                productNameLabel.setTextFill(Color.WHITE);
                productNameLabel.setFont(Font.font("Century Gothic Bold", 17));
                productPane.getChildren().add(productNameLabel);

                Label descriptionLabel = new Label(produit.getDescription());
                descriptionLabel.setLayoutX(240);
                descriptionLabel.setLayoutY(40);
                descriptionLabel.setPrefWidth(400);
                descriptionLabel.setStyle("-fx-text-fill: white;");
                descriptionLabel.setFont(Font.font("Century Gothic Bold", 17));
                descriptionLabel.setWrapText(true);
                productPane.getChildren().add(descriptionLabel);

                Label prixLabel = new Label("Prix: "+produit.getPrix()+"DT");
                prixLabel.setLayoutX(680);
                prixLabel.setLayoutY(120);
                prixLabel.setPrefWidth(400);
                prixLabel.setStyle("-fx-text-fill: white;");
                prixLabel.setFont(Font.font("Century Gothic Bold", 17));
                prixLabel.setWrapText(true);
                productPane.getChildren().add(prixLabel);


                Button removeFromBasket = new Button("Remove From Basket");
                removeFromBasket.setLayoutX(800);
                removeFromBasket.setLayoutY(14);
                removeFromBasket.setPrefSize(139, 30);
                removeFromBasket.setStyle(
                        "-fx-background-color:  #dc3545;" +
                                "-fx-text-fill: white;"+
                                "-fx-border-width: 2px 2px 2px 2px;" +
                                "-fx-border-radius: 50px;" +
                                "-fx-background-radius: 50px;" +
                                "-fx-border-style: solid;" +
                                "-fx-border-color: black;");

                removeFromBasket.setOnAction(event->{
                    ServicePanierProduit servicePanierProduit = new ServicePanierProduit();
                    try {
                        servicePanierProduit.deleteOne(panierProduit);
                        showBasket();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                productPane.getChildren().add(removeFromBasket);

                Label categoryLabel = new Label("Categorie: "+produit.getCategorie());
                categoryLabel.setLayoutX(680);
                categoryLabel.setLayoutY(80);
                categoryLabel.setPrefWidth(400);
                categoryLabel.setStyle("-fx-text-fill: white;");
                categoryLabel.setFont(Font.font("Century Gothic Bold", 17));
                categoryLabel.setWrapText(true);
                productPane.getChildren().add(categoryLabel);

                Label qteLabel = new Label("Qte:");
                qteLabel.setLayoutX(800);
                qteLabel.setLayoutY(120);
                qteLabel.setPrefWidth(400);
                qteLabel.setStyle("-fx-text-fill: white;");
                qteLabel.setFont(Font.font("Century Gothic Bold", 17));
                qteLabel.setWrapText(true);
                productPane.getChildren().add(qteLabel);

                TextField qteField = new TextField(String.valueOf(panierProduit.getQuantite()));
                qteField.setLayoutX(840);
                qteField.setLayoutY(110);
                qteField.setPrefWidth(80);
                qteField.setStyle("-fx-text-fill: black;");
                qteField.setFont(Font.font("Century Gothic Bold", 17));
                qteField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        qteField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
                qteField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        handleQteFieldChange(qteField.getText(), panierProduit.getPanier_id().getId(), produit.getId());
                        event.consume();
                    }
                });

                productPane.getChildren().add(qteField);

                ImageView imageView;
                if(Objects.equals(produit.getImage(), ""))
                    imageView = new ImageView(new Image("@../../assets/images/productsIllustration.png"));
                else
                    imageView = new ImageView(new Image("@../../upload/products/"+produit.getImage()));
                imageView.setLayoutX(50);
                imageView.setLayoutY(5);
                imageView.setFitWidth(139);
                imageView.setFitHeight(141);
                productPane.getChildren().add(imageView);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ImageView fullScreenImageView = new ImageView(imageView.getImage());
                        fullScreenImageView.setFitWidth(600);
                        fullScreenImageView.setFitHeight(600);
                        StackPane fullScreenPane = new StackPane(fullScreenImageView);
                        Scene fullScreenScene = new Scene(fullScreenPane, 600, 600);
                        Stage fullScreenStage = new Stage();
                        fullScreenStage.setScene(fullScreenScene);
                        fullScreenStage.show();
                    }
                });
                offsetY += 180;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return anchorPane;
    }

    private void handleQteFieldChange(String newValue, int panierId, int produit_id) {
        try {
            ServicePanierProduit servicePanierProduit = new ServicePanierProduit();
            ServiceProduit serviceProduit = new ServiceProduit();
            ServicePanier servicePanier = new ServicePanier();
            PanierProduit panierProduit = new PanierProduit(servicePanier.selectById(panierId),serviceProduit.selectById(produit_id),Integer.parseInt(newValue));
            Produit produit = serviceProduit.selectById(produit_id);

            if(produit.getQte()<panierProduit.getQuantite())
            {
                panierProduit.setQuantite(produit.getQte());
            }

            if(panierProduit.getQuantite() <=0)
                servicePanierProduit.deleteOne(panierProduit);
            else
                servicePanierProduit.updateOne(panierProduit);

            showBasket();
        } catch (SQLException e) {
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
        showBasket();
        System.out.println("test");
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
