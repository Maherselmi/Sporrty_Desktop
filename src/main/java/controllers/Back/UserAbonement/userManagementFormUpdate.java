package controllers.Back.UserAbonement;
import javafx.scene.control.PasswordField;
import models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Role;
import models.User;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class userManagementFormUpdate {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;
    @FXML
    private Label mess_Lab;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label InscriMessageLabel;

    private User user;

    @FXML
    void initialize() {
        roleComboBox.getItems().addAll(Role.ADHERANT, Role.ADMIN, Role.COACH);}
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
    private void LogOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/loginFxml.fxml"));
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

/*
    @FXML
    private void handleSubmitB(ActionEvent event) {
        // Récupérer les valeurs des champs
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Vérifier si les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            // Afficher un message d'erreur dans la label mess_Lab
            mess_Lab.setText("Les mots de passe ne correspondent pas.");
            return;
        }

        // Vérifier si l'email est valide en utilisant une expression régulière
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            // Afficher un message d'erreur dans la label mess_Lab
            mess_Lab.setText("Email invalide.");
            return;
        }

        // Vérifier si le mot de passe respecte les critères (au moins 8 caractères et un chiffre)
        if (!password.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{8,}$")) {
            // Afficher un message d'erreur dans la label mess_Lab
            mess_Lab.setText("Le mot de passe doit contenir au moins 8 caractères et un chiffre.");
            return;
        }

        // Mettre à jour les propriétés de l'utilisateur
        user.setNom(firstName);
        user.setPrenom(lastName);
        user.setEmail(email);
        user.setPassword(password);

        try {
            // Appeler la méthode update de votre service pour mettre à jour l'utilisateur
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.updateUser(user);

            // Afficher un message de succès à l'utilisateur
            mess_Lab.setText("Utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            // En cas d'erreur, afficher un message d'erreur dans la label mess_Lab
            mess_Lab.setText("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }
*/
@FXML
private void handleSubmitB(ActionEvent event) {
    // Récupérer les valeurs des champs
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String email = emailField.getText();
    String password = passwordField.getText();
    String confirmPassword = confirmPasswordField.getText();

    // Vérifier si les mots de passe correspondent
    if (!password.equals(confirmPassword)) {
        // Afficher un message d'erreur dans la label mess_Lab
        mess_Lab.setText("Passwords do not match.");
        return;
    }

    // Vérifier si l'email est valide en utilisant une expression régulière
    if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        // Afficher un message d'erreur dans la label mess_Lab
        mess_Lab.setText("Invalid email.");
        return;
    }

    // Vérifier si le mot de passe respecte les critères (au moins 8 caractères et un chiffre)
    if (!password.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{8,}$")) {
        // Afficher un message d'erreur dans la label mess_Lab
        mess_Lab.setText("The password must contain at least 8 characters and one number.");
        return;
    }

    // Mettre à jour les propriétés de l'utilisateur
    user.setNom(firstName);
    user.setPrenom(lastName);
    user.setEmail(email);
    user.setPassword(password);

    try {

        // Appeler la méthode update de votre service pour mettre à jour l'utilisateur

        // Vérifier si l'utilisateur existe déjà avec cet email
        ServiceUser serviceUser = new ServiceUser();

            if (serviceUser.userExists(email)) {
                // Afficher un message d'erreur dans la label mess_Lab
                mess_Lab.setText("A user with this email already exists.");
                return;
            }

            serviceUser.updateUser(user);

        // Afficher un message de succès à l'utilisateur
        mess_Lab.setText("User updated successfully !");

    } catch (SQLException e) {
        // En cas d'erreur, afficher un message d'erreur dans la label mess_Lab
        mess_Lab.setText("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
    }
}
@FXML
    public void initData(User user) {
        // Remplir le formulaire avec les données de l'utilisateur sélectionné
        this.user = user;
        firstNameField.setText(user.getNom());
        lastNameField.setText(user.getPrenom());
        emailField.setText(user.getEmail());
        passwordField.setText(""); // Laisser le champ de mot de passe vide
    }

}
