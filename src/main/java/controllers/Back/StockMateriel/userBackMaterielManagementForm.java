package controllers.Back.StockMateriel;

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
import models.Materiel;
import models.Stock;
import services.ServiceMateriel;
import services.ServiceStock;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class userBackMaterielManagementForm {

    @FXML
    private ComboBox<String> CategorieComboBox;

    @FXML
    private Button CourMan;
    @FXML
    private Label Error_Image_Label;
    @FXML
    private Label stockErrorLabel;
    @FXML
    private Label error_lien_video;



    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button HomaMan;
    @FXML
    private TextField Id_StockField;




    @FXML
    private Label InscriMessageLabel;

    @FXML
    private Button LogOut;

    @FXML
    private TextField NomFeild;
    @FXML
    private Label categorieErrorLabel;

    @FXML
    private Label idStockErrorLabel;

    @FXML
    private Label nomErrorLabel;

    @FXML
    private Label quantiteErrorLabel;

    @FXML
    private Button ProductMan;

    @FXML
    private TextField QuantiteField;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button addT;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;
    private Materiel MaterielSelectionne;

    @FXML
    private ComboBox<String> StockComboBox;


    @FXML
    private Label imagePath;

    @FXML
    private TextField Lien_VideoTF;

    @FXML
    void initialize() {
        assert CategorieComboBox != null : "fx:id=\"CategorieComboBox\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        CategorieComboBox.getItems().addAll("Musculation", "Cardio", "Yoga " , "Musculation","Aérobic");


        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert Id_StockField != null : "fx:id=\"Id_StockField\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert NomFeild != null : "fx:id=\"NomFeild\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert QuantiteField != null : "fx:id=\"QuantiteField\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert addT != null : "fx:id=\"addT\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackMaterielManagementForm.fxml'.";
        Id_StockField = new TextField();


        ServiceStock serviceStock = new ServiceStock();
        try {
            for (Stock stock:serviceStock.selectAll())
                StockComboBox.getItems().add(stock.getNom());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    void GoBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackMaterielManagement.fxml"));
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
    void Cancel(ActionEvent event) {
        GoBack();

    }


    @FXML
    void Submit(ActionEvent event) {
        clearErrorLabels(); // Effacer les messages d'erreur précédents

        // Valider les champs de saisie
        if (validateInput()) {
            // Si la validation réussit, procédez à l'insertion ou à la mise à jour
            try {
                ServiceMateriel sm = new ServiceMateriel();
                Materiel m;

                ServiceStock serviceStock = new ServiceStock();

                if (MaterielSelectionne != null) {

                    m = new Materiel(MaterielSelectionne.getId(), NomFeild.getText(), CategorieComboBox.getValue(), Integer.parseInt(QuantiteField.getText()), serviceStock.selectByName(StockComboBox.getValue()),Lien_VideoTF.getText(),imagePath.getText());
                    sm.update(m);
                } else {
                    m = new Materiel(0, NomFeild.getText(), CategorieComboBox.getValue(), Integer.parseInt(QuantiteField.getText()), serviceStock.selectByName(StockComboBox.getValue()),Lien_VideoTF.getText(),imagePath.getText());
                    sm.insert(m);
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
        nomErrorLabel.setText("");
        categorieErrorLabel.setText("");
        quantiteErrorLabel.setText("");
        idStockErrorLabel.setText("");
    }

    // Méthode pour valider la saisie
    private boolean validateInput() {
        boolean isValid = true;

        // Vérification de la validité du champ Nom
        // Vérification de la validité du champ Nom
        String nomMateriel = NomFeild.getText();
        if (nomMateriel.isEmpty()) {
            nomErrorLabel.setText("Le champ Nom est requis");
            isValid = false;
        }

        // Vérification de la validité du champ Catégorie
        String categorieSelectionnee = CategorieComboBox.getValue();
        if (categorieSelectionnee == null || categorieSelectionnee.isEmpty()) {
            categorieErrorLabel.setText("Le champ Catégorie est requis");
            isValid = false;
        }


        // Vérification de la validité du champ Quantité
        try {
            int quantite = Integer.parseInt(QuantiteField.getText());
            if (quantite <= 0) {
                quantiteErrorLabel.setText("La quantité doit être un entier positif");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            quantiteErrorLabel.setText("La quantité doit être un entier valide");
            isValid = false;
        }
        // Vérification de la validité du champ Stock
        String stockSelectionne = StockComboBox.getValue();
        if (stockSelectionne == null || stockSelectionne.isEmpty()) {
            stockErrorLabel.setText("Le champ Stock est requis");
            isValid = false;
        }
        String imageName = imagePath.getText();
        if (imageName == null || imageName.isEmpty()) {
            Error_Image_Label.setText("Le champ Image est requis");
            isValid = false;
        }

        // Vérification de la validité du champ lien vidéo
        String videoLink = Lien_VideoTF.getText();
        if (videoLink == null || videoLink.isEmpty()) {
            error_lien_video.setText("Le champ Lien vidéo est requis");
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
    private Materiel MaterielToUpdate; // Champ pour stocker les données du stock à mettre à jour
    public void setMateriel( Materiel materiel) {
        this.MaterielToUpdate =materiel ;
        MaterielSelectionne= materiel;

        // Utilisez les données du stock pour mettre à jour les champs ou effectuer d'autres actions si nécessaire
        NomFeild.setText(materiel.getNom()); // Exemple d'utilisation : afficher le nom du stock dans un champ de texte
        CategorieComboBox.setValue(materiel.getCategorie());// Afficher le lieu du stock
        Id_StockField.setText(String.valueOf(materiel.getId_stock())); // Afficher la quantité du sstock
        Lien_VideoTF.setText(materiel.getVideo());
        imagePath.setText(materiel.getImage());
        ServiceStock serviceStock = new ServiceStock();

        StockComboBox.setValue(materiel.getId_stock().getNom());
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
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            imagePath.setText(file.getName());
        }
        String fileName = imagePath.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                // If the directory doesn't exist, create it
                File uploadsDirectory = new File("src/main/resources/upload");
                if (!uploadsDirectory.exists()) {
                    uploadsDirectory.mkdirs();
                }
                URL resourceUrl = uploadsDirectory.toURI().toURL();

                // Copy the uploaded file to the uploads directory
                Files.copy(new File(file.getPath()).toPath(), Paths.get(resourceUrl.toURI()).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace(); // Handle exception properly based on your application's requirements
            }
        }
    }
}