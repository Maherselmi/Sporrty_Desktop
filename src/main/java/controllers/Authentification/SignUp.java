package controllers.Authentification;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import models.Role;
import models.User;
import services.ServiceUser;
import test.GMailer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SignUp {
    @FXML
    private Label imageFullPath;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button signUp;
    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private Label InscriMessageLabel;

    @FXML
    private ImageView ImageViewUser;
    @FXML
    private VBox vbox;

    private Parent fxml;

    @FXML
    private void signUp() {

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // Validate the form data
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            InscriMessageLabel.setText("Please complete all fields");
            return;
        }

        // Check if email has a valid format (using a simple regex for demonstration)
        if (!email.matches("^(.+)@(.+)$")) {
            InscriMessageLabel.setText("Enter a valid email address.");
            return;
        }

        // Check if the password meets a certain length requirement (e.g., at least 8 characters)
        if (password.length() < 8) {
            InscriMessageLabel.setText("The password must be at least 8 characters long.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            InscriMessageLabel.setText("Passwords do not match.");
            return;
        }

        // Check if the password meets strength criteria (e.g., a mix of letters and numbers)
        if (!password.matches("(?=.*[0-9])(?=.*[a-zA-Z]).{8,}")) {
            InscriMessageLabel.setText("The password must contain letters and numbers.");
            return;
        }

        // Create a User object with form data
        Role role = Role.ADHERANT;
        User user = new User(firstName, lastName, email, password, role,imageFullPath.getText());
        ServiceUser sp = new ServiceUser();

        // Check if the user already exists in the database
        try {
            if (sp.userExists(email)) {
                InscriMessageLabel.setText("User with this email already exists.");
                return;
            }

            // Call your method insertUser(User u) to insert the user into the database
            sp.insertUser(user);
            InscriMessageLabel.setText("Successful registration!");
            // Envoi de l'e-mail de confirmation
            String subject = "Confirmation d'inscription";
            String message = "Bonjour " + firstName + ",\n\nVotre inscription a été confirmée avec succès.";
            GMailer mailer = new GMailer();
            mailer.sendMail(email, subject, message);

               /*         // Ajouter le préfixe "+216" au numéro de téléphone
                     String phoneNumber = "+21656704145" ;

                        // Envoyer un SMS pour informer l'utilisateur que son compte est activé
                        sms.sendSMS(phoneNumber, "Votre compte a été activé avec succès! Merci d'utiliser notre service.");

*/

            // Clear the form fields
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");


        } catch (SQLException ex) {
            // Log the exception, and provide a user-friendly message
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            InscriMessageLabel.setText("Error registering, please try again.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert firstNameField != null : "fx:id=\"firstNameField\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert lastNameField != null : "fx:id=\"lastNameField\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert signUp != null : "fx:id=\"signUp\" was not injected: check your FXML file 'SignUp.fxml'.";

    }
    @FXML
    private void open_signin(ActionEvent event){
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 32);
        t.play();
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("/Authentification/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
    }

    /*  @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            imagePath = file.toURI().toString();
            imageFullPath.setText(file.getName());

            // Charger l'image dans l'ImageView
            Image image = new Image(imagePath);
            ImageViewUser.setImage(image);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }*/
    @FXML
    void uploadImage(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            imageFullPath.setText(file.getName());
        }
        String fileName = imageFullPath.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                // Get the resource URL for the uploads directory
                URL resourceUrl = getClass().getClassLoader().getResource("/uploads");
                if (resourceUrl == null) {
                    // If the directory doesn't exist, create it
                    File uploadsDirectory = new File("src/main/resources/upload");
                    if (!uploadsDirectory.exists()) {
                        uploadsDirectory.mkdirs();
                    }
                    resourceUrl = uploadsDirectory.toURI().toURL();
                }

                // Copy the uploaded file to the uploads directory
                Files.copy(new File(file.getPath()).toPath(), Paths.get(resourceUrl.toURI()).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                Image image = new Image(new FileInputStream(file));
                ImageViewUser.setImage(image);
            } catch (IOException | URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
