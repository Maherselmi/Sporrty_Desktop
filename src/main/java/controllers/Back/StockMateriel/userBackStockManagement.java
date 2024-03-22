package controllers.Back.StockMateriel;


import API.GMailer;
import API.MapGeo;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Stock;
import services.ServiceStock;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class userBackStockManagement {

    @FXML
    private Button CourMan;

    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;
    @FXML
    private TextField tf_recherche;

    @FXML
    private Button HomeMan;
    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TableColumn<?, ?> Lieu;

    @FXML
    private TableColumn<?, ?> Nom;

    @FXML
    private Button ProductMan;
    @FXML
    private Button StockMan;

    @FXML
    private TableColumn<?, ?> Quantite;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button btn_workbench111;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111111;

    @FXML
    private Button btn_workbench111112;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Button btn_workbench11111211;

    @FXML
    private Button btn_workbench111112111;

    @FXML
    private Button btn_workbench1111121111;

    @FXML
    private Button btn_workbench13;

    @FXML
    private TableColumn<?, ?> colModif;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableView<Stock> tableView;


    @FXML
    private Label gymLabel;
    private Stock StockSelectionne;


    @FXML
    void handleRowSelection(MouseEvent event) {
        if (event.getClickCount() > 0) {
            StockSelectionne = (Stock) tableView.getSelectionModel().getSelectedItem();
        }
    }

    private void afficherStock() {
        // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView
        tableView.setOnMouseClicked(this::handleRowSelection);
        //initialiser les colonnes
        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Quantite.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
        Lieu.setCellValueFactory(new PropertyValueFactory<>("Lieu"));
        //affichage
        try {
            ServiceStock service = new ServiceStock();
            List<Stock> CabineList = service.selectAll();

            // Clear existing items in the TableView
            tableView.getItems().clear();

            // Add all items from the list to the TableView
            tableView.getItems().addAll(CabineList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors du chargement des données");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors du chargement des données. Veuillez réessayer plus tard.");
            alert.show();

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
    void showMap(ActionEvent event) {
        MapGeo mapGeo = new MapGeo();

        ServiceStock serviceStock = new ServiceStock();
        try {
            List<Stock> stocks = serviceStock.selectAll();
            for(Stock stock: stocks){
                String cord = stock.getCordonnet();
                String[] latLong = cord.split(",");
                if (latLong.length == 2) { // Ensure we have both latitude and longitude
                    double latitude = Double.parseDouble(latLong[0].trim());
                    double longitude = Double.parseDouble(latLong[1].trim());
                    mapGeo.addMarker(latitude, longitude);
                }            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        WebView webView = mapGeo.getWebView();
        Stage stage = new Stage();
        Scene scene = new Scene(webView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void openStockManagement() {
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
    void GoToMateriel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackMaterielManagement.fxml"));
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
            Scene scene = UserMan.getScene();
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
    private void openUserBackHome() {
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
    void openProfile(ActionEvent event) {



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
    void initialize() {
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert HomeMan != null : "fx:id=\"HomeMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert Lieu != null : "fx:id=\"Lieu\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert Nom != null : "fx:id=\"Nom\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert Quantite != null : "fx:id=\"Quantite\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";

        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench111112 != null : "fx:id=\"btn_workbench111112\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench11111211 != null : "fx:id=\"btn_workbench11111211\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench1111121111 != null : "fx:id=\"btn_workbench1111121111\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert colModif != null : "fx:id=\"colModif\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert tf_recherche != null : "fx:id=\"tf_recherche\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        assert combobox != null : "fx:id=\"combobox\" was not injected: check your FXML file 'userBackStockManagement.fxml'.";
        combobox.getItems().addAll("Quantite", "Nom");
        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Quantite")) {
                // Appeler la méthode pour trier par quantité
                trierParQuantite();
            } else if (newValue.equals("Nom")) {
                trierParNom();
            }
        });



        afficherStock();
    }

    private void trierParQuantite() {
        // Trier les éléments dans la TableView par quantité (ordre descendant)
        tableView.getItems().sort((stock1, stock2) -> {
            if (stock1.getQuantite() > stock2.getQuantite()) {
                return -1;
            } else if (stock1.getQuantite() < stock2.getQuantite()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    private void trierParNom() {
        // Trier les éléments dans la TableView par nom (ordre alphabétique)
        tableView.getItems().sort((stock1, stock2) -> stock1.getNom().compareToIgnoreCase(stock2.getNom()));
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

    public void setGym(Stock salleSelectionne) {
        System.out.println(salleSelectionne.getId());
        StockSelectionne = salleSelectionne;
        gymLabel.setText(salleSelectionne.getNom());
    }


    @FXML
    void AddStock(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackStockManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Get the controller after the FXML file has been loaded
            userBackStockManagementForm controller = loader.getController();

            // Récupérer les informations nécessaires pour envoyer l'e-mail
            String senderEmail = "slimiahmed478@gmail.com";
            String senderPassword = "Mmaa123456";
            String recipientEmail = "aminlouati145@gmail.com\n";
            String subject = "Nouvel ajout de stock";
            String content = "Un nouveau stock a été ajouté.";

            // Envoyer l'e-mail en utilisant la classe MailerAPI


            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void updateStock(ActionEvent event) {
        if (StockSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackStockManagementForm.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                // Obtenir le contrôleur du formulaire de gestion du stock
                userBackStockManagementForm controller = loader.getController();

                // Passer le stock sélectionné au contrôleur du formulaire de gestion du stock
                controller.setStock(StockSelectionne);

                // Récupérer la scène actuelle
                Scene scene = StockMan.getScene();

                // Remplacer le contenu de la scène actuelle par le contenu du formulaire de gestion du stock
                scene.setRoot(parent);
            } catch (IOException e) {
                // Afficher l'erreur dans la console ou un fichier journal
                e.printStackTrace();

                // Afficher un message d'erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors du chargement du formulaire");
                alert.setContentText("Une erreur est survenue lors du chargement du formulaire de gestion du stock. Veuillez réessayer plus tard.");
                alert.show();
            }
        } else {
            // Afficher un message d'erreur ou une notification indiquant qu'aucun stock n'a été sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun stock sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un stock à mettre à jour.");
            alert.show();
        }
    }

    @FXML
    void remove(ActionEvent event) {
        if (StockSelectionne != null) {
            ServiceStock ss = new ServiceStock();
            try {
                ss.Delete(StockSelectionne);

                // Envoyer un e-mail de notification pour informer de la suppression du stock
                try {
                    //Mailing.sendHtmlNotification("ademaloui744@gmail.com","Stock Notification",StockSelectionne.getNom(),"Deleted");

                    GMailer gMailer = new GMailer();
                    gMailer.sendMail("Stock supprimé", "Le stock " + StockSelectionne.getNom() + " a été supprimé.");
                    System.out.println("Email envoyé avec succès !");
                } catch (Exception e) {
                    System.err.println("Erreur lors de l'envoi de l'e-mail de notification : " + e.getMessage());
                }

                afficherStock();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Afficher un message d'erreur ou une notification indiquant qu'aucun stock n'a été sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun stock sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Materiel à suprimer .");
            alert.show();
        }
    }

    @FXML
    void Rechercher(ActionEvent event) {
        String NomRecherche = tf_recherche.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (NomRecherche.isEmpty()) { // Vérifier si le champ de recherche est vide
            // Afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ de recherche vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nom pour effectuer la recherche.");
            alert.show();
            return; // Sortir de la méthode car le champ de recherche est vide
        }

        try {
            ServiceStock service = new ServiceStock();
            List<Stock> Stocks = service.searchByNom(NomRecherche); // Appeler une méthode de service pour rechercher par nom

            // Effacer les éléments existants dans la TableView
            tableView.getItems().clear();

            // Trier les stocks par nom dans l'ordre alphabétique avant de les afficher dans la TableView
            Stocks.sort((stock1, stock2) -> stock1.getNom().compareToIgnoreCase(stock2.getNom()));

            // Ajouter tous les éléments de la liste à la TableView
            tableView.getItems().addAll(Stocks);
        } catch (SQLException e) {
            // Gérer les erreurs de manière appropriée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la recherche");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la recherche. Veuillez réessayer plus tard.");
            alert.show();
        }
    }

    public void generatePDF(List<Stock> StocklListe) {
        // Création d'une boîte de dialogue pour choisir l'emplacement de sauvegarde du PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));

        // Afficher la boîte de dialogue et attendre que l'utilisateur sélectionne un emplacement
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Utiliser le chemin sélectionné par l'utilisateur pour enregistrer le PDF
            String dest = selectedFile.getAbsolutePath();
            try {
                // Initialiser le document PDF
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
                Document document = new Document(pdfDoc, PageSize.A4);

                // Ajouter un titre au document
                Paragraph title = new Paragraph("Liste de Matériel");
                title.setFontSize(16).setBold();
                document.add(title);

                // Créer un tableau pour afficher les données
                Table table = new Table(4);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Nom")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Quantite")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("lieu")));

                // Remplir le tableau avec les données de la liste
                for (Stock stock :StocklListe) {
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(stock.getNom())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(stock.getQuantite()))));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(stock.getLieu()))));
                }

                // Ajouter le tableau au document
                document.add(table);

                // Fermer le document
                document.close();

                // Affichage d'une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF a été enregistré avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun emplacement sélectionné.");
        }
    }


    @FXML
    void print_PDF(ActionEvent event) {
        List<Stock> stocklListe = tableView.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(stocklListe);


    }


}



