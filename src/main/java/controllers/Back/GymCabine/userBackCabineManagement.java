package controllers.Back.GymCabine;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Cabine;
import models.SalleDeSport;
import services.Mailing;
import services.ServiceCabine;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class userBackCabineManagement {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfrecherche;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane gg;

    @FXML
    private Button Home;
    @FXML
    private ComboBox<String> combobox;

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
    private Pane side_ankerpane;

    @FXML
    private TableView<Cabine> tableView;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableColumn<?, ?> CapCo;

    @FXML
    private TableColumn<?, ?> hasVrCo;

    @FXML
    private TableColumn<?, ?> actionCo;


    @FXML
    private Button btn_workbench111112;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private Button btn_workbench11111211;

    @FXML
    private Button btn_workbench111112111;

    @FXML
    private Button btn_workbench13;

    private SalleDeSport GymSelectionne;

    @FXML
    private Label gymLabel;
    private Cabine cabineSelectionne;

    @FXML
    void handleRowSelection(MouseEvent event) {
        if (event.getClickCount() > 0) {
            cabineSelectionne = (Cabine) tableView.getSelectionModel().getSelectedItem();
        }
    }

    private void afficherSalle() {
        // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView
        tableView.setOnMouseClicked(this::handleRowSelection);
        //initialiser les colonnes
        nomCo.setCellValueFactory(new PropertyValueFactory<>("nom_cabine"));
        CapCo.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        hasVrCo.setCellValueFactory(new PropertyValueFactory<>("has_vr"));
        //affichage
        try {
            ServiceCabine service = new ServiceCabine();
            List<Cabine> CabineList = service.selectAll();

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
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert gg != null : "fx:id=\"gg\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert btn_workbench111112 != null : "fx:id=\"btn_workbench111112\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert btn_workbench11111211 != null : "fx:id=\"btn_workbench11111211\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackCabineManagement.fxml'.";
        combobox.getItems().addAll("Nom_cabine","capacite");
        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Nom_cabine")) {
                // Appeler la méthode pour trier par quantité
                sortByNom();
            } else if (newValue.equals("capacite")) {
                sortByCapacite();
            }
        });
        afficherSalle();
    }
    private void sortByNom() {
        try {
            ServiceCabine service = new ServiceCabine();
            List<Cabine> cabineList = service.selectAll();

            // Trier les éléments par nom
            cabineList.sort(Comparator.comparing(Cabine::getNom_cabine));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<Cabine> sortedList = FXCollections.observableArrayList(cabineList);
            tableView.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
    private void sortByCapacite() {
        try {
            ServiceCabine service = new ServiceCabine();
            List<Cabine> cabineList = service.selectAll();

            // Trier les éléments par quantité de matériel de manière ascendante
            cabineList.sort(Comparator.comparingInt(Cabine::getCapacite));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<Cabine> sortedList = FXCollections.observableArrayList(cabineList);
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

    public void setGym(SalleDeSport salleSelectionne) {
        System.out.println(salleSelectionne.getId_salle());
        GymSelectionne = salleSelectionne;
        gymLabel.setText(salleSelectionne.getNom_salle());
        
    }


    @FXML
    void addCabine(ActionEvent event) {
        if (GymSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackCabineManagementForm.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent
                // Get the controller after the FXML file has been loaded
                userBackCabineManagementForm controller = loader.getController();

                controller.setGym(GymSelectionne);
                // Récupérer la scène actuelle
                Scene scene = UserMan.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void removeCabine(ActionEvent event) {
        if (cabineSelectionne != null) {
            ServiceCabine sp = new ServiceCabine();
            try {
                Mailing.sendHtmlNotification("ademaloui744@gmail.com","Cabine Notification",cabineSelectionne.getNom_cabine(),"Removed");
                sp.Delete(cabineSelectionne);
                afficherSalle();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void updateCabine(ActionEvent event) {
        if (GymSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackCabineManagementForm.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent
                // Get the controller after the FXML file has been loaded
                userBackCabineManagementForm controller = loader.getController();

                controller.setGym(GymSelectionne);
                controller. setCabine(cabineSelectionne);
                // Récupérer la scène actuelle
                Scene scene = UserMan.getScene();
                // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
                scene.setRoot(parent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void recherche(ActionEvent event) {
        String nomRecherche = tfrecherche.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                ServiceCabine service = new ServiceCabine();
                List<Cabine> cabineList = service.searchByNom(nomRecherche); // Appeler une méthode de service pour rechercher par nom

                // Effacer les éléments existants dans le TableView
                tableView.getItems().clear();

                // Ajouter tous les éléments de la liste à la TableView
                tableView.getItems().addAll(cabineList);
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
            alert.setContentText("Veuillez saisir le nom de la cabine a rechercher.");
            alert.show();
        }
    }
    public void generatePDF(List<Cabine> cabinelListe) {
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
                Table table = new Table(4);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("nom_cabine")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("capacite")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("has_vr")));


                // Remplir le tableau avec les données de la liste
                for (Cabine cabine :cabinelListe) {
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(cabine.getNom_cabine()))));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(cabine.getCapacite()))));
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(cabine.isHas_vr()))));



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
    void printt(ActionEvent event) {
        List<Cabine> cabineListe = tableView.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(cabineListe);

    }





}
