package controllers.Front.UserAbonement;

import controllers.Front.EventsReclamation.userFrontEvent;
import controllers.Front.EventsReclamation.userFrontReclamationFxml;
import controllers.Front.GymCabine.UserGym;
import controllers.Front.ProduitPanier.userFrontGymProduit;
import controllers.Front.UserFrontHome;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Abonnement;
import models.Panier;
import models.PanierProduit;
import models.User;
import services.ServicePanier;
import services.ServicePanierProduit;
import services.ServiceUser;
import services.ServiceUserAbonnement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



public class userFrontProfile {
    @FXML
    private ImageView userImage;

    @FXML
    private Label pass_labe;
    @FXML
    private PasswordField old;

    @FXML
    private Label userNameLabel;

    @FXML
    private ImageView userPhoto;
    @FXML
    private Label nbrPannier;
    @FXML
    private Label idU;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField NomId;

    @FXML
    private TextField PrenomId;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField abonnementsLabel;
    @FXML
    private Label prenomLabel;
@FXML
private ComboBox abonnementComboBox;
    @FXML
    private TextField emailId;
    @FXML
    private  Label messagecc;
    private User user;
    private int userId;





    @FXML
    void submitButtonOnClick(ActionEvent event) {
        String newNom = NomId.getText();
        String newPrenom = PrenomId.getText();
        String newEmail = emailId.getText();

        // Vérifier si les champs ne sont pas vides
        if (!newNom.isBlank() && !newPrenom.isBlank() && !newEmail.isBlank()) {
            // Vérifier si l'email est au bon format
            if (newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                try {
                    ServiceUser serviceUser = new ServiceUser();
                    User existingUser = serviceUser.selectById(user.getId());

                    // Vérifier si l'utilisateur existe déjà avec le nouvel email
                    boolean emailExists = serviceUser.userExists(newEmail);

                    if (!emailExists || newEmail.equals(existingUser.getEmail())) {
                        // Mettre à jour l'utilisateur avec les nouvelles valeurs
                        User updatedUser = new User(user.getId(), newNom, newPrenom, newEmail, existingUser.getPassword());
                        serviceUser.updateUser(updatedUser); // Mettre à jour l'utilisateur dans la base de données

                        // Mettre à jour l'affichage dans l'interface utilisateur
                        NomId.setText(newNom);
                        PrenomId.setText(newPrenom);
                        emailId.setText(newEmail);
                        emailLabel.setText(newEmail); // Mettre à jour l'affichage de l'email
                        userNameLabel.setText(newNom);
                        prenomLabel.setText(newPrenom);

                        messagecc.setText("Validation successful !");
                    } else {
                        messagecc.setText("This email is already used by another user!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    messagecc.setText("Error during validation!");
                }
            } else {
                messagecc.setText("Email is invalid!");
            }
        } else {
            messagecc.setText("Please complete all fields!");
        }
    }



    @FXML
    void changePasswordButtonOnClick(ActionEvent event) {
        String newNom = NomId.getText();
        String newPrenom = PrenomId.getText();
        String newEmail = emailId.getText();
        String newPassword = password.getText();
        String newconfirmPassword = confirmPassword.getText();
        String oldPassword = old.getText();

        // Vérifier si les champs ne sont pas vides
        if (!newPassword.isBlank() && !newconfirmPassword.isBlank() && !oldPassword.isBlank()) {
            // Vérifier si l'email est au bon format

            // Vérifier si le mot de passe contient au moins 8 caractères et au moins un chiffre
            if (newPassword.length() >= 8 && newPassword.matches(".*\\d.*")) {
                try {
                    ServiceUser serviceUser = new ServiceUser();
                    User existingUser = serviceUser.selectById(user.getId());

                    // Vérifier si l'ancien mot de passe correspond
                    if (hashPassword(oldPassword).equals(existingUser.getPassword())) {
                        // Vérifier si l'utilisateur existe déjà avec le nouvel email
                        boolean emailExists = serviceUser.userExists(newEmail);

                        if (!emailExists || newEmail.equals(existingUser.getEmail())) {
                            if (newPassword.equals(newconfirmPassword)) {
                                // Afficher une alerte de confirmation
                                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                confirmationAlert.setTitle("Confirmation");
                                confirmationAlert.setHeaderText("Change password confirmation");
                                confirmationAlert.setContentText("Are you sure you want to change your password?");

                                Optional<ButtonType> result = confirmationAlert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    User updatedUser = new User(user.getId(), newNom, newPrenom, newEmail, newPassword); // Créer un nouvel utilisateur avec les nouvelles valeurs et le nouveau mot de passe

                                    serviceUser.updateUser(updatedUser); // Mettre à jour l'utilisateur dans la base de données

                                    // Mettre à jour l'affichage dans l'interface utilisateur
                                    NomId.setText(newNom);
                                    PrenomId.setText(newPrenom);
                                    emailId.setText(newEmail);
                                    password.clear();
                                    confirmPassword.clear();
                                    emailLabel.setText(newEmail); // Mettre à jour l'affichage de l'email
                                    userNameLabel.setText(newNom);
                                    prenomLabel.setText(newPrenom);

                                    pass_labe.setText("Validation successful !");
                                }
                            } else {
                                pass_labe.setText("Passwords do not match!");
                            }
                        } else {
                            pass_labe.setText("This email is already used by another user!");
                        }
                    } else {
                        pass_labe.setText("Incorrect old password!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    pass_labe.setText("Error during validation!");
                }
            } else {
                pass_labe.setText("Password must be at least 8 characters long and contain at least one digit!");
            }

        } else {
            pass_labe.setText("please complete all fields!");
        }
    }


    /*
    @FXML
    void deleteButtonOnClick(ActionEvent event) {
        try {
            ServiceUser serviceUser = new ServiceUser();
            User user = serviceUser.selectById(userId); // Récupérer l'utilisateur actuel
            serviceUser.deleteCompte(user); // Supprimer le compte de l'utilisateur actuel
            // Rediriger l'utilisateur vers la page de connexion ou afficher un message de succès
            Platform.exit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
*/
@FXML
void deleteButtonOnClick(ActionEvent event) {
    try {
        ServiceUser serviceUser = new ServiceUser();
        User currentUser = serviceUser.selectById(user.getId()); // Récupérer l'utilisateur actuel
        serviceUser.deleteCompte(currentUser); // Supprimer le compte de l'utilisateur actuel
        // Rediriger l'utilisateur vers la page de connexion ou afficher un message de succès
        Platform.exit();
    } catch (SQLException e) {
        e.printStackTrace();
        // Gérer l'exception
    }
}

    @FXML
    void initialize() {


    }
    @FXML
    public void afficherAbonnementsUtilisateur() {
        try {
            ServiceUserAbonnement serviceUserAbonnement = new ServiceUserAbonnement();
            List<Abonnement> abonnements = serviceUserAbonnement.selectAbonnementsForUser(user.getId()); // userId est l'ID de l'utilisateur connecté
            System.out.println("id utilisateur:");

            // Construire une chaîne contenant les types d'abonnements
            StringBuilder abonnementsText = new StringBuilder();
            for (Abonnement abonnement : abonnements) {
                abonnementsText.append(abonnement.getType()).append(", ");
            }
            if (abonnementsText.length() > 0) {
                abonnementsText.delete(abonnementsText.length() - 2, abonnementsText.length()); // Supprimer la virgule et l'espace en trop
            }

            // Afficher les types d'abonnements dans la label 'abonnementsLabel'
            abonnementsLabel.setText(abonnementsText.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }



    /*
@FXML
    private void openAbonnement(ActionEvent event) {
        userId = Integer.parseInt(idU.getText()); // Récupérer l'ID de l'utilisateur à partir de l'interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/FrontAbonnement.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            FrontAbonnement controller = loader.getController();
            controller.setId(userId); // Définir l'ID de l'utilisateur dans le contrôleur

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Abonnement");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
*/
  /*  @FXML
    private void openAbonnement(ActionEvent event) {
      //  userId = Integer.parseInt(idU.getText()); // Récupérer l'ID de l'utilisateur à partir de l'interface
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserAbonement/FrontAbonnement.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            FrontAbonnement controller = loader.getController();
            controller.setUserModel(user.getId()); // Définir l'ID de l'utilisateur dans le contrôleur
            Scene scene = userNameLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void openUserInterface(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            UserFrontHome controller = loader.getController();
            if (user != null) {
                controller.setUser(user); // Définir l'utilisateur dans le contrôleur
                // Définir l'utilisateur dans le contrôleur de userFrontProfile
                userFrontProfile userProfileHomeController = new userFrontProfile();
                userProfileHomeController.setUser(user);
                // Afficher la nouvelle fenêtre
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setTitle("Home");
                stage.setScene(scene);
                Stage currentStage = (Stage) userNameLabel.getScene().getWindow();
                currentStage.close();
                stage.show();
            } else {
                // Gérer le cas où l'objet user est nul
                System.err.println("User object is null");
                return;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }*/

    /*    @FXML
        private void openHomeFront(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
                Parent parent = loader.load();

                // Accéder au contrôleur de la nouvelle fenêtre
                UserFrontHome controller = loader.getController();

                // Récupérer l'utilisateur correspondant à l'ID
                ServiceUser serviceUser = new ServiceUser();
                User user = serviceUser.selectById(userId);

                // Définir l'utilisateur dans le contrôleur
                controller.setId(user);

                Scene scene = userNameLabel.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }
*/
public void setUser(User user) throws SQLException {
    this.user = user;
    this.userId = user.getId(); // Initialiser userId avec l'ID de l'utilisateur

    // Mettre à jour les labels et les champs de texte avec les informations de l'utilisateur
    userNameLabel.setText(user.getNom());
    prenomLabel.setText(user.getPrenom());
    emailLabel.setText(user.getEmail());
    NomId.setText(user.getNom());
    PrenomId.setText(user.getPrenom());
    emailId.setText(user.getEmail());
    // Charger l'image de l'utilisateur si le chemin d'accès est disponible
    ServiceUser serviceUser = new ServiceUser();

    try {
        userPhoto.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
        userImage.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));

    String imagePath = user.getImage_user();
    if (imagePath != null && !imagePath.isEmpty()) {
        try {
            // Obtenir le chemin absolu de l'image à partir du chemin relatif
            //String absoluteImagePath = getClass().getResource("file:/C:/xampp/htdocs/images/resultat_2A.png").toExternalForm();
            Image image = new Image("/images/notif.ico");
            userImage.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



    public void setUserModel(User user) {
        try {
            this.user = user;
            userNameLabel.setText(user.getNom()); // Afficher le nom de l'utilisateur dans le label
            prenomLabel.setText(user.getPrenom());
            userNameLabel.setText(user.getNom()); // Mettre à jour le label avec le nom de l'utilisateur
            emailLabel.setText(user.getEmail());
            NomId.setText(user.getNom()); // Remplir le
            // champ nom
            PrenomId.setText(user.getPrenom()); // Remplir le champ prénom
            emailId.setText(user.getEmail()); // Remplir le champ email

            // Afficher les abonnements de l'utilisateur
            ServiceUserAbonnement serviceUserAbonnement = new ServiceUserAbonnement();
            List<Abonnement> abonnements = serviceUserAbonnement.getAbonnementsForUser(user.getId());
            StringBuilder sb = new StringBuilder();
            for (Abonnement abonnement : abonnements) {
                sb.append(abonnement.getType()).append(", "); // Concaténer les types d'abonnements
            }
            abonnementsLabel.setText(sb.toString());

            // Afficher les abonnements de l'utilisateur
            afficherAbonnementsUtilisateur();

            System.out.println("Method afficherAbonnementsUtilisateur called successfully");
            System.out.println("User received in userFrontProfile:22 " + user.getNom()); // Afficher un message de confirmation
            ServiceUser serviceUser = new ServiceUser();

            try {
                userPhoto.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
                userImage.setImage(new Image("/upload/" + serviceUser.selectByEmail(user.getEmail()).getImage_user()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            nbrPannier.setText(String.valueOf(getNbrProductInPannier(user.getId())));
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception en affichant la trace de la pile
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
    /*@FXML
    private void changePasswordButtonOnClick(ActionEvent event) {
        String firstName = NomId.getText().trim();
        String lastName = PrenomId.getText().trim();
        String email = emailId.getText().trim();
        String newPassword = password.getText().trim();
        String confirmNewPassword = confirmPassword.getText().trim();
        String oldPassword = old.getText().trim();

        // Validate the form data
        if (newPassword.isEmpty() || confirmNewPassword.isEmpty() || oldPassword.isEmpty()) {
            pass_labe.setText("Please complete all fields");
            return;
        }

        // Check if the password meets a certain length requirement (e.g., at least 8 characters)
        if (newPassword.length() < 8) {
            pass_labe.setText("The password must be at least 8 characters long.");
            return;
        }

        // Check if the password meets strength criteria (e.g., a mix of letters and numbers)
        if (!newPassword.matches("(?=.*[0-9])(?=.*[a-zA-Z]).{8,}")) {
            pass_labe.setText("The password must contain letters and numbers.");
            return;
        }

        // Create a User object with form data
        Role role = Role.ADHERANT;
        User user = new User(firstName, lastName, email, newPassword, role);
        ServiceUser sp = new ServiceUser();

        try {
            // Check if the user already exists in the database
            User existingUser = sp.selectByEmail(email);

            // Check if the old password matches the one in the database
            String hashedOldPassword = hashPassword(oldPassword);
            if (!hashedOldPassword.equals(existingUser.getPassword())) {
                pass_labe.setText("Incorrect old password!");
                return;
            }

            // Hash the new password before updating the user
            String hashedNewPassword = hashPassword(newPassword);
            user.setPassword(hashedNewPassword);

            // Update the user's password in the database
            sp.updateUser(user);

            // Afficher un message indiquant que le mot de passe a été modifié avec succès
            pass_labe.setText("Password successfully updated!");

            // Rediriger l'utilisateur vers la page de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification/SignIn.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            // Clear the form fields
            password.setText("");
            confirmPassword.setText("");
        } catch (SQLException | IOException ex) {
            // Log the exception, and provide a user-friendly message
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            pass_labe.setText("Error updating password, please try again.");
        }
    }
*/

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Gérer l'exception NoSuchAlgorithmException
            e.printStackTrace();
            return null;
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
