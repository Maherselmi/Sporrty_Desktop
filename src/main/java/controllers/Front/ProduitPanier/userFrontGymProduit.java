package controllers.Front.ProduitPanier;

import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.GymCabine.UserGym;
import controllers.Front.UserAbonement.userFrontProfile;
import controllers.Front.UserFrontHome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Panier;
import models.PanierProduit;
import models.Produit;
import models.User;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceProduit;
import services.ServiceUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class userFrontGymProduit {

    @FXML
    private Pagination pagination;
    @FXML
    private ImageView userPhoto;
    @FXML
    private Label errorPanierProduit;

    @FXML
    private Label nbrPannier;
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
    private Button btn_workbench112;

    @FXML
    private Button btn_workbench12;

    @FXML
    private Button btn_workbench13;

    @FXML
    private Button btn_workbench131;

    @FXML
    private Pane pane_1111;

    @FXML
    private Label userNameLabel;
    @FXML
    private Pane pane_1111111111;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;
    private List<Produit> produits;


    final int PRODUCTS_PER_PAGE = 6;
    private User user;

    @FXML
    void initialize() {
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert btn_workbench131 != null : "fx:id=\"btn_workbench131\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert pane_1111 != null : "fx:id=\"pane_1111\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert pane_1111111111 != null : "fx:id=\"pane_1111111111\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'userFrontGymProduit.fxml'.";
        ServiceProduit sp = new ServiceProduit();

        try {
            produits = sp.selectAll();
            int pageSize = produits.size() / 6;
            if (produits.size() % 6 != 0) {
                pageSize++;
            }
            pagination.setPageCount(pageSize);
            pagination.setPageFactory(this::createPage);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        StringBuilder recomandation = new StringBuilder("If you were a client of a gym application that sells products which will you recommended to buy and why only recommand one product? these are the products: ");

        try {
            for(Produit produit:sp.selectAll())
            {
                recomandation.append("Produit Nom: ").append(produit.getNom())
                        .append(" Produit description: ").append(produit.getDescription())
                        .append(" Produit category: ").append(produit.getCategorie())
                        .append(" Produit quantite: ").append(produit.getQte())
                        .append(" Produit price: ").append(produit.getPrix());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        interactWithChatGPT(String.valueOf(recomandation));
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

    private AnchorPane createPage(int pageIndex) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(900);
        anchorPane.setPrefHeight(600);

        int startIndex = pageIndex * PRODUCTS_PER_PAGE;
        int endIndex = Math.min(startIndex + PRODUCTS_PER_PAGE, produits.size());

        int xOffset = 0;
        int yOffset = 30;
        int xIncrement = 330;
        int yIncrement = 260;

        for (int i = startIndex; i < endIndex; i++) {
            Produit produit = produits.get(i);

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

            Label nameLabel = new Label(produit.getNom());
            nameLabel.setLayoutX(18);
            nameLabel.setLayoutY(12);
            nameLabel.setPrefWidth(119);
            nameLabel.setStyle("-fx-text-fill: white;");
            nameLabel.setWrapText(true);
            productPane.getChildren().add(nameLabel);

            Label descriptionLabel = new Label(produit.getDescription());
            descriptionLabel.setLayoutX(18);
            descriptionLabel.setLayoutY(50);
            descriptionLabel.setPrefWidth(150);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setWrapText(true);
            productPane.getChildren().add(descriptionLabel);

            Label priceLabel = new Label("Prix: " + produit.getPrix() + "DT");
            priceLabel.setLayoutX(19);
            priceLabel.setLayoutY(207);
            priceLabel.setStyle("-fx-text-fill: white;");
            productPane.getChildren().add(priceLabel);

            Label qteLabel;
            if (produit.getQte() > 0)
            {
                qteLabel = new Label("Status: In Stock (" + produit.getQte()+")");
                qteLabel.setStyle("-fx-text-fill: green;");
            }
            else
            {
                qteLabel = new Label("Status: Out Of Stock (" + produit.getQte()+")");
                qteLabel.setStyle("-fx-text-fill: red;");
            }
            qteLabel.setLayoutX(100);
            qteLabel.setLayoutY(207);
            productPane.getChildren().add(qteLabel);

            Label categoryLabel = new Label("Category: " + produit.getCategorie());
            categoryLabel.setLayoutX(19);
            categoryLabel.setLayoutY(187);
            categoryLabel.setStyle("-fx-text-fill: white;");
            productPane.getChildren().add(categoryLabel);

            ImageView imageView;
            if(Objects.equals(produit.getImage(), ""))
                imageView = new ImageView(new Image("@../../assets/images/productsIllustration.png"));
            else
                imageView = new ImageView(new Image("@../../upload/products/"+produit.getImage()));

            imageView.setLayoutX(159);
            imageView.setLayoutY(48);
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

            Button addButton = new Button("Add To Basket");
            addButton.setLayoutX(159);
            addButton.setLayoutY(10);
            addButton.setPrefSize(139, 30);
            addButton.setStyle(
                    "-fx-background-color: #f3fafa;" +
                    "-fx-border-width: 2px 2px 2px 2px;" +
                    "-fx-border-radius: 50px;" +
                    "-fx-background-radius: 50px;" +
                    "-fx-border-style: solid;" +
                    "-fx-border-color: black;");
            productPane.getChildren().add(addButton);

            if(produit.getQte()<=0)
                addButton.setDisable(true);

            addButton.setOnAction(event -> {
                try {
                    errorPanierProduit.setText("");
                    ServicePanier sp = new ServicePanier();
                    List<Panier> paniers = sp.selectByUserId(user.getId());
                    if(paniers.isEmpty()) {
                        ServiceUser serviceUser = new ServiceUser();
                        Panier p = new Panier(serviceUser.selectById(user.getId()));
                        sp.insertOne(p);
                        paniers = sp.selectByUserId(user.getId());
                    }
                    Panier panier = paniers.get(0);
                    ServicePanierProduit Spp = new ServicePanierProduit();

                    List<PanierProduit> currentPanierProduit = Spp.selectByPanierProduitId(panier.getId(),produit.getId());
                    if(currentPanierProduit.isEmpty())
                    {
                        System.out.println("current Pannier Produit is empty");
                        PanierProduit panierProduit = new PanierProduit(panier,produit,1);
                        Spp.insertOne(panierProduit);
                    }else{

                        System.out.println("current Pannier Produit is not empty");
                        int currentQte = currentPanierProduit.get(0).getQuantite();
                        if(currentQte + 1 >= produit.getQte())
                        {
                            System.out.println("You reach the max qte for that product");
                            errorPanierProduit.setText("Error: You cannot add this item to your cart. The quantity you're trying to add (" + produit.getQte() + ") is less than or equal to the current quantity (" + (currentQte+1) + ").");
                            return;
                        }
                        PanierProduit panierProduit = new PanierProduit(panier,produit,currentQte + 1);
                        Spp.updateOne(panierProduit);
                    }

                    nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });

            xOffset += xIncrement;
            if ((i + 1) % 3 == 0) {
                xOffset = 0;
                yOffset += yIncrement;
            }

            anchorPane.getChildren().add(productPane);
        }

        return anchorPane;
    }

    public void openBasket(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/ProduitPanier/userFrontGymProduitPanier.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent
            userFrontGymProduitPanier controller = loader.getController();
            controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

            // Récupérer la scène actuelle
            Scene scene = side_ankerpane.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void interactWithChatGPT(String userInput) {
        try {
            // Set up the HTTP connection
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer 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");

            // Build the request body
            String requestBody = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + userInput + "\", \"max_tokens\": 50}";
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Recommandation: "+response);
                    // Display the response in a dialog or a small window
                    displayChatbotDialog(String.valueOf(response));
                }
            } else {
                System.err.println("HTTP Request Failed with Error Code: " + responseCode);
                String defaultResponse = "I'd recommend purchasing the \"test\" supplement. With 92 quantities available and a price of $200.0, it seems like a popular choice. Plus, the description mentions it as a \"cool supplement,\" which implies it could offer some unique benefits to your workout routine. It's always good to have a reliable supplement to support your fitness goals.";
                displayChatbotDialog(defaultResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayChatbotDialog(String response) {
        // Create a new stage for the dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Chatbot Recommendation");

        // Create a VBox to hold the dialog content
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Load the bot image (replace the path with your actual image path)
        InputStream inputStream = getClass().getResourceAsStream("/assets/gif/bot-emotions-principle.gif");
        Image botImage = new Image(inputStream);
        ImageView imageView = new ImageView(botImage);

        // Create a label to display the chatbot response
        Label responseLabel = new Label(response);
        responseLabel.setWrapText(true);

        // Add the image and label to the VBox
        vbox.getChildren().addAll(responseLabel,imageView);

        // Create a scene with the VBox and set it to the dialog stage
        Scene dialogScene = new Scene(vbox);
        dialogStage.setScene(dialogScene);

        // Show the dialog
        dialogStage.show();
    }

    public void setUserModel(User user) {
        this.user = user;
        nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));
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

    }

    public void openHome(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            UserFrontHome controller = loader.getController();
            controller.setUserModel(user); // Utiliser la méthode setUserModel avec l'objet user

            System.out.println("User sent to userFrontProfile: " + user.getNom()); // Message de débogage pour vérifier l'envoi de l'utilisateur

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
    public void openProfile(ActionEvent actionEvent) {
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
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
