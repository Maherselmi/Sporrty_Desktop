package controllers.Front.UserAbonement;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.GymCabine.UserGym;
import controllers.Front.ProduitPanier.userFrontGymProduit;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Abonnement;
import models.Panier;
import models.PanierProduit;
import models.User;
import services.ServiceAbonnement;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FrontAbonnement {

    @FXML
    private TableView<Abonnement> tableView;
    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;
    @FXML
    private TableColumn<?, ?> prixColumn;
    @FXML
    private ComboBox<String> abonnementComboBox;
      @FXML
    private Button choisirButton;
    @FXML
    private Label idU;
    @FXML
    private Label userNameLabel;
    private int userId; // Champ pour stocker l'ID de l'utilisateur
    @FXML
    private Pagination abonnementsPagination;
    @FXML
    private Label messLabel;
@FXML
private Label messLabel1;

    @FXML
    private Label nbrPannier;
    @FXML
    private ImageView userPhoto;
    private Payment payment;

    private List<Abonnement> abonnements;
    private final int ABONNEMENTS_PER_PAGE = 6;
    int currentUserId = 0;
    final int ab_PER_PAGE = 6;
    private User user;

    // Ajoutez un attribut userId à votre classe FrontAbonnement

    // Modifiez le constructeur de FrontAbonnement pour prendre l'ID de l'utilisateur en paramètre
    public FrontAbonnement(int userId) {
        this.userId = userId;
    }
    public FrontAbonnement() {
        Stripe.apiKey = "sk_test_51Opw7UJNk8jtQfF2nN8hQP1LlMYOLdH078kl4iDHhw4j6GQRVLfNMlDxYkTNtVv6SwlEHUsYIjqRp6ueVh2vJWxy00yUPyptuu";

        // Constructeur par défaut sans paramètres
    }


    /* public void setId(int userId) {
         this.userId = userId;
     }*/
 /*FXML
    public void setId(int id) {
        userId = id;
        idU.setText(Integer.toString(id));

        try {
            ServiceUser serviceUser = new ServiceUser();
            User user = serviceUser.selectById(id); // Récupérer l'utilisateur à partir de l'ID
            if (user != null) {
                userNameLabel.setText(user.getNom()); // Mettre à jour le label avec le nom de l'utilisateur


                // Afficher les abonnements de l'utilisateur
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    /*@FXML
    private void openProfile(ActionEvent event) {
        userId = Integer.parseInt(idU.getText()); // Récupérer l'ID de l'utilisateur à partir de l'interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/userFrontProfile.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            userFrontProfile controller = loader.getController();
            controller.setId(userId); // Définir l'ID de l'utilisateur dans le contrôleur
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/
    private void processPayment(double amountInEuros) {
        double tauxDeChange = 3.0; // Exemple de taux de change
        double amountInDinars = amountInEuros * tauxDeChange;

        // Convertir le montant en dinars en cents
        long amountInCents = Math.round(amountInDinars * 100);

        System.out.println("Montant en dinars tunisiens : " + amountInDinars);
        System.out.println("Montant en cents : " + amountInCents);

        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setCurrency("usd") // Utilisez USD pour l'exemple, mais remplacez par votre devise
                            .setAmount(amountInCents)
                            .addPaymentMethodType(String.valueOf(SessionCreateParams.PaymentMethodType.CARD))
                            .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        payment = new Payment();
        assert abonnementsPagination != null : "fx:id=\"abonnementsPagination\" was not injected: check your FXML file 'FrontAbonnement.fxml'.";
        try {
            ServiceAbonnement se = new ServiceAbonnement();
            abonnements = se.selectAllAbonnements();
            int pageSize = abonnements.size() / 6;
            if (abonnements.size() % 6 != 0) {
                pageSize++;
            }
            abonnementsPagination.setPageCount(pageSize);
            abonnementsPagination.setPageFactory(this::createPage);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/*
 private void ajouterAbonnement(String typeAbonnement) {
     try {
         ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
         Abonnement abonnementChoisi = serviceAbonnement.selectAbonnementByType(typeAbonnement);
         if (abonnementChoisi != null) {
             ServiceUser serviceUser = new ServiceUser();
             User user = serviceUser.selectById(userId);
             if (user != null) {
                 if (serviceAbonnement.userHasAbonnement(user, abonnementChoisi)) {
                     messLabel1.setText("");
                     messLabel.setText("The user already has this subscription");

                 } else {
                     serviceAbonnement.inscrireUser(abonnementChoisi, user);
                     messLabel.setText("");
                     messLabel1.setText("You chose : " + abonnementChoisi.getType());

                 }
             } else {
                 messLabel.setText("Utilisateur non trouvé avec l'ID : " + userId);
             }
         } else {
             messLabel.setText("Abonnement non trouvé avec le type : " + typeAbonnement);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
*/
// Utilisez l'ID de l'utilisateur à partir de l'objet User dans ajouterAbonnement

    private void ajouterAbonnement(String typeAbonnement, User user) {
    // Utilisez user.getId() pour obtenir l'ID de l'utilisateur
    int userId = user.getId();
    try {
        ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
        Abonnement abonnementChoisi = serviceAbonnement.selectAbonnementByType(typeAbonnement);
        if (abonnementChoisi != null) {
            if (serviceAbonnement.userHasAbonnement(user, abonnementChoisi)) {
                messLabel1.setText("");
                messLabel.setText("The user already has this subscription");
            } else {
                serviceAbonnement.inscrireUser(abonnementChoisi, user);
                messLabel.setText("");
                messLabel1.setText("You chose : " + abonnementChoisi.getType());
            }
        } else {
            messLabel.setText("Abonnement non trouvé avec le type : " + typeAbonnement);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}





    private AnchorPane createPage(int pageIndex) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(900);
        anchorPane.setPrefHeight(600);

        int startIndex = pageIndex * ab_PER_PAGE;
        int endIndex = Math.min(startIndex + ab_PER_PAGE, abonnements.size());



        int xOffset = 0;
        int yOffset = 30;
        int xIncrement = 330;
        int yIncrement = 260;

        for (int i = startIndex; i < endIndex; i++) {
            Abonnement ev = abonnements.get(i);

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

            Label TypeLabel = new Label(ev.getType());
            TypeLabel .setLayoutX(18);
            TypeLabel .setLayoutY(12);
            TypeLabel .setPrefWidth(170);
            TypeLabel .setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Nom en gras
            TypeLabel .setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            TypeLabel .setWrapText(true);
            productPane.getChildren().add(TypeLabel );


            Button addButton = new Button("subscribe");
            addButton.setLayoutX(200); // Positionner le bouton à droite du nom de l'abonnement
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

// Utilisation de la variable 'ev' pour définir l'action du bouton
           // addButton.setOnAction(event -> ajouterAbonnement(ev.getType()));
            System.out.println(userId);
        //    addButton.setOnAction(event -> ajouterAbonnement(ev.getType(), userId));

            addButton.setOnAction(event -> ajouterAbonnement(ev.getType(), user));




            Label descriptionLabel = new Label(ev.getDescription());
            descriptionLabel.setLayoutX(18);
            descriptionLabel.setLayoutY(50);
            descriptionLabel.setPrefWidth(270);
            descriptionLabel.setStyle("-fx-text-fill: white;");
            descriptionLabel.setFont(Font.font("Arial", 14)); // Augmenter la taille de la police
            descriptionLabel.setWrapText(true);
            productPane.getChildren().add(descriptionLabel);



            Label PriceLabel = new Label("Price: " + ev.getPrix());
            PriceLabel.setLayoutX(18); // Positionner le nombre de participants
            PriceLabel.setLayoutY(130);
            PriceLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Participants en gras
            PriceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Augmenter la taille de la police
            productPane.getChildren().add( PriceLabel);


            xOffset += xIncrement;
            if ((i + 1) % 3 == 0) {
                xOffset = 0;
                yOffset += yIncrement;
            }

            anchorPane.getChildren().add(productPane);
        }

        return anchorPane;



    }

    public void setUserModel(User user) throws SQLException {
        this.user = user;
        ServiceUser serviceUser = new ServiceUser();

        try {
            userPhoto.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));
        // Utilisez l'utilisateur chargé pour initialiser votre interface utilisateur
        if (user != null) {
            System.out.println("trouve");
            userNameLabel.setText(user.getNom());
        } else {
            System.out.println("Utilisateur non trouvé avec l'ID : " + userId);
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

        Scene scene = idU.getScene();
        // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
        scene.setRoot(parent);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
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
