package controllers.Back.GymCabine;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.SalleDeSport;
import services.Mailing;
import services.MapGeo;
import services.ServiceSalle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



public class userBackGymManagement {

    @FXML
    private HBox root;

    @FXML
    private AnchorPane gg;

    @FXML
    private ComboBox<String> comboboxx;
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
    private TableView<SalleDeSport> tableView;

    @FXML
    private TableColumn<?, ?> NomCo;

    @FXML
    private TableColumn<?, ?> DescriptionCo;

    @FXML
    private TableColumn<?, ?> LieuCo;

    @FXML
    private TableColumn<?, ?> NumeroCo;

    @FXML
    private TableColumn<?, ?> ActionCo;

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

    @FXML
    private Button btn_workbench1111121111;
    private SalleDeSport salleSelectionne;
    @FXML
    private TextField tfrecherches;
    @FXML
    void handleRowSelection(MouseEvent event) {
        if (event.getClickCount() > 0) {
            salleSelectionne = (SalleDeSport) tableView.getSelectionModel().getSelectedItem();
        }
    }
    @FXML
    void UpdateGym(ActionEvent event) {
        if(salleSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackGymManagementForm.fxml"));

                Parent parent = loader.load(); // Load the FXML file to initialize the controller

                // Get the controller after the FXML file has been loaded
                userBackGymManagementForm controller = loader.getController();
                if (salleSelectionne != null) {
                    controller.setGymToUpDate(salleSelectionne);
                }
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
    void showMap(ActionEvent event) {
        if(salleSelectionne != null)
        {
            MapGeo mapGeo = new MapGeo();
            String location = salleSelectionne.getLocation();
            String[] parts = location.split(",");
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);

            mapGeo.addMarker(latitude,longitude);
            WebView webView = mapGeo.getWebView();

            Stage stage = new Stage();
            Scene scene = new Scene(webView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void AddGym(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackGymManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void afficherSalle() {
        // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView
        tableView.setOnMouseClicked(this::handleRowSelection);
        //initialiser les colonnes
        NomCo.setCellValueFactory(new PropertyValueFactory<>("Nom_salle"));
        LieuCo.setCellValueFactory(new PropertyValueFactory<>("Lieu_salle"));
        NumeroCo.setCellValueFactory(new PropertyValueFactory<>("Num_salle"));
        DescriptionCo.setCellValueFactory(new  PropertyValueFactory<>("Descr"));
        //affichage
        try {
            ServiceSalle service = new ServiceSalle();
            List<SalleDeSport> GymsList = service.selectAll();

            // Clear existing items in the TableView
            tableView.getItems().clear();

            // Add all items from the list to the TableView
            tableView.getItems().addAll(GymsList);
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
            Scene scene =UserMan.getScene();
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
    void removeGym(ActionEvent event) {
        if (salleSelectionne != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer la salle de sport sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a cliqué sur OK, supprimer la salle de sport
                ServiceSalle sp = new ServiceSalle();
                try {
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Gym Notification",salleSelectionne.getNom_salle(),"Removed");
                    sp.Delete(salleSelectionne);
                    afficherSalle();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


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
    void showCabine(ActionEvent event) {
        if (salleSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackCabineManagement.fxml"));
                Parent parent = loader.load(); // Charger l'interface dans le parent

                userBackCabineManagement controller = loader.getController();
                controller.setGym(salleSelectionne);
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
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert gg != null : "fx:id=\"gg\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert NomCo != null : "fx:id=\"NomCo\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert DescriptionCo != null : "fx:id=\"DescriptionCo\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert LieuCo != null : "fx:id=\"LieuCo\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert NumeroCo != null : "fx:id=\"NumeroCo\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert ActionCo != null : "fx:id=\"ActionCo\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench111112 != null : "fx:id=\"btn_workbench111112\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench1111121 != null : "fx:id=\"btn_workbench1111121\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench11111211 != null : "fx:id=\"btn_workbench11111211\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench13 != null : "fx:id=\"btn_workbench13\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        assert btn_workbench1111121111 != null : "fx:id=\"btn_workbench1111121111\" was not injected: check your FXML file 'userBackGymManagement.fxml'.";
        comboboxx.getItems().addAll("nom_salle","num_salle" , "lieu_salle");
        comboboxx.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("nom_salle")) {
                // Call the method to sort by name
                sortByNom();
            } else if (newValue.equals("num_salle")) {
                sortByNumSalle(); // Assuming this method is defined to sort by num_salle
            }  else if (newValue.equals("lieu_salle")) {
                sortByLieuSalle(); // Assuming this method is defined to sort by lieu_salle
            }
        });
        afficherSalle();
    }
    private void sortByNom() {
        try {
            ServiceSalle service = new ServiceSalle();
            List<SalleDeSport> sallelist = service.selectAll();

            // Trier les éléments par nom
            sallelist.sort(Comparator.comparing(SalleDeSport::getNom_salle));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<SalleDeSport> sortedList = FXCollections.observableArrayList(sallelist);
            tableView.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
    private void sortByLieuSalle() {
        try {
            ServiceSalle service = new ServiceSalle();
            List<SalleDeSport> sallelist = service.selectAll();

            // Trier les éléments par nom
            sallelist.sort(Comparator.comparing(SalleDeSport::getLieu_salle));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<SalleDeSport> sortedList = FXCollections.observableArrayList(sallelist);
            tableView.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }


    private void sortByNumSalle() {
        try {
            ServiceSalle service = new ServiceSalle();
            List<SalleDeSport> sallelist = service.selectAll();


            // Trier les éléments par quantité de matériel de manière ascendante
            sallelist.sort(Comparator.comparing(SalleDeSport::getNum_salle));

            // Mettre à jour la TableView avec les éléments triés
            ObservableList<SalleDeSport> sortedList = FXCollections.observableArrayList(sallelist);
            tableView.setItems(sortedList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

    }
    @FXML

    void searchgym(ActionEvent event) {
        String nomRecherche = tfrecherches.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                ServiceSalle service = new ServiceSalle();
                List<SalleDeSport> sallelist = service.searchByNom(nomRecherche); // Appeler une méthode de service pour rechercher par nom

                // Effacer les éléments existants dans le TableView
                tableView.getItems().clear();

                // Ajouter tous les éléments de la liste à la TableView
                tableView.getItems().addAll(sallelist);
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
    public void generatePDF(List<SalleDeSport> SallelListe) {
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
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Num_salle")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Nom_salle")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Description")));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("lieuSalle")));


                // Remplir le tableau avec les données de la liste
                for (SalleDeSport salle :SallelListe) {
                    table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(salle.getNum_salle()))));
                    table.addCell(new Cell().add(new Paragraph(salle.getNom_salle())));
                    table.addCell(new Cell().add(new Paragraph(salle.getDescr())));
                    table.addCell(new Cell().add(new Paragraph(salle.getLieu_salle())));



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
        List<SalleDeSport> salleListe = tableView.getItems();

        // Appeler la méthode generatePDF pour créer le PDF
        generatePDF(salleListe);

    }







}
