package controllers.Back.GymCabine;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import models.SalleDeSport;
import services.Mailing;
import services.ServiceSalle;

public class userBackGymManagementForm {

    @FXML
    private ResourceBundle resources;

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
    private TextField NomGym;

    @FXML
    private TextField NumeroGym;

    @FXML
    private TextField LieuGym;

    @FXML
    private TextField locationField;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Label InscriMessageLabel;
    @FXML
    private Label noms;

    @FXML
    private Label locationError;
    @FXML
    private Label nums;

    @FXML
    private Label lieus;

    @FXML
    private Label descs;

    @FXML
    private TextArea DescriptionGym;
    private SalleDeSport GymSelectionne;

    @FXML
    private TextField LienGym;

    @FXML
    private Label liens;

    @FXML
    private Label uploads;

    @FXML
    private Label uploadPath;

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
                uploads.setText(e.getMessage());
            }
        }
    }
    void GoBack(){
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
    void CancelGym(ActionEvent event) {

        GoBack();
    }

    @FXML
    void SubmitGym(ActionEvent event) {
        clearErrorLabels(); // Effacer les messages d'erreur précédents

        // Valider les champs de saisie
        if (validateInput()) {
            // Si la validation réussit, procédez à l'insertion ou à la mise à jour
            try {
                ServiceSalle ss = new ServiceSalle();
                SalleDeSport s;

                if (GymSelectionne != null) {
                    s= new SalleDeSport(GymSelectionne.getId_salle(), Integer.parseInt(NumeroGym.getText()) ,NomGym.getText(),DescriptionGym.getText() , LieuGym.getText(),LienGym.getText(),uploadPath.getText(),locationField.getText());
                    ss.update(s);
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Gym Notification",NomGym.getText(),"Updated");
                } else {
                    s= new SalleDeSport(NomGym.getText() , DescriptionGym.getText() , LieuGym.getText(),Integer.parseInt(NumeroGym.getText()),LienGym.getText(),uploadPath.getText(),locationField.getText());
                    ss.insert(s);
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Gym Notification",NomGym.getText(),"Added");
                }

                GoBack(); // Méthode pour revenir en arrière après l'opération (à implémenter)
            } catch (NumberFormatException e) {
                // Gérer l'exception pour les conversions de chaînes en nombres
                showAlert("Les champs Quantité et ID de Stock doivent être des nombres entiers valides.");
            } catch (Exception e) {
                // Gérer toute autre exception imprévue
                e.printStackTrace(); // Afficher la trace de la pile pour le débogage
                showAlert("Une erreur s'est produite. Veuillez réessayer.");
            }
        }
    }

    // Méthode pour effacer les messages d'erreur
    private void clearErrorLabels() {
        noms.setText("");
        lieus.setText("");
        descs.setText("");
        nums.setText("");
    }

    public boolean validateYouTubeUrl(String url) {
        return url != null && (url.contains("youtube.com") || url.contains("youtu.be"));
    }
    // Méthode pour valider la saisie
    private boolean validateInput() {
        boolean isValid = true;

        // Vérification de la validité du champ Nom
        if (NomGym.getText().isEmpty()) {
            noms.setText("Le champ Nom est requis");
            isValid = false;
        }
        if (LieuGym.getText().isEmpty()) {
            lieus.setText("Le champ Lieu est requis");
            isValid = false;
        }
        if (NumeroGym.getText().isEmpty()) {
            nums.setText("Le champ Numero est requis");
            isValid = false;
        } else if (NumeroGym.getText().length() != 8) {
            nums.setText("Le numéro doit contenir exactement 8 caractères");
            isValid = false;
        }

        if (DescriptionGym.getText().isEmpty()) {
            descs.setText("Le champ Description est requis");
            isValid = false;
        }
        if(locationField.getText().isEmpty()){
            locationError.setText("Le champ Location est requis");
            isValid = false;
        }
        // Usage
        if (!validateYouTubeUrl(LienGym.getText())) {
            liens.setText("Le champ LienGym doit être un lien YouTube valide");
            isValid = false;
        }
                // Vérification de la validité du champ Quantité
        try {
            int num_salle = Integer.parseInt(NumeroGym.getText());
            if (num_salle <= 0) {
                nums.setText("le numero doit être un entier positif");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            nums.setText("le numero doit être un entier valide");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackGymManagementForm.fxml"));
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
    void openProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackProfile.fxml"));
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
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert addT != null : "fx:id=\"addT\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert NomGym != null : "fx:id=\"NomGym\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert NumeroGym != null : "fx:id=\"NumeroGym\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert LieuGym != null : "fx:id=\"LieuGym\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";
        assert DescriptionGym != null : "fx:id=\"DescriptionGym\" was not injected: check your FXML file 'userBackGymManagementForm.fxml'.";

    }

    public void setGymToUpDate(SalleDeSport salleSelectionne) {
        GymSelectionne = salleSelectionne;
        NomGym.setText(salleSelectionne.getNom_salle());
        DescriptionGym.setText(salleSelectionne.getDescr());
        LieuGym.setText(salleSelectionne.getLieu_salle());
        NumeroGym.setText(String.valueOf(salleSelectionne.getNum_salle()));
        uploadPath.setText(salleSelectionne.getImage());
        LienGym.setText(salleSelectionne.getLienVideo());
        locationField.setText(salleSelectionne.getLocation());

    }
}
