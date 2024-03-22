package controllers.Back.CoursProgram;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.cours;
import services.servicesCours;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class userBackCoursManagement {
    @FXML
    private Button pdfBtn;
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
    private Button tfupdate;

    @FXML
    private ComboBox<String> tfFiltrer;
    @FXML
    private Button tfPdf;
    @FXML
    private Button EventMan;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111111;

    @FXML
    private Button GymMan;
    @FXML
    private TableColumn<cours, String> colModif;
    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;
    @FXML
    private TextField searchField;

    @FXML
    private TableView<cours> tabCours;

    @FXML
    private TableColumn<?, ?> colNom;
    @FXML
    private Button tfSearch;
    @FXML
    private TableColumn<?, ?> colCoach;

    @FXML
    private TableColumn<?, ?> colJours;

    @FXML
    private TableColumn<?, ?> Colduree;

    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableColumn<?, ?> id_progr;
    @FXML
    private TableColumn<?, ?> colPrix;

    @FXML
    private Button add;


    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Button tfsupprimer;

    @FXML
    private Button btn_workbench111112111;

    @FXML
    private Button btn_workbench13;

    @FXML
    private Button tfProg;

    @FXML
    private Button CourMan;
    @FXML
    ObservableList<cours> courlist = FXCollections.observableArrayList();
    private cours coursSelectionne;


    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert tabCours != null : "fx:id=\"tabCours\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert colNom != null : "fx:id=\"colNom\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert colCoach != null : "fx:id=\"colCoach\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert colJours != null : "fx:id=\"colJours\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert Colduree != null : "fx:id=\"Colduree\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert colType != null : "fx:id=\"colType\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert colPrix != null : "fx:id=\"colPrix\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert tfupdate != null : "fx:id=\"tfupdate\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert tfsupprimer != null : "fx:id=\"tfsupprimer\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert colModif != null : "fx:id=\"colModif\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert tfProg != null : "fx:id=\"tfProg\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert id_progr != null : "fx:id=\"id_progr\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert tfPdf != null : "fx:id=\"tfPdf\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        // ObservableList<String> daysOfWeek = FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        // tfFiltrer.setItems(daysOfWeek);
        AfficherCours();


        tfFiltrer.getItems().addAll("type", "Nom");
        tfFiltrer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("type")) {
                // Appeler la méthode pour trier par quantité
                trierParType();
            } else if (newValue.equals("Nom")) {
                trierParNom();
            }
        });


