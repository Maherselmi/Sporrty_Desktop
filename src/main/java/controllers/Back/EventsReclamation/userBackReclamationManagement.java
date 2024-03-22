package controllers.Back.EventsReclamation;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
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
import models.User;
import models.reclamation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.Mailing;
import services.ServiceReclamation;
import services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



public class userBackReclamationManagement {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button CourMan;

    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button Home;
    @FXML
    private Button RecMan;

    @FXML
    private Button LogOut;

    @FXML
    private Button ProductMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button btn_workbench1111;


    @FXML
    private Button btn_workbench13;
    @FXML
    private TableColumn<reclamation, String> colAnswerReclamation;

    @FXML
    private TableColumn<reclamation, LocalDate> colDateReclamation;

    @FXML
    private TableColumn<reclamation, String> colDescReclamation;

    @FXML
    private TableColumn<reclamation, String> colModifReclamation;

    @FXML
    private TableColumn<reclamation, Integer> colNbrEtRecalamtion;

    @FXML
    private TableColumn<reclamation, String> colStatusReclamation;

    @FXML
    private TableColumn<reclamation, String> colTypeReclamation;

    @FXML
    private TableColumn<reclamation, String> colUserReclamation;

    @FXML
    private Button modif;
    @FXML
    private Button print;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableView<reclamation> tabView;
    private reclamation recSelectionne;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private TextField searchField;

