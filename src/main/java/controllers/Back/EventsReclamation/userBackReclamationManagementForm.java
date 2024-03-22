package controllers.Back.EventsReclamation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.reclamation;
import services.Mailing;
import services.ServiceReclamation;
import test.GMailer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userBackReclamationManagementForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button CourMan;

    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button HomaMan;

    @FXML
    private Label InscriMessageLabel;

    @FXML
    private Button LogOut;

    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private Button tfCancel;

    @FXML
    private TextArea tfRep;

    @FXML
    private Button tfSave;
    private  reclamation recSelectionne;
    @FXML
    private Label statutErrorLabel;
    @FXML
    private Label reponseErrorLabel;

    @FXML
    void GoBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackReclamationManagement.fxml"));
            Parent parent = loader.load();

            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void AnnulerForm(ActionEvent actionEvent) {
        GoBack();

    }
    public void setRecToUpdate(reclamation rec) {
        recSelectionne = rec;
        tfRep.setText(rec.getReponse());

        // Assurez-vous d'initialiser votre ComboBox avec les catégories disponibles
        statutComboBox.setItems(FXCollections.observableArrayList("In Progress","Finished"));
        // Ensuite, vous pouvez sélectionner la catégorie correspondante
        statutComboBox.setValue(rec.getStatut());


    }
    @FXML
    void repondreRec(ActionEvent event) {
        try {
            ServiceReclamation se = new ServiceReclamation();
            // Vérification des champs obligatoires
            boolean reponseEmpty = tfRep.getText().isEmpty();
            boolean statutEmpty = statutComboBox.getValue() == null;

            if (reponseEmpty || statutEmpty) {
                if (reponseEmpty) {
                    // Afficher un message d'erreur dans un label si la réponse est vide
                    reponseErrorLabel.setText("Please enter a response.");
                } else {
                    reponseErrorLabel.setText("");
                }

                if (statutEmpty) {
                    // Afficher un message d'erreur dans un label si le statut est vide
                    statutErrorLabel.setText("Please select a status.");
                } else {
                    statutErrorLabel.setText("");
                }

                return; // Arrêter l'exécution de la méthode si des champs sont vides
            }
            if (recSelectionne != null) {

                // Mettre à jour les propriétés de l'événement sélectionné
                recSelectionne.setReponse(tfRep.getText());

                recSelectionne.setStatut(statutComboBox.getValue());

                se.updateOne(recSelectionne);
                Mailing.sendHtmlNotification("ademaloui744@gmail.com","Reclamation Notification",recSelectionne.getReponse(),"Updated");

            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackReclamationManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
            String subject = "Reclamation ";
            String message = "Bonjour ";
            GMailer mailer = new GMailer();
            mailer.sendMail("dinagharbi893@gmail.com", subject, message); } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openEventManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackEventManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent


            // Récupérer la scène actuelle
            Scene scene = EventMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void LogOut(ActionEvent event) {

    }

    @FXML
    void openCourManagement(ActionEvent event) {

    }



    @FXML
    void openGymManagement(ActionEvent event) {

    }

    @FXML
    void openProductManagement(ActionEvent event) {

    }



    @FXML
    void openStockManagement(ActionEvent event) {

    }

    @FXML
    void openUserBackHome(ActionEvent event) {

    }

    @FXML
    void openUserManagement(ActionEvent event) {

    }




    @FXML
    void initialize() {
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert statutComboBox != null : "fx:id=\"statutComboBox\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert tfCancel != null : "fx:id=\"tfCancel\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert tfRep != null : "fx:id=\"tfRep\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";
        assert tfSave != null : "fx:id=\"tfSave\" was not injected: check your FXML file 'userBackReclamationManagementForm.fxml'.";

    }

    public void openReclamationManagement(ActionEvent event) {
    }
}
