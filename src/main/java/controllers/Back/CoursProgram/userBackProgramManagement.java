package controllers.Back.CoursProgram;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.programme;
import services.servicesProgramme;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class userBackProgramManagement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Button Home;

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
    private Button btn_workbench111111;

    @FXML
    private Button GymMan;

    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;

    @FXML
    private TableView<programme> tabProg;

    @FXML
    private TableColumn<programme, String> colNom;

    @FXML
    private TableColumn<programme, String> ColDescrip;

    @FXML
    private TableColumn<programme, String> ColDuree;

    @FXML
    private TableColumn<programme, String> colAction;

    @FXML
    private Button tfback;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Button btn_workbench11111211;

    @FXML
    private Button btn_workbench111112111;

    @FXML
    private Button btn_workbench13;

    @FXML
    private TextField searchTextField;
    @FXML
    private Button tfupdate;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private Button searchButton;

    @FXML
    private Label courNameLabel;

    @FXML
    private TextField rechercheFiled;

    @FXML
    private Button tfrecherche;
    @FXML
    private ComboBox<String> tfFiltre;


    models.programme programmeSelectionne;
    private TableCell<programme, String> cell;


    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert tabProg != null : "fx:id=\"tabProg\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert colNom != null : "fx:id=\"colNom\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert ColDescrip != null : "fx:id=\"ColDescrip\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert ColDuree != null : "fx:id=\"ColDuree\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert colAction != null : "fx:id=\"colAction\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert tfback != null : "fx:id=\"tfback\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert btn_workbench11111211 != null : "fx:id=\"btn_workbench11111211\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert tfupdate != null : "fx:id=\"tfupdate\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";
        assert tfFiltre != null : "fx:id=\"tfFiltre\" was not injected: check your FXML file 'userBackProgramManagement.fxml'.";

        AfficherProgramme();

        tfFiltre.getItems().addAll("duree", "Nom");
        tfFiltre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("duree")) {
                // Appeler la méthode pour trier par durée
                trierParDuree();
            } else if (newValue.equals("Nom")) {
                trierParNom();
            }
        });

       
    }

    private void trierParNom() {
        tabProg.getItems().sort(Comparator.comparing(programme::getNom));

    }

    private void trierParDuree() {
      tabProg.getItems().sort(Comparator.comparing(programme::getDuree));
        
    }

    public void openUserBackHome(javafx.event.ActionEvent actionEvent) {

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

    public void openUserManagement(javafx.event.ActionEvent actionEvent) {

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

    public void openStockManagement(javafx.event.ActionEvent actionEvent) {

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

    public void openEventManagement(javafx.event.ActionEvent actionEvent) {

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

    public void openGymManagement(javafx.event.ActionEvent actionEvent) {
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

    public void openProductManagement(javafx.event.ActionEvent actionEvent) {
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

    public void openReclamationManagement(javafx.event.ActionEvent actionEvent) {
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

    public void openBack(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackProgramManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = Home.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }


    public void handleRowSelection(MouseEvent mouseEvent) {
        // Vérifie si un clic de souris a été effectué


        if (mouseEvent.getClickCount() > 0) {
            // Récupérer la ligne sélectionnée dans le TableView
           programmeSelectionne = tabProg.getSelectionModel().getSelectedItem();

        }
    }

    public void AfficherProgramme() {
        // Lier la méthode handleRowSelection au programme de sélection de ligne dans le TableView
        tabProg.setOnMouseClicked(this::handleRowSelection);

        // Initialiser les colonnes
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ColDescrip.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ColDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));


        //affichage
        try {
           servicesProgramme service = new servicesProgramme();
            List<programme> programmes = service.selectAll();

            // Clear existing items in the TableView
         tabProg.getItems().clear();


            // Ajouter les données chargées au TableView
           tabProg.getItems().addAll(programmes);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading data");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while loading data. please try again later.");
            alert.show();

        }

        //add cell of button edit
        Callback<TableColumn<programme, String>, TableCell<programme, String>> cellFactory = (TableColumn<programme, String> param) -> {
            final TableCell<programme ,String> cell = new TableCell<programme, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setStyle("-fx-cursor: hand; -glyph-size: 28px; -fx-fill: #ff1744;");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            programme programmeToDelete = getTableView().getItems().get(getIndex());
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("\n" + "Deletion confirmation");
                            confirmation.setHeaderText("Are you sure you want to remove this program ?");
                            confirmation.setContentText("Cette action est irréversible.");
                            Optional<ButtonType> result = confirmation.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    servicesProgramme service = new servicesProgramme();
                                    service.deleteOneP(programmeToDelete);
                                    getTableView().getItems().remove(programmeToDelete);
                                } catch (SQLException e) {
                                    afficherErreur("Deletion error", "An error occurred while deleting the program.");
                                }
                            }
                        });
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
       colAction.setCellFactory(cellFactory);

    }

    public void updateClick2(ActionEvent actionEvent) {

        if (programmeSelectionne != null) {
            try {
                // Load the form page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackProgramManagementForm.fxml"));
                Parent parent = loader.load(); // Load the interface into the parent

                // Get the controller after the FXML file has been loaded
                userBackProgramManagementForm controller = loader.getController();

                // Set the selected course to update in the form
                controller.setTabprogrammeToUpdate( programmeSelectionne);

                // Get the current scene
                Scene scene = CourMan.getScene();
                // Replace the content of the current scene with the content of the new interface
                scene.setRoot(parent);
            } catch (IOException e) {
                // Print the error to the console or a log file
                e.printStackTrace();

                // Show an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error loading the form");
                alert.setContentText("An error occurred while loading the course management form. Please try again later.");
                alert.show();
            }
        } else {
            // Show a warning message indicating that no course has been selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No course selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to update.");
            alert.show();
        }
    }

    public void Recherche(ActionEvent actionEvent) {

        String programmeRecherche = rechercheFiled.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (programmeRecherche.isEmpty()) { // Vérifier si le champ de recherche est vide
            // Afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ de recherche vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une quantité pour effectuer la recherche.");
            alert.show();
            return; // Sortir de la méthode car le champ de recherche est vide
        }

        try {
            String titre = programmeRecherche;
       servicesProgramme service = new servicesProgramme();
            List<programme> programme= service.searchByTitre(titre); // Appeler une méthode de service pour rechercher par quantité

            // Effacer les éléments existants dans le TableView
            tabProg.getItems().clear();

            // Ajouter tous les éléments de la liste à la TableView
        tabProg.getItems().addAll(programme);
        } catch (NumberFormatException e) {
            // Gérer les erreurs de format de quantité
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format de quantité incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une quantité valide.");
            alert.show();
        } catch (SQLException e) {
            // Gérer les erreurs de manière appropriée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la recherche");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la recherche. Veuillez réessayer plus tard.");
            alert.show();
        }

    }



    public void filtrerProg(ActionEvent actionEvent) {

        tabProg.getItems().sort(Comparator.comparing(programme::getNom));
       tabProg.getItems().sort(Comparator.comparing(programme::getDuree));
    }
    public void generatePDF(List<programme> programmeListe) {
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
                Paragraph title = new Paragraph("Liste des programmes ");
                title.setFontSize(16).setBold();
                document.add(title);

                // Créer un tableau pour afficher les données
                Table table = new Table(6);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nom")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("description")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("durée")));






                // Remplir le tableau avec les données de la liste
                for (programme prog :programmeListe) {
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(prog.getNom())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(prog.getDescription())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(prog.getDuree()))));






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
    public void handleGeneratePDF(ActionEvent actionEvent) {
        // Récupérer la liste des cours à partir de votre TableView ou de toute autre source de données
        List<programme> programmeListe = tabProg.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(programmeListe);
    }

}









