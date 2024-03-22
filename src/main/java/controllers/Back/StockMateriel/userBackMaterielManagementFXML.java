package controllers.Back.StockMateriel;

import Animation.AnimationHelper;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Materiel;
import services.ServiceMateriel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static Animation.AnimationHelper.addButtonShadowEffect;


public class userBackMaterielManagementFXML {

    @FXML
    private TableColumn<?, ?> CategorieMat;

    @FXML
    private Button CourMan;

    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button HomeMan;

    @FXML
    private TableColumn<?, ?> NomMateriel;

    @FXML
    private Button ProductMan;

    @FXML
    private TableColumn<?, ?> QuantiteMat;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button UserMan;
    @FXML
    private TextField tf_Recherche;

    @FXML
    private TableColumn<?, ?> action;

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
    private TableColumn<?, ?> id_Stock;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableView<Materiel> tableView;
    @FXML
    private ComboBox<String> combobox;


    @FXML

    private Materiel MaterielSelectionne;


    @FXML
    void handleRowSelection(MouseEvent event) {
        if (event.getClickCount() > 0) {
            MaterielSelectionne = (Materiel) tableView.getSelectionModel().getSelectedItem();
        }
    }

    private void afficheMateriel() {
        // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView
        tableView.setOnMouseClicked(this::handleRowSelection);
        //initialiser les colonnes
        NomMateriel.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        CategorieMat.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
        QuantiteMat.setCellValueFactory(new PropertyValueFactory<>("qte"));
        id_Stock.setCellValueFactory(new PropertyValueFactory<>("id"));

        //affichage
        try {
            ServiceMateriel service = new ServiceMateriel();
            List<Materiel> CabineList = service.selectAll();

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
        assert CategorieMat != null : "fx:id=\"CategorieMat\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert HomeMan != null : "fx:id=\"HomeMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert NomMateriel != null : "fx:id=\"NomMateriel\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert QuantiteMat != null : "fx:id=\"QuantiteMat\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert action != null : "fx:id=\"action\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench111112 != null : "fx:id=\"btn_workbench111112\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench11111211 != null : "fx:id=\"btn_workbench11111211\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench1111121111 != null : "fx:id=\"btn_workbench1111121111\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert id_Stock != null : "fx:id=\"id_Stock\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert tf_Recherche != null : "fx:id=\"tf_Recherche\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        assert combobox != null : "fx:id=\"combobox\" was not injected: check your FXML file 'userBackMaterielManagement.fxml'.";
        // Ajouter l'animation de transition d'échelle à chaque bouton
        addButtonShadowEffect(CourMan);
        addButtonShadowEffect(EventMan);
        addButtonShadowEffect(GymMan);
        addButtonShadowEffect(HomeMan);
        addButtonShadowEffect(ProductMan);
        addButtonShadowEffect(ReclamMan);
        addButtonShadowEffect(StockMan);
        addButtonShadowEffect(UserMan);


        // Ajouter l'animation de transition d'opacité à chaque bouton
        Node rootPane = null;
        AnimationHelper.addFadeTransition(rootPane);



        AnimationHelper.addTableViewFadeAnimation(tableView);

        // Ajouter une animation de transition d'opacité à chaque ligne de la TableView
        AnimationHelper.addTableRowFadeAnimation(tableView);


        combobox.getItems().addAll("Nom","QuantiteMat");
        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Nom")) {
                // Appeler la méthode pour trier par quantité
                sortByNom();
            } else if (newValue.equals("QuantiteMat")) {
                sortByQuantiteMat();
            }
        });



        afficheMateriel();
    }
    private void sortByQuantiteMat() {
        try {
            ServiceMateriel service = new ServiceMateriel();
            List<Materiel> materiels = service.selectAll();

            // Trier les éléments par quantité de matériel de manière ascendante
            materiels.sort(Comparator.comparingInt(Materiel::getQte));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<Materiel> sortedList = FXCollections.observableArrayList(materiels);
            tableView.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
    private void sortByNom() {
        try {
            ServiceMateriel service = new ServiceMateriel();
            List<Materiel> materiels = service.selectAll();

            // Trier les éléments par nom
            materiels.sort(Comparator.comparing(Materiel::getNom));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<Materiel> sortedList = FXCollections.observableArrayList(materiels);
            tableView.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
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
    void AddMateriel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackMaterielManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent
            // Get the controller after the FXML file has been loaded
            userBackMaterielManagementForm controller = loader.getController();


            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void UpadateMateriel(ActionEvent event) {
        if (MaterielSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/StockMateriel/userBackMaterielManagementForm.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                // Obtenir le contrôleur du formulaire de gestion du stock
                userBackMaterielManagementForm controller = loader.getController();

                // Passer le stock sélectionné au contrôleur du formulaire de gestion du stock
                controller.setMateriel(MaterielSelectionne);

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
            alert.setTitle("Aucun Materiel sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Materiel à mettre à jour.");
            alert.show();
        }


    }

    @FXML
    void RemoveMateriel(ActionEvent event) {
        if (MaterielSelectionne != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce matériel ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ServiceMateriel ss = new ServiceMateriel();
                try {
                    ss.Delete(MaterielSelectionne);
                    afficheMateriel();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            // Afficher un message d'erreur ou une notification indiquant qu'aucun stock n'a été sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun Materiel sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Materiel à supprimer.");
            alert.show();
        }
    }

    @FXML
    void Recherche(ActionEvent event) {
        String nomRecherche = tf_Recherche.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                ServiceMateriel service = new ServiceMateriel();
                List<Materiel> materiels = service.selectAll(); // Récupérer tous les éléments de la base de données

                // Filtrer les éléments dont le nom commence par la première lettre saisie
                List<Materiel> filteredMateriels = materiels.stream()
                        .filter(materiel -> materiel.getNom().toLowerCase().startsWith(nomRecherche.toLowerCase()))
                        .collect(Collectors.toList());

                // Effacer les éléments existants dans le TableView
                tableView.getItems().clear();

                // Ajouter les éléments filtrés à la TableView
                tableView.getItems().addAll(filteredMateriels);
            } catch (SQLException e) {
                // Gérer les erreurs de manière appropriée
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la recherche");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de la recherche. Veuillez réessayer plus tard.");
                alert.show();
            }
        } else {
            // Si le champ de recherche est vide, afficher un message à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ de recherche vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nom pour effectuer la recherche.");
            alert.show();
        }
    }
    public void generatePDF(List<Materiel> MaterielListe) {
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
                table.addCell(new Cell().add(new Paragraph("Nom")));
                table.addCell(new Cell().add(new Paragraph("Catégorie")));
                table.addCell(new Cell().add(new Paragraph("Quantité")));
                table.addCell(new Cell().add(new Paragraph("ID Stock")));

                // Remplir le tableau avec les données de la liste
                for (Materiel materiel : MaterielListe) {
                    table.addCell(new Cell().add(new Paragraph(materiel.getNom())));
                    table.addCell(new Cell().add(new Paragraph(materiel.getCategorie())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(materiel.getQte()))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(materiel.getId_stock()))));
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

    // Vos autres méthodes ici...



    @FXML
    void print_PDF(ActionEvent event) {
        // Récupérer la liste des cours à partir de votre TableView ou de toute autre source de données
        List<Materiel> materielListe = tableView.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(materielListe);
    }

    private void printPDFDocument() {
        try (PdfWriter writer = new PdfWriter("Maker.pdf");
             PdfDocument pdfDoc = new PdfDocument(writer)) {

            Document document = new Document(pdfDoc);

            float[] columnWidths = {150f, 150f, 100f, 100f};
            Table pdfTable = new Table(columnWidths);

            // Ajouter un titre au document
            Paragraph title = new Paragraph("Liste de Matériel")
                    .setFontSize(16)
                    .setBold();
            document.add(title);

            // Ajouter les en-têtes de colonne à la table
            pdfTable.addCell(new Cell().add(new Paragraph("Nom")));
            pdfTable.addCell(new Cell().add(new Paragraph("Catégorie")));
            pdfTable.addCell(new Cell().add(new Paragraph("Quantité")));
            pdfTable.addCell(new Cell().add(new Paragraph("ID Stock")));

            // Obtenir les données de la TableView
            ObservableList<Materiel> materiels = tableView.getItems();

            // Ajouter les données à la table PDF
            for (Materiel materiel : materiels) {
                pdfTable.addCell(new Cell().add(new Paragraph(materiel.getNom())));
                pdfTable.addCell(new Cell().add(new Paragraph(materiel.getCategorie())));
                pdfTable.addCell(new Cell().add(new Paragraph(String.valueOf(materiel.getQte()))));
                pdfTable.addCell(new Cell().add(new Paragraph(String.valueOf(materiel.getId_stock()))));
            }

            // Ajouter la table PDF au document
            document.add(pdfTable);

        } catch (IOException e) {
            e.printStackTrace();
            // Gérer d'autres erreurs d'entrée-sortie
        } catch (Exception ex) {
            ex.printStackTrace();
            // Gérer d'autres exceptions
        }
    }}








// Méthode utilitaire pour afficher une boîte de dial








