package controllers.Back.UserAbonement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.Role;
import models.User;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userBackUserManagementForm {
    @FXML
    private Label InscriMessageLabel1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

   /* @FXML
    private TextField passwordField;
*/
   @FXML
   private PasswordField passwordField;
    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private  PasswordField confirmPasswordField;

    @FXML
    private Label InscriMessageLabel;
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
        roleComboBox.getItems().addAll(Role.ADHERANT, Role.ADMIN, Role.COACH);
        assert firstNameField != null : "fx:id=\"firstNameField\" was not injected: check your FXML file 'CreateFxml.fxml'.";
        assert lastNameField != null : "fx:id=\"lastNameField\" was not injected: check your FXML file 'CreateFxml.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'CreateFxml.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'CreateFxml.fxml'.";
        assert roleComboBox != null : "fx:id=\"roleComboBox\" was not injected: check your FXML file 'CreateFxml.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'CreateFxml.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'CreateFxml.fxml'.";

    }
    @FXML
    private void signUp() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Valider les données du formulaire
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Afficher un message d'erreur à l'utilisateur
            InscriMessageLabel.setText("Please complete all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            // Afficher un message d'erreur à l'utilisateur
            InscriMessageLabel.setText("\n" +
                    "The passwords do not match.");
            return;
        }

        Role selectedRole = roleComboBox.getValue();
        if (selectedRole == null) {
            // Afficher un message d'erreur à l'utilisateur
            InscriMessageLabel.setText("Please select a role.");
            return;
        }

        // Vérifier si l'utilisateur existe déjà avec cet email
        ServiceUser sp = new ServiceUser();
        try {
            if (sp.userExists(email)) {
                InscriMessageLabel.setText("A user with this email already exists.");
                return;
            }

            // Créer un objet User avec les données du formulaire
            User user = new User(firstName, lastName, email, password, selectedRole);
            // Appeler votre méthode insertUser(User u) pour insérer l'utilisateur dans la base de données
            sp.insertUser(user);
            InscriMessageLabel1.setText("Successful registration !");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


/*
    @FXML
    private void signUp() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Valider les données du formulaire
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Afficher un message d'erreur à l'utilisateur
            InscriMessageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            // Afficher un message d'erreur à l'utilisateur
            InscriMessageLabel.setText("Les mots de passe ne correspondent pas.");
            return;
        }

        Role selectedRole = roleComboBox.getValue();
        if (selectedRole == null) {
            // Afficher un message d'erreur à l'utilisateur
            InscriMessageLabel.setText("Veuillez sélectionner un rôle.");
            return;
        }

        // Créer un objet User avec les données du formulaire
        User user = new User(firstName, lastName, email, password, selectedRole);
        ServiceUser sp = new ServiceUser();
        // Appeler votre méthode insertUser(User u) pour insérer l'utilisateur dans la base de données
        try {
            sp.insertUser(user);
            InscriMessageLabel.setText("Inscription réussie !");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }*/

    @FXML
    void openUserBackHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void openStockManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackStockManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void openUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackUserManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void LogOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification/SignIn.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openCourManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackCoursManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene =  InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openEventManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackEventManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene =  InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openGymManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackGymManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openProductManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProduitPanier/userBackProductManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openReclamationManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackReclamationManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = InscriMessageLabel.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}