    @FXML
    void updateClick(ActionEvent event) {
        if (recSelectionne != null) {
            try {
                // Ouvrir la page du formulaire
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackReclamationManagementForm.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                // Get the controller after the FXML file has been loaded
                userBackReclamationManagementForm controller = loader.getController();

                controller.setRecToUpdate(recSelectionne);

                // Récupérer la scène actuelle
                Scene scene = RecMan.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                // throw new RuntimeException(e);
                // Afficher l'erreur dans la console ou un fichier journal
                e.printStackTrace();

                // Afficher un message d'erreur à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error loading form");
                alert.setContentText("An error occurred while loading the inventory management form. Please try again later.");
                alert.show();
            }
        } else {
            // Afficher un message d'erreur ou une notification indiquant qu'aucun evenement n'a été sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No event selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event to update.");
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

    public void handleRowSelection(MouseEvent mouseEvent) {
        // Vérifie si un clic de souris a été effectué
        if (mouseEvent.getClickCount() > 0) {
            // Récupérer la ligne sélectionnée dans le TableView
            recSelectionne = tabView.getSelectionModel().getSelectedItem();

        }
    }

    private void afficherRec() {

        // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView

        //affichage
        try {
            ServiceReclamation service = new ServiceReclamation();
            List<reclamation> rec = service.selectAll();

            // Clear existing items in the TableView
            tabView.getItems().clear();

            // Ajouter les données chargées au TableView
            tabView.getItems().addAll(rec);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading data");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while loading data. Please try again later.");
            alert.show();
        }

        //add cell of button edit
        Callback<TableColumn<reclamation, String>, TableCell<reclamation, String>> cellFoctory = (TableColumn<reclamation, String> param) -> {
            // make cell containing buttons
            final TableCell<reclamation, String> cell = new TableCell<reclamation, String>() {
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
                            reclamation eventToDelete = getTableView().getItems().get(getIndex());

                            // Show a confirmation dialog
                            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmation.setTitle("Confirmation de suppression");
                            confirmation.setHeaderText("Êtes-vous sûr de vouloir supprimer cet reclamation ?");
                            confirmation.setContentText("Cette action est irréversible.");

                            Optional<ButtonType> result = confirmation.showAndWait();

                            // Check if the user clicked OK button
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    // Delete the event from the database
                                    ServiceReclamation service = new ServiceReclamation();
                                    service.deleteOne(eventToDelete);
                                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Reclamation Notification",eventToDelete.getNomComplet(),"Deleted");

                                    // Remove the event from the TableView
                                    getTableView().getItems().remove(eventToDelete);
                                } catch (SQLException e) {
                                    // Show an error message if there's an SQL exception
                                    afficherErreur("Erreur de suppression", "Une erreur s'est produite lors de la suppression de l'événement.");
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
        colModifReclamation.setCellFactory(cellFoctory);
    }

    @FXML
    private void openUserManagement() {
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
    private void openProductManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProduitPanier/userBackProductManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = Home.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void generatePDF(List<reclamation> reclamations) {
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
                // Création d'un nouveau document PDF
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Définition des propriétés de style
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 20;
                float tableRowHeight = 15;

                // Ajout du titre du document
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.setNonStrokingColor(java.awt.Color.BLACK);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("List of Reclamation/Feedback");
                contentStream.endText();
                yPosition -= 20;

                // Ajout de la date actuelle
                Date date = new Date();
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setNonStrokingColor(java.awt.Color.BLACK);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Date: " + date.toString());
                contentStream.endText();
                yPosition -= 20;

                // Dessin des lignes du tableau
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.moveTo(margin, yPosition - 20);
                contentStream.lineTo(margin + tableWidth, yPosition - 20);
                contentStream.stroke();
                yPosition -= 20;

                // Création des en-têtes de colonnes
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.setNonStrokingColor(java.awt.Color.BLACK);
                contentStream.newLineAtOffset(margin, yPosition);

                contentStream.showText("Type");
                contentStream.newLineAtOffset(60, 0);
                contentStream.showText("User Name");
                contentStream.newLineAtOffset(120, 0);
                contentStream.showText("Description");
                contentStream.newLineAtOffset(130, 0);
                contentStream.showText("Date");
                contentStream.newLineAtOffset(50, 0); // Ajustement de la position de la colonne "Date"
                contentStream.showText("Rating");
                contentStream.newLineAtOffset(50, 0); // Ajustement de la position de la colonne "Rating"
               /* contentStream.showText("Answer");
                contentStream.newLineAtOffset(50, 0); // Ajustement de la position de la colonne "Answer"*/
                contentStream.showText("Status");
                contentStream.newLineAtOffset(150, 0); // Ajustement de la position de la colonne "Status"
                contentStream.endText();
                yPosition -= rowHeight;


                // Remplissage du tableau avec les données des réclamations
                for (reclamation rec : reclamations) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.setNonStrokingColor(java.awt.Color.BLACK);
                    contentStream.newLineAtOffset(margin, yPosition);
                    // Vérifier si les données ne sont pas null avant de les utiliser
                    String type = rec.getType() != null ? rec.getType() : "";
                    String nomComplet = rec.getNomComplet() != null ? rec.getNomComplet() : "";
                    String descriRec = rec.getDescriRec() != null ? rec.getDescriRec() : "";
                    String dateStr = rec.getDate() != null ? rec.getDate().toString() : "";
                    String nbrEt = rec.getNbrEt() != null ? rec.getNbrEt() : "";
                    //String reponse = rec.getReponse() != null ? rec.getReponse() : "";
                    String statut = rec.getStatut() != null ? rec.getStatut() : "";
                    contentStream.showText(type);
                    contentStream.newLineAtOffset(25, 0);  // Décalage pour la colonne "Type"
                    contentStream.showText(nomComplet);
                    contentStream.newLineAtOffset(70, 0);  // Décalage pour la colonne "User Name"
                    contentStream.showText(descriRec);
                    contentStream.newLineAtOffset(75, 0);  // Décalage pour la colonne "Description"
                    contentStream.showText(dateStr);
                    contentStream.newLineAtOffset(140, 0);  // Décalage pour la colonne "Date"
                    contentStream.showText(nbrEt);
                    contentStream.newLineAtOffset(150, 0);  // Décalage pour la colonne "Rating"
                   /* contentStream.showText(reponse);
                    contentStream.newLineAtOffset(125, 0);  // Décalage pour la colonne "Answer"*/
                    contentStream.showText(statut);
                    contentStream.newLineAtOffset(150, 0);  // Décalage pour la colonne "Status"
                    contentStream.endText();
                    yPosition -= tableRowHeight;
                }

                // Fermeture du contenu du document
                contentStream.close();

                // Enregistrement du document
                document.save(dest);
                document.close();

                // Affichage d'une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("The PDF file was saved successfully !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No location selected.");
        }
    }


    @FXML
    void print_PDF(ActionEvent event) {
        // Afficher une boîte de dialogue de confirmation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Générer un PDF");
        confirmation.setContentText("Êtes-vous sûr de vouloir générer un PDF avec les réclamations actuelles ?");

        // Attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmation.showAndWait();

        // Si l'utilisateur a confirmé
        if (result.isPresent() && result.get() == ButtonType.OK) {
            List<reclamation> reclamations = tabView.getItems();
            generatePDF(reclamations);
        }
    }

    @FXML
    void searchRec(ActionEvent event) {

        if(filterComboBox.getValue().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Filter Combo not selected");
            alert.setHeaderText(null);
            alert.setContentText("Choose what to search by");
            alert.show();
            return;
        }
        String recRecherche = searchField.getText().trim(); // Récupérer le texte saisi dans le champ de recherche

        try {
            String titre = recRecherche;
            ServiceReclamation sr = new ServiceReclamation();
            List<reclamation> rec = sr.searchBy(filterComboBox.getValue(),titre); // Appeler une méthode de service pour rechercher par quantité

            // Effacer les éléments existants dans le TableView
            tabView.getItems().clear();

            // Ajouter tous les éléments de la liste à la TableView
            tabView.getItems().addAll(rec);
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


    @FXML
    void initialize() {
        assert RecMan != null : "fx:id=\"RecMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colAnswerReclamation != null : "fx:id=\"colAnswerReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colDateReclamation != null : "fx:id=\"colDateReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colDescReclamation != null : "fx:id=\"colDescReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colModifReclamation != null : "fx:id=\"colModifReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colNbrEtRecalamtion != null : "fx:id=\"colNbrEtRecalamtion\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colStatusReclamation != null : "fx:id=\"colStatusReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colTypeReclamation != null : "fx:id=\"colTypeReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert colUserReclamation != null : "fx:id=\"colUserReclamation\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert modif != null : "fx:id=\"modif\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert print != null : "fx:id=\"print\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        assert tabView != null : "fx:id=\"tabView\" was not injected: check your FXML file 'userBackReclamationManagement.fxml'.";
        tabView.setOnMouseClicked(this::handleRowSelection);
        //initialiser les colonnes

        colTypeReclamation.setCellValueFactory(new PropertyValueFactory<>("type"));
        colUserReclamation.setCellValueFactory(cellData -> {
            reclamation rec = cellData.getValue();
            SimpleStringProperty qtProperty;

            ServiceUser serviceUser = new ServiceUser();
            try {
                if(rec.getIdUser() != null)
                {
                    User user = serviceUser.selectById(rec.getIdUser().getId());
                    if (user != null) { // Ajout de la vérification null
                        qtProperty = new SimpleStringProperty(user.getNom());
                        return qtProperty;
                    } else {
                        return new SimpleStringProperty(""); // Retourner une chaîne vide si l'utilisateur est nul
                    }
                }
                else{
                    return new SimpleStringProperty("");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        colDescReclamation.setCellValueFactory(new PropertyValueFactory<>("descriRec"));
        colDateReclamation.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNbrEtRecalamtion.setCellValueFactory(new PropertyValueFactory<>("nbrEt"));
        colAnswerReclamation.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        colStatusReclamation.setCellValueFactory(new PropertyValueFactory<>("statut"));

        afficherRec();
        tabView.setOnMouseClicked(this::handleRowSelection);

// Initialiser le ComboBox avec les différents statuts
        ObservableList<String> statuses = FXCollections.observableArrayList("Nom","Description","Date","Reponse","Statut");
        filterComboBox.setItems(statuses);

        // Ajout d'un écouteur d'événements sur le ComboBox pour détecter les changements de sélection



    }

    // Méthode pour charger les réclamations en fonction du statut sélectionné
    private void loadReclamationsByStatus(String status) {
        try {
            ServiceReclamation service = new ServiceReclamation();
            List<reclamation> rec = service.selectByStatus(status);

            // Effacer les éléments existants dans le TableView
            tabView.getItems().clear();

            // Ajouter les données chargées au TableView
            tabView.getItems().addAll(rec);
        } catch (SQLException e) {
            afficherErreur("Erreur lors du chargement des données", "Une erreur est survenue lors du chargement des données. Veuillez réessayer plus tard.");
        }}}