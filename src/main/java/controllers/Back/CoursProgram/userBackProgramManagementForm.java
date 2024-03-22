package controllers.Back.CoursProgram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.programme;
import services.servicesProgramme;

import java.io.IOException;
import java.sql.SQLException;

public class userBackProgramManagementForm {

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
    private Button tfajouter;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfDuree;

    @FXML
    private Button tfannuler;

    @FXML
    private Label InscriMessageLabel;

    @FXML
    private TextArea tfDescrip;
    @FXML
    private HBox root;

    @FXML
    private Label Nomerror;

    @FXML
    private Label dureeerror;

    @FXML
    private Label descriperror;
    private programme programmeSelectionne;
    private String contenu;


    @FXML
    void AnnulerForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackProgramManagement.fxml"));
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
    void LogOut(ActionEvent event) {

    }

    @FXML
    void ajouterProg(ActionEvent event) {

        try {

            boolean isValid = true;
            // Validate input fields
            if (tfNom.getText().isEmpty()) {
                Nomerror.setText("Name is required");
                isValid = false;
            } else {
                Nomerror.setText("");
            }

            if (tfDuree.getText().isEmpty()) {
                dureeerror.setText("Duree is required");
                isValid = false;
            } else {
                dureeerror.setText("");
            }

            if (tfDescrip.getText().isEmpty()) {
                descriperror .setText("Description is required");
                isValid = false;
            } else {
                descriperror .setText("");
            }

           servicesProgramme sp = new servicesProgramme();
            if (programmeSelectionne != null) {
                // Update the properties of the selected program
               programmeSelectionne.setNom(tfNom.getText());
                programmeSelectionne.setDescription(tfDescrip.getText());

                programmeSelectionne.setDuree(parsePositiveInt(tfDuree.getText()));

                sp.updateOneP(programmeSelectionne);
            } else {
                // Create a new program if none is selected

               programme programme = new programme(tfNom.getText(), tfDescrip.getText(),  parsePositiveInt(tfDuree.getText()));
                sp.insertOneP(programme);
            }

            // Load the new interface after adding/updating the program
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackProgramManagement.fxml"));
            Parent parent = loader.load();
            Scene scene = UserMan.getScene();
            scene.setRoot(parent);
        } catch (SQLException | IOException | NumberFormatException e) {
            // Handle errors
            /*
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();*/
        }
    }


    // Define appropriate methods to handle SQLException and NumberFormatException
    void handleSQLException() {
        System.out.println("An error occurred while inserting the program. Please try again later.");
    }

    void handleNumberFormatException() {
        System.out.println("Please enter valid numeric values for duration.");
    }

    int parsePositiveInt(String text) throws NumberFormatException {
        int value = Integer.parseInt(text);
        if (value <= 0) {
            throw new NumberFormatException("The value must be a positive integer.");
        }
        return value;
    }



    @FXML
    void openCourManagement(ActionEvent event) {

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
    void openGymManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackGymManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = GymMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void openProductManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProduitPanier/userBackProductManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = ProductMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void openReclamationManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackReclamationManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = ReclamMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void openStockManagement(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackStockManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = StockMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openUserBackHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = HomaMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void openUserManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
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
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert tfajouter != null : "fx:id=\"tfajouter\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert tfNom != null : "fx:id=\"tfNom\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert tfDuree != null : "fx:id=\"tfDuree\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert tfannuler != null : "fx:id=\"tfannuler\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";
        assert tfDescrip != null : "fx:id=\"tfDescrip\" was not injected: check your FXML file 'userBackProgramManagementForm.fxml'.";

    }


    public void setTabprogrammeToUpdate(programme programme) {
        programmeSelectionne = programme;

        // Set values for your text fields
        tfNom.setText(programme.getNom());
        tfDescrip.setText(programme.getDescription());
        tfDuree.setText(String.valueOf(programme.getDuree()));
    }

    @FXML


    private void afficherErreur(String aucunprogrammeSélectionné, String s, String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherSucces(String modificationRéussie, String s, String titre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);

        alert.setContentText((String) contenu);

        // Supprime le bouton de fermeture de l'alerte
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.OK);

        alert.showAndWait();

    }

}