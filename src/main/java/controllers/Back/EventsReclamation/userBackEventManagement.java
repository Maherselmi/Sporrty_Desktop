package controllers.Back.EventsReclamation;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
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
import models.evenements;
import services.Mailing;
import services.ServiceEvenement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class userBackEventManagement {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button CourMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button Home;

    @FXML
    private Button LogOut;

    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;
    @FXML
    private Button EventMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button btn_workbench1111;


    @FXML
    private Button btn_workbench111112111;

    @FXML
    private Button btn_workbench112;

    @FXML
    private Button btn_workbench13;
    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableColumn<evenements, String> ColNom;

    @FXML
    private TableColumn<evenements, String> colCateg;

    @FXML
    private TableColumn<evenements, LocalDate> colDate;

    @FXML
    private TableColumn<evenements, String> colDescr;

    @FXML
    private TableColumn<evenements, String> colLieu;

    @FXML
    private TableColumn<evenements, String> colModif;

    @FXML
    private TableColumn<evenements, Integer> colNbrP;


    @FXML
    private TableView<evenements> tbEvents;

    @FXML
    private Button tfAdd;
    @FXML
    private Button tfUpd;
    @FXML
    private TextField tf_recherche;
    @FXML
    private ComboBox<String> comboTri;
    @FXML
    private Button tfPart;

    private evenements eventSelectionne;

    @FXML
    public void addClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackEventManagementForm.fxml"));
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
    void updateClick(ActionEvent event) {
        if (eventSelectionne != null) {
            try {
                // Ouvrir la page du formulaire
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackEventManagementForm.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                // Get the controller after the FXML file has been loaded
                userBackEventManagementForm controller = loader.getController();

                controller.setEvenementToUpdate(eventSelectionne);

                // Récupérer la scène actuelle
                Scene scene = EventMan.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                // throw new RuntimeException(e);
                // Afficher l'erreur dans la console ou un fichier journal
                e.printStackTrace();

                // Afficher un message d'erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Error loading form");
                alert.setContentText(
                        "An error occurred while loading the inventory management form. Please try again later");
                alert.show();
            }
        } else {
            // Afficher un message d'erreur ou une notification indiquant qu'aucun evenement n'a été sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Event selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event to update.");
            alert.show();
        }
    }


    public void handleRowSelection(MouseEvent mouseEvent) {
        // Vérifie si un clic de souris a été effectué
        if (mouseEvent.getClickCount() > 0) {
            // Récupérer la ligne sélectionnée dans le TableView
            eventSelectionne = tbEvents.getSelectionModel().getSelectedItem();

        }
    }

    private void afficherEvent() {
        // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView
        tbEvents.setOnMouseClicked(this::handleRowSelection);
        //initialiser les colonnes

        ColNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCateg.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescr.setCellValueFactory(new PropertyValueFactory<>("descri"));
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colNbrP.setCellValueFactory(new PropertyValueFactory<>("nbrP"));
        //affichage
        try {
            ServiceEvenement service = new ServiceEvenement();
            List<evenements> events = service.selectAll();

            // Clear existing items in the TableView
            tbEvents.getItems().clear();


            // Ajouter les données chargées au TableView
            tbEvents.getItems().addAll(events);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading data");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while loading data. Please try again later.");
            alert.show();

        }

        //add cell of button edit
        Callback<TableColumn<evenements, String>, TableCell<evenements, String>> cellFoctory = (TableColumn<evenements, String> param) -> {
            // make cell containing buttons
            final TableCell<evenements, String> cell = new TableCell<evenements, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);


                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            evenements eventToDelete = getTableView().getItems().get(getIndex());

                            // Show a confirmation dialog
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("Confrim Remove");
                            confirmation.setHeaderText("Are you sure you want to delete this event?");
                            confirmation.setContentText("This action is irreversible.");

                            Optional<ButtonType> result = confirmation.showAndWait();

                            // Check if the user clicked OK button
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    // Delete the event from the database
                                    ServiceEvenement service = new ServiceEvenement();
                                    service.deleteOne(eventToDelete);
                                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Event Notification",eventToDelete.getNom(),"Deleted");
                                    // Remove the event from the TableView
                                    getTableView().getItems().remove(eventToDelete);
                                } catch (SQLException e) {
                                    // Show an error message if there's an SQL exception
                                    afficherErreur("Remove Error", "An error occurred while deleting the event.");
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
        colModif.setCellFactory(cellFoctory);

    }

    @FXML
    void Rechercher(ActionEvent event) {
        String eventRecherche = tf_recherche.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (eventRecherche.isEmpty()) { // Vérifier si le champ de recherche est vide
            // Afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty search field");
            alert.setHeaderText(null);
            alert.setContentText("Please enter an event to search.");
            alert.show();
            return; // Sortir de la méthode car le champ de recherche est vide
        }

        try {
            String titre = eventRecherche;
            ServiceEvenement service = new ServiceEvenement();
            List<evenements> Stocks = service.searchByTitre(titre); // Appeler une méthode de service pour rechercher par quantité

            // Effacer les éléments existants dans le TableView
            tbEvents.getItems().clear();

            // Ajouter tous les éléments de la liste à la TableView
            tbEvents.getItems().addAll(Stocks);
        } catch (NumberFormatException e) {
            // Gérer les erreurs de format de quantité
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Event");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid quantity.");
            alert.show();
        } catch (SQLException e) {
            // Gérer les erreurs de manière appropriée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la recherche");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred during the search. Please try again later.");
            alert.show();
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
    private void openBackHome() {
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
    // Méthode pour trier les événements en fonction du critère sélectionné
    @FXML
    private void onRoleSelectionChanged(ActionEvent event) {
        String selectedRole = comboTri.getValue();
        if (selectedRole != null) {
            try {
                ServiceEvenement se = new ServiceEvenement();
                List<evenements> ev = se.selectAllOrderedByDate();
                ObservableList<evenements> userList = FXCollections.observableArrayList(ev);
                tbEvents.setItems(userList);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
            }
        }
    }
    private void trierParnbrP() {
        // Trier les éléments dans la TableView par quantité (ordre descendant)
        tbEvents.getItems().sort((ev1, ev2) -> {
            if (ev1.getNbrP() > ev2.getNbrP()) {
                return -1;
            } else if (ev1.getNbrP() < ev2.getNbrP()) {
                return 1;
            } else {
                return 0;
            }
        });
    }
    private void trierParTitre() {
        // Trier les éléments dans la TableView par quantité (ordre descendant)
        tbEvents.getItems().sort((ev1, ev2) -> ev1.getNom().compareToIgnoreCase(ev2.getNom()));
    }
    @FXML
    void initialize() {
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";


        assert ColNom != null : "fx:id=\"ColNom\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert colCateg != null : "fx:id=\"colCateg\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert colDescr != null : "fx:id=\"colDescr\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert colLieu != null : "fx:id=\"colLieu\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert colModif != null : "fx:id=\"colModif\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert colNbrP != null : "fx:id=\"colNbrP\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert tbEvents != null : "fx:id=\"tbEvents\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert tfAdd != null : "fx:id=\"tfAdd\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";
        assert tfUpd != null : "fx:id=\"tfUpd\" was not injected: check your FXML file 'userBackEventManagement.fxml'.";

        comboTri.getItems().addAll("Date (décroissant)", "Date (croissant)", "Titre (A-Z)", "Titre (Z-A)", "Catégorie (A-Z)", "Catégorie (Z-A)", "Date (plus récent)", "Date (plus ancien)","nbrP");


        comboTri.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals("nbrP")) {
                        // Appeler la méthode pour trier par quantité
                        trierParnbrP();
                    }
             else if (newValue.equals("Titre (A-Z)")) {
                // Appeler la méthode pour trier par quantité
                trierParTitre();
            }
                });
        afficherEvent();


}
    public void generatePDF(List<evenements> eventsliste) {
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
                Paragraph title = new Paragraph("Liste des salles de sport ");
                title.setFontSize(16).setBold();
                document.add(title);

                // Créer un tableau pour afficher les données
                Table table = new Table(6);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nom")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("description")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("categorie")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("date")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("lieu")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nombre_participant")));

                // Remplir le tableau avec les données de la liste
                for (evenements event :eventsliste) {
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(event.getNom())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(event.getDescri())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(event.getCategorie())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(event.getDate()))));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(event.getLieu())));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(event.getNbrP()))));




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
        List<evenements> envents = tbEvents.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(envents);

    }


    public void openParticipation(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackParticipationManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = tfPart.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
