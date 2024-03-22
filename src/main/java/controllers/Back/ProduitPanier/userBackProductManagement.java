package controllers.Back.ProduitPanier;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Produit;
import services.Mailing;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class userBackProductManagement {


    @FXML
    private Button Home;
    @FXML
    private TableColumn<?, ?> CategorieCo;

    @FXML
    private Button CourMan;

    @FXML
    private TableColumn<?, ?> DescriptionCo;

    @FXML
    private Button GymMan;

    @FXML
    private Button LogOut;

    @FXML
    private TableColumn<?, ?> NomCo;

    @FXML
    private TableColumn<?, ?> PrixCo;

    @FXML
    private TableColumn<Produit, String> QtCo;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMn;

    @FXML
    private Button UserMan;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111112;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button printButton;

    @FXML
    private Button btn_workbench11211;

    @FXML
    private Button btn_workbench13;

    @FXML
    private Button event;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableView<Produit> tableView;
    private Produit selectedProduit;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchField;


    public void displayAllProductsInTableView() {
        tableView.setOnMouseClicked(this::handleTableViewClick);
        NomCo.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrixCo.setCellValueFactory(new PropertyValueFactory<>("prix"));
        QtCo.setCellValueFactory(cellData -> {
            Produit produit = cellData.getValue();
            SimpleStringProperty qtProperty;

            if (produit.getQte() > 0) {
                qtProperty = new SimpleStringProperty("In Stock ("+produit.getQte()+")");
            } else {
                qtProperty = new SimpleStringProperty("Out of Stock");
            }
            return qtProperty;
        });
        DescriptionCo.setCellValueFactory(new PropertyValueFactory<>("description"));
        CategorieCo.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        try {
            ServiceProduit serviceProduit = new ServiceProduit();
            List<Produit> produits = serviceProduit.selectAll();
            ObservableList<Produit> produitObservableList = FXCollections.observableArrayList(produits);
            tableView.setItems(produitObservableList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }
    }

    private void handleTableViewClick(MouseEvent mouseEvent) {
        Produit selectedProduit = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            this.selectedProduit = selectedProduit;

            setTableActionButtons(false);
        }
    }

    @FXML
    void searchProduct(ActionEvent event) {

        String filterBy = filterComboBox.getValue();
        String search = searchField.getText();

        try {
            ServiceProduit serviceProduit = new ServiceProduit();
            List<Produit> produits;
            if(search.isEmpty()){
                produits = serviceProduit.selectAll();
            }else
                produits = serviceProduit.searchBy(filterBy,search);
            ObservableList<Produit> produitObservableList = FXCollections.observableArrayList(produits);
            tableView.setItems(produitObservableList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }
    }

    @FXML
    void addProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProduitPanier/userBackProduitManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = Home.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void removeProduct(ActionEvent event) {
        if(selectedProduit != null)
        {
            ServiceProduit sp = new ServiceProduit();
            try {
                sp.deleteOne(selectedProduit);
                Mailing.sendHtmlNotification("ademaloui744@gmail.com","Product Notification",selectedProduit.getNom(),"Deleted");
                displayAllProductsInTableView();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void updateProduct(ActionEvent event) {
        if(selectedProduit != null)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProduitPanier/userBackProduitManagementForm.fxml"));
                Parent parent = loader.load();

                userBackProductManagementForm controller = loader.getController();

                controller.setProduct(selectedProduit);

                Scene scene = Home.getScene();
                scene.setRoot(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void openUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackUserManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = Home.getScene();
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
            Scene scene = Home.getScene();
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
            Scene scene = Home.getScene();
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
            Scene scene = Home.getScene();
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
            Scene scene = Home.getScene();
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
            Scene scene = Home.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void openUserBackHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = Home.getScene();
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
            Scene scene = Home.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert CategorieCo != null : "fx:id=\"CategorieCo\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert DescriptionCo != null : "fx:id=\"DescriptionCo\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert NomCo != null : "fx:id=\"NomCo\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert PrixCo != null : "fx:id=\"PrixCo\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert QtCo != null : "fx:id=\"QtCo\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert StockMn != null : "fx:id=\"StockMn\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert btn_workbench111112 != null : "fx:id=\"btn_workbench111112\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert printButton != null : "fx:id=\"printButton\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert btn_workbench11211 != null : "fx:id=\"btn_workbench11211\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert event != null : "fx:id=\"event\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'userBackProductManagement.fxml'.";
        displayAllProductsInTableView();

        setTableActionButtons(true);

    }

    private void setTableActionButtons(boolean state) {

        updateButton.setDisable(state);
        removeButton.setDisable(state);
        printButton.setDisable(state);
    }

    public void generatePDF(List<Produit> produitlist) {
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
                Paragraph title = new Paragraph("Liste des produits ");
                title.setFontSize(16).setBold();
                document.add(title);

                // Créer un tableau pour afficher les données
                Table table = new Table(5);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nom")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("prix")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("qte")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("description")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("categorie")));



                // Remplir le tableau avec les données de la liste
                for (Produit product :produitlist) {
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(product.getNom())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(product.getPrix()))));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(product.getQte()))));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(product.getDescription())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(product.getCategorie())));



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
    void print(ActionEvent event) {
        // Récupérer la liste des cours à partir de votre TableView ou de toute autre source de données
        List<Produit> produitListe = tableView.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(produitListe);

    }


}
