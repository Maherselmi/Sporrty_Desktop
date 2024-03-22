package controllers.Back.EventsReclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Participation;
import models.User;
import models.evenements;
import services.ServiceParticipation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class userBackParticipationManagement {

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
    private Button LogOut;

    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button btnRechercher;

    @FXML
    private Button btn_workbench111112111;

    @FXML
    private TableColumn<Participation, evenements> colEvent;
    @FXML
    private TableColumn<Participation, evenements> colDate;




    @FXML
    private TableColumn<Participation, User> colUser;

    @FXML
    private ComboBox<?> comboTri;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableView<Participation> tbPart;

    @FXML
    private Button tfAdd;

    @FXML
    private Button tfUpd;

    @FXML
    private TextField tf_recherche;

    private Participation partSelectionne;

    public void handleRowSelection(MouseEvent mouseEvent) {
        // Vérifie si un clic de souris a été effectué
        if (mouseEvent.getClickCount() > 0) {
            // Récupérer la ligne sélectionnée dans le TableView
            partSelectionne = tbPart.getSelectionModel().getSelectedItem();

        }
    }

public void afficherPart() {
    // Lier la méthode handleRowSelection à l'événement de sélection de ligne dans le TableView
    tbPart.setOnMouseClicked(this::handleRowSelection);

    // Initialiser les colonnes
    colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
    colEvent.setCellValueFactory(new PropertyValueFactory<>("evenement"));
    colDate.setCellValueFactory(new PropertyValueFactory<>("evenement"));


    // Définir la cellule personnalisée pour la colonne User
    colUser.setCellFactory(column -> new TableCell<Participation, User>() {
        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);
            if (user == null || empty) {
                setText(null);
            } else {
                setText(user.getNom());
                setAlignment(Pos.CENTER); // Centre le texte dans la cellule
                setStyle("-fx-alignment: center;"); // Centre le contenu de la cellule
            }
        }
    });

    // Définir la cellule personnalisée pour la colonne Event
    colEvent.setCellFactory(column -> new TableCell<Participation, evenements>() {
        @Override
        protected void updateItem(evenements event, boolean empty) {
            super.updateItem(event, empty);
            if (event == null || empty) {
                setText(null);
            } else {
                setText(event.getNom());
                setAlignment(Pos.CENTER); // Centre le texte dans la cellule
                setStyle("-fx-alignment: center;"); // Centre le contenu de la cellule
            }
        }
    });
// Définir la cellule personnalisée pour la colonne Event
    colDate.setCellFactory(column -> new TableCell<Participation, evenements>() {
        @Override
        protected void updateItem(evenements event, boolean empty) {
            super.updateItem(event, empty);
            if (event == null || empty) {
                setText(null);
            } else {
                Date date = event.getDate();
                if (date != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date que vous souhaitez
                    String formattedDate = dateFormat.format(date);
                    setText(formattedDate);
                    setAlignment(Pos.CENTER); // Centre le texte dans la cellule
                    setStyle("-fx-alignment: center;"); // Centre le contenu de la cellule
                } else {
                    setText(""); // Si la date est nulle
                }
            }
        }
    });
    // Affichage des données
    try {
        ServiceParticipation sp = new ServiceParticipation();
        List<Participation> p = sp.selectAll();

        // Clear existing items in the TableView
        tbPart.getItems().clear();

        // Ajouter les données chargées au TableView
        tbPart.getItems().addAll(p);
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error loading data");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while loading data. Please try again later.");
        alert.show();
    }
}

    public void openBackHome(ActionEvent event) {
    }

    public void openUserManagement(ActionEvent event) {
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

    public void LogOut(ActionEvent event) {
    }

    public void openCourManagement(ActionEvent event) {
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

    public void openStockManagement(ActionEvent event) {
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

    public void openReclamationManagement(ActionEvent event) {
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

    public void openProductManagement(ActionEvent event) {
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

    public void Rechercher(ActionEvent event) {
        String userRecherche = tf_recherche.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (userRecherche.isEmpty()) { // Vérifier si le champ de recherche est vide
            // Afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty search field");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a username to search.");
            alert.show();
            return; // Sortir de la méthode car le champ de recherche est vide
        }

        try {
            ServiceParticipation service = new ServiceParticipation();
            List<Participation> participations = service.searchByUser(userRecherche); // Appeler la méthode de service pour rechercher par nom d'utilisateur

            // Effacer les éléments existants dans le TableView
            tbPart.getItems().clear();

            // Ajouter tous les éléments de la liste à la TableView
            tbPart.getItems().addAll(participations);
        } catch (SQLException e) {
            // Gérer les erreurs de manière appropriée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la recherche");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred during the search. Please try again later.");
            alert.show();
        }

    }

    public void onRoleSelectionChanged(ActionEvent event) {
    }

    public void print(ActionEvent event) {
    }



    public void openGymManagement(ActionEvent event) {
    }
    @FXML
    void initialize() {
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert btnRechercher != null : "fx:id=\"btnRechercher\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert btn_workbench111112111 != null : "fx:id=\"btn_workbench111112111\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert colEvent != null : "fx:id=\"colEvent\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert colUser != null : "fx:id=\"colUser\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert comboTri != null : "fx:id=\"comboTri\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert tbPart != null : "fx:id=\"tbPart\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert tfAdd != null : "fx:id=\"tfAdd\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert tfUpd != null : "fx:id=\"tfUpd\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
        assert tf_recherche != null : "fx:id=\"tf_recherche\" was not injected: check your FXML file 'userBackParticipationManagement.fxml'.";
  afficherPart();
    }

}
