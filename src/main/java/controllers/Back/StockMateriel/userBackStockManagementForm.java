package controllers.Back.StockMateriel;


import API.GMailer;
import API.Mailing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Stock;
import services.ServiceStock;

import java.io.IOException;

public class userBackStockManagementForm {

    @FXML
    private Button CourMan;

    @FXML
    private Label cordonnetError;

    @FXML
    private TextField cordonnetStock;
    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button HomaMan;
    @FXML
    private Label LieuError;
    @FXML
    private Label NomError;
    @FXML
    private Label QuantiteError;


    @FXML
    private Label InscriMessageLabel;

    @FXML
    private TextField LieuStock;

    @FXML
    private Button LogOut;

    @FXML
    private TextField NomStock;

    @FXML
    private Button ProductMan;

    @FXML
    private TextField QuantiteStock;

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
    private Stock StockSelectionne;

    private Stock stockToUpdate; // Champ pour stocker les données du stock à mettre à jour
    public void setStock(Stock stock) {
        this.stockToUpdate = stock;
        StockSelectionne = stock;

        // Utilisez les données du stock pour mettre à jour les champs ou effectuer d'autres actions si nécessaire
        NomStock.setText(stock.getNom()); // Exemple d'utilisation : afficher le nom du stock dans un champ de texte
        LieuStock.setText(stock.getLieu()); // Afficher le lieu du stock
        QuantiteStock.setText(String.valueOf(stock.getQuantite())); // Afficher la quantité du stock
        cordonnetStock.setText(stock.getCordonnet());
    }


    @FXML
    void initialize() {
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert LieuStock != null : "fx:id=\"LieuStock\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert NomStock != null : "fx:id=\"NomStock\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert QuantiteStock != null : "fx:id=\"QuantiteStock\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert addT != null : "fx:id=\"addT\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackStockManagementForm.fxml'.";



    }
    void GoBack(){
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
    void Cancel(ActionEvent event) {
        GoBack();

    }




    @FXML
    void Submit(ActionEvent event) {
        clearErrorLabels();

        if (validateInput()) {
            try {
                ServiceStock ss = new ServiceStock();
                Stock s;

                if (StockSelectionne != null) {
                    // Si un stock est sélectionné, mettez à jour le stock existant
                    s = new Stock(StockSelectionne.getId(), NomStock.getText(), Integer.parseInt(QuantiteStock.getText()), LieuStock.getText(),cordonnetStock.getText());
                    ss.update(s);

                    // Envoyer un e-mail de notification pour la mise à jour du stock
                    try {
                        //Mailing.sendHtmlNotification("ademaloui744@gmail.com","Stock Notification",s.getNom(),"Updated");

                        GMailer gMailer = new GMailer();
                        gMailer.sendMail("Stock mis à jour", "Stock mis à jour : " + s.getNom());
                        System.out.println("Email envoyé avec succès !");
                    } catch (Exception e) {
                        System.err.println("Erreur lors de l'envoi de l'e-mail de notification : " + e.getMessage());
                    }
                } else {
                    // Sinon, insérez un nouveau stock
                    s = new Stock(0, NomStock.getText(), Integer.parseInt(QuantiteStock.getText()), LieuStock.getText(),cordonnetStock.getText());
                    ss.insert(s);

                    // Envoyer un e-mail de notification pour l'ajout du nouveau stock
                    try {
                        Mailing.sendHtmlNotification("ademaloui744@gmail.com","Stock Notification",s.getNom(),"Added");

                        //GMailer gMailer = new GMailer();
                        //gMailer.sendMail("Nouveau stock ajouté", "Nouveau stock ajouté : " + s.getNom());
                        System.out.println("Email envoyé avec succès !");
                    } catch (Exception e) {
                        System.err.println("Erreur lors de l'envoi de l'e-mail de notification : " + e.getMessage());
                    }
                }
                GoBack(); // Retourner à l'écran précédent après l'insertion ou la mise à jour du stock
            } catch (NumberFormatException e) {
                showAlert("Les champs Quantité et ID de Stock doivent être des nombres entiers valides.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Une erreur s'est produite. Veuillez réessayer.");
            }
        }
    }



    // Méthode pour envoyer un e-mail après l'ajout d'un stock



    // Méthode pour effacer les messages d'erreur
    private void clearErrorLabels() {
        NomError.setText("");
        QuantiteError.setText("");
        LieuError.setText("");

    }

    // Méthode pour valider la saisie
    private boolean validateInput() {
        boolean isValid = true;

        // Vérification de la validité du champ Nom
        if (NomStock.getText().isEmpty()) {
            NomError.setText("Le champ Nom est requis");
            isValid = false;
        }

        if(cordonnetStock.getText().isEmpty()){
            cordonnetError.setText("Le champ Cordonnet est requis");
            isValid = false;
        }

        // Vérification de la validité du champ Catégorie


        // Vérification de la validité du champ Quantité
        try {
            int quantite = Integer.parseInt(QuantiteStock.getText());
            if (quantite <= 0) {
                QuantiteError.setText("La quantité doit être un entier positif");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            QuantiteError.setText("La quantité doit être un entier valide");
            isValid = false;
        }

        // Vérification de la validité du champ ID de Stock
        if (LieuStock.getText().isEmpty()) {
            LieuError.setText("Le champ Lieu est requis");
            isValid = false;
        }

        return isValid;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void openStockManagement(ActionEvent actionEvent) {
    }

    public void openUserBackHome(ActionEvent actionEvent) {
    }
}