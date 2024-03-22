package controllers.Back;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.ServiceReclamation;
import services.ServiceSalle;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserBackHomeFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Button btn_workbench1;

    @FXML
    private Button btn_workbench11;

    @FXML
    private Button btn_workbench111;

    @FXML
    private Button btn_workbench12;

    @FXML
    private Button btn_workbench112;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench111111;

    @FXML
    private Button btn_workbench1121;

    @FXML
    private Button btn_workbench11211;

    @FXML
    private Button btn_workbench1122;

    @FXML
    private Pane pane_1111;

    @FXML
    private Pane pane_11111;

    @FXML
    private Pane pane_111111;

    @FXML
    private Pane pane_111111111;

    @FXML
    private Pane pane_1111111111;

    @FXML
    private Label TotalGymsText;

    @FXML
    private Label totalReclamationText;

    @FXML
    private Label totalUsersText;

    @FXML
    private StackedAreaChart<String, Number> statReclamation;

    @FXML
    private StackedAreaChart<?, ?> statsCabineVr;
    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench1 != null : "fx:id=\"btn_workbench1\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench11 != null : "fx:id=\"btn_workbench11\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench111 != null : "fx:id=\"btn_workbench111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench12 != null : "fx:id=\"btn_workbench12\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench112 != null : "fx:id=\"btn_workbench112\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench111111 != null : "fx:id=\"btn_workbench111111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench1121 != null : "fx:id=\"btn_workbench1121\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench11211 != null : "fx:id=\"btn_workbench11211\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert btn_workbench1122 != null : "fx:id=\"btn_workbench1122\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert pane_1111 != null : "fx:id=\"pane_1111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert pane_11111 != null : "fx:id=\"pane_11111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert pane_111111 != null : "fx:id=\"pane_111111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert pane_111111111 != null : "fx:id=\"pane_111111111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        assert pane_1111111111 != null : "fx:id=\"pane_1111111111\" was not injected: check your FXML file 'userBackHome.fxml'.";
        ServiceUser serviceUser = new ServiceUser();
        try {
            totalUsersText.setText(String.valueOf(serviceUser.selectAll().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceReclamation serviceReclamation = new ServiceReclamation();
        try {
            totalReclamationText.setText(String.valueOf(serviceReclamation.selectAll().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceSalle serviceSalle = new ServiceSalle();
        try {
            TotalGymsText.setText(String.valueOf(serviceSalle.selectAll().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackUserManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = side_ankerpane.getScene();
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
            Scene scene = side_ankerpane.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void LogOut() {
        Stage stage = (Stage) side_ankerpane.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void openCourManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackCoursManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = side_ankerpane.getScene();
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
            Scene scene = side_ankerpane.getScene();
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
            Scene scene = side_ankerpane.getScene();
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
            Scene scene = side_ankerpane.getScene();
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
            Scene scene = side_ankerpane.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
