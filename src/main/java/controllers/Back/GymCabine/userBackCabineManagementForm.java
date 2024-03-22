package controllers.Back.GymCabine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import models.Cabine;
import models.SalleDeSport;
import services.Mailing;
import services.ServiceCabine;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class userBackCabineManagementForm {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Label nomer;

    @FXML
    private Label capaer;

    @FXML
    private Label haser;

    @FXML
    private URL location;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Button HomaMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button CourMan;

    @FXML
    private Button EventMan;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button LogOut;

    @FXML
    private Button GymMan;

    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button addT;


    @FXML
    private TextField nomField;

    @FXML
    private TextField CapaciteField;

    @FXML
    private Label uploadPath;

    @FXML
    private ComboBox<Boolean> vrComboBox;


    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Label InscriMessageLabel;

    @FXML
    private Label gymName;
    private SalleDeSport GymSelectionne;
    private Cabine CabineSelectionne;
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            uploadPath.setText(file.getName());
        }
        String fileName = uploadPath.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                // Get the resource URL for the uploads directory
                URL resourceUrl = getClass().getClassLoader().getResource("uploads");
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
            } catch (IOException | URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    void GoBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackCabineManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            userBackCabineManagement controller = loader.getController();

            controller.setGym(GymSelectionne);
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
            Scene scene = UserMan.getScene();
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
            Scene scene = UserMan.getScene();
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
            Scene scene = UserMan.getScene();
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
            Scene scene = UserMan.getScene();
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
            Scene scene =UserMan.getScene();
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
            Scene scene = UserMan.getScene();
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
            Scene scene = UserMan.getScene();
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
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void signUp(ActionEvent event) {

    }


    @FXML
    void addCabine(ActionEvent event) {
        clearErrorLabels(); // Effacer les messages d'erreur précédents

        // Valider les champs de saisie
        if (validateInput()) {
            // Si la validation réussit, procédez à l'insertion ou à la mise à jour
            try {
                ServiceCabine sm = new ServiceCabine();
                Cabine c;

                if (CabineSelectionne != null) {
                    c = new Cabine(CabineSelectionne.getId(), nomField.getText(),Integer.parseInt(CapaciteField.getText()), vrComboBox.getValue(), GymSelectionne,uploadPath.getText());
                    sm.update(c);
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Cabine Notification",nomField.getText(),"Updated");
                } else {
                    c = new Cabine(nomField.getText(),Integer.parseInt(CapaciteField.getText()), vrComboBox.getValue(), GymSelectionne,uploadPath.getText());
                    sm.insert(c);
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Cabine Notification",nomField.getText(),"Added");

                }

                GoBack(); // Méthode pour revenir en arrière après l'opération (à implémenter)
            } catch (NumberFormatException e) {
                // Gérer l'exception pour les conversions de chaînes en nombres
                showAlert("oooooooooooooo.");
            } catch (Exception e) {
                // Gérer toute autre exception imprévue
                e.printStackTrace(); // Afficher la trace de la pile pour le débogage
                showAlert("Une erreur s'est produite. Veuillez réessayer.");
            }
        }
    }

    // Méthode pour effacer les messages d'erreur
    private void clearErrorLabels() {
        nomer.setText("");
        capaer.setText("");
        haser.setText("");
           }

    // Méthode pour valider la saisie
    private boolean validateInput() {
        boolean isValid = true;

        // Vérification de la validité du champ Nom
        if (nomField.getText().isEmpty()) {
            nomer.setText("Le champ Nom est requis");
            isValid = false;
        }

        // Vérification de la validité du champ vrcombobox
        if (vrComboBox.getValue() == null) {
            haser.setText("Le champ has_vr  est requis");
            isValid = false;
        }

        // Vérification de la validité du champ capacite
        try {
            int capacite = Integer.parseInt(CapaciteField.getText());
            if (capacite <= 0) {
                capaer.setText("la capacite doit être un entier positif");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            capaer.setText("la capacite doit être un entier valide");
            isValid = false;
        }



        return isValid;
    }

    // Méthode pour afficher une alerte avec un message donné

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



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
    void cancel(ActionEvent event) {
        GoBack();
    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert addT != null : "fx:id=\"addT\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackCabineManagementForm.fxml'.";

    }

    public void setGym(SalleDeSport gymSelectionne) {
        GymSelectionne = gymSelectionne;
        gymName.setText(gymSelectionne.getNom_salle());
    }

    public void setCabine(Cabine cabineSelectionne) {
        CabineSelectionne = cabineSelectionne;
        nomField.setText(cabineSelectionne.getNom_cabine());
        vrComboBox.setValue(cabineSelectionne.isHas_vr());
        CapaciteField.setText(String.valueOf(cabineSelectionne.getCapacite()));
        uploadPath.setText(cabineSelectionne.getImage());

    }

}
