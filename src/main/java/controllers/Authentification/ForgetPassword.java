package controllers.Authentification;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import test.GMailer;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgetPassword {

    @FXML
    private TextField mail;

    @FXML
    private Button send_mail;
    @FXML
    private TextField code;

    @FXML
    private Button code_button;
    private String resetCode;

    private int generatedCode;

    @FXML
    private void handleResetPassword(ActionEvent event) {
        String email = mail.getText().trim();
// Générer un code aléatoire de 6 chiffres
        Random random = new Random();
        generatedCode = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999

        // Enregistrer le code généré dans une variable de classe pour pouvoir le vérifier ultérieurement
        this.resetCode = String.valueOf(code);

        // Envoyer le code par e-mail à l'utilisateur
        try {
            GMailer mailer = new GMailer();
           mailer.sendResetCode(email, String.valueOf(generatedCode));

            System.out.println("Code envoyé avec succès. Veuillez vérifier votre e-mail.");

            // Afficher le champ pour saisir le code et le bouton de vérification
            code.setVisible(true);
            code_button.setVisible(true);
        } catch (Exception e) {
            // Gérer l'erreur d'envoi de l'e-mail
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVerifyCode(ActionEvent event) {
        String codeText = code.getText().trim();
        if (!codeText.isEmpty()) {
            try {
                int enteredCode = Integer.parseInt(codeText);
                if (enteredCode == generatedCode) {
                    // Le code saisi correspond au code généré, vous pouvez permettre à l'utilisateur de réinitialiser son mot de passe
                    System.out.println("Code correct, vous pouvez réinitialiser votre mot de passe.");

                    // Récupérer l'utilisateur depuis la base de données en utilisant son email
                    String email = mail.getText();;
                    ServiceUser serviceUser = new ServiceUser();
                    User user = serviceUser.selectByEmail(email);

                    // Fermer la fenêtre actuelle
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

                    // Charger la page newPassword.fxml
                    loadNewPasswordPage(user);
                } else {
                    System.out.println("Code incorrect, veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un code valide.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération de l'utilisateur depuis la base de données : " + e.getMessage());
            }
        } else {
            System.out.println("Veuillez saisir le code reçu par e-mail.");
        }
    }

    private void loadNewPasswordPage(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification/newPassword.fxml"));
            Parent root = loader.load();

            // Passer les données de l'utilisateur à la nouvelle page
            NewPassword newPasswordController = loader.getController();
            newPasswordController.initData(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









}