// Méthode pour trier par nom


    }

    private void trierParType() {
        tabCours.getItems().sort(Comparator.comparing(cours::getType));

    }

    private void trierParNom() {
        tabCours.getItems().sort(Comparator.comparing(cours::getNom));

    }

    @FXML
    public void ajouterClick(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackCoursManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = Home.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void openProgram(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackProgramManagement.fxml"));
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
    void updateClick(ActionEvent event) {
        if (coursSelectionne != null) {
            try {
                // Load the form page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackCoursManagementForm.fxml"));
                Parent parent = loader.load(); // Load the interface into the parent

                // Get the controller after the FXML file has been loaded
                userBackCoursManagementForm controller = loader.getController();

                // Set the selected course to update in the form
                controller.setTabCoursToUpdate(coursSelectionne);

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


    public void openGymManagement(ActionEvent actionEvent) {
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

    public void openProductManagement(ActionEvent actionEvent) {
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

    void openCourManagement(ActionEvent event) {

    }

    public void openReclamationManagement(ActionEvent actionEvent) {

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

    public void openUserBackHome(ActionEvent actionEvent) {
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

    public void openUserManagement(ActionEvent actionEvent) {
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

    public void openStockManagement(ActionEvent actionEvent) {
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

    public void openEventManagement(ActionEvent actionEvent) {
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


    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    private void handleRowSelection(javafx.scene.input.MouseEvent mouseEvent) {

        // Vérifie si un clic de souris a été effectué


        if (mouseEvent.getClickCount() > 0) {
            // Récupérer la ligne sélectionnée dans le TableView
            coursSelectionne = tabCours.getSelectionModel().getSelectedItem();

        }
    }

    public void AfficherCours() {
        // Lier la méthode handleRowSelection cours de sélection de ligne dans le TableView
        tabCours.setOnMouseClicked(this::handleRowSelection);

        // Initialiser les colonnes
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCoach.setCellValueFactory(new PropertyValueFactory<>("coach"));
        colJours.setCellValueFactory(new PropertyValueFactory<>("jours"));
        Colduree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        id_progr.setCellValueFactory(new PropertyValueFactory<>("id_programme"));


        //affichage
        try {
            servicesCours service = new servicesCours();
            List<cours> cours = service.selectAll();

            // Clear existing items in the TableView
            tabCours.getItems().clear();


            // Ajouter les données chargées au TableView
            tabCours.getItems().addAll(cours);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors du chargement des données");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors du chargement des données. Veuillez réessayer plus tard.");
            alert.show();

        }

        //add cell of button edit
        Callback<TableColumn<cours, String>, TableCell<cours, String>> cellFactory = (TableColumn<cours, String> param) -> {
            final TableCell<cours, String> cell = new TableCell<cours, String>() {
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
                            cours coursToDelete = getTableView().getItems().get(getIndex());
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("Deletion confirmation");
                            confirmation.setHeaderText("\n" + "Are you sure you want to delete this course ?");
                            confirmation.setContentText("Cette action est irréversible.");
                            Optional<ButtonType> result = confirmation.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    servicesCours service = new servicesCours();
                                    service.deleteOne(coursToDelete);
                                    getTableView().getItems().remove(coursToDelete);
                                } catch (SQLException e) {
                                    afficherErreur("Erreur de suppression", "Une erreur s'est produite lors de la suppression de cours.");
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
        colModif.setCellFactory(cellFactory);

    }



    @FXML
    public void FiltrerClick() {


    tabCours.getItems().sort(Comparator.comparing(cours::getNom));
    tabCours.getItems().sort(Comparator.comparing(cours::getType));

    }

    @FXML
    public void serachClick(ActionEvent actionEvent) {


        String CoursRecherche = searchField.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (CoursRecherche.isEmpty()) { // Vérifier si le champ de recherche est vide
            // Afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ de recherche vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une quantité pour effectuer la recherche.");
            alert.show();
            return; // Sortir de la méthode car le champ de recherche est vide
        }

        try {
            String titre = CoursRecherche;
           servicesCours service = new servicesCours();
            List<cours> cours= service.searchByNom(titre); // Appeler une méthode de service pour rechercher par quantité

            // Effacer les éléments existants dans le TableView
            tabCours.getItems().clear();

            // Ajouter tous les éléments de la liste à la TableView
           tabCours.getItems().addAll(cours);
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

    public void generatePDF(List<cours> courslListe) {
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
                Paragraph title = new Paragraph("Liste des cours ");
                title.setFontSize(16).setBold();
                document.add(title);

                // Créer un tableau pour afficher les données
                Table table = new Table(6);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nom")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("coach")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("jours")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("dureé")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("type")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("prix")));





                // Remplir le tableau avec les données de la liste
                for (cours Cours :courslListe) {
                    table.addCell(new Cell().add(new Paragraph(Cours.getNom())));
                    table.addCell(new Cell().add(new Paragraph(Cours.getCoach())));
                    table.addCell(new Cell().add(new Paragraph(Cours.getJours())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(Cours.getDuree()))));
                    table.addCell(new Cell().add(new Paragraph(Cours.getType())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(Cours.getPrix()))));





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


    // Gestionnaire d'événements pour le clic sur le bouton de génération de PDF
    @FXML
    void handleGeneratePDF(ActionEvent event) {
        // Récupérer la liste des cours à partir de votre TableView ou de toute autre source de données
        List<cours> salleListe = tabCours.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(salleListe);



}}


