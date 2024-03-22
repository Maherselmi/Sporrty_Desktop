package controllers.Back.UserAbonement;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Role;
import models.User;
import services.ServiceUser;
import services.ServiceUserAbonnement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class userBackUserManagementFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> tableView;

  //  @FXML
 //   private TableColumn<?, ?> idCo;

    @FXML
    private TableColumn<?, ?> NomCo;

    @FXML
    private TableColumn<?, ?> PrenomCo;

    @FXML
    private TableColumn<?, ?> emailCo;

    @FXML
    private TableColumn<?, ?> paswordCo;

    @FXML
    private TableColumn<?, ?> RoleCo;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField FilterField;
    @FXML
    private ComboBox<Role> RoleC;
    @FXML
    private TableColumn<User, String> colModif;
    @FXML
    private ImageView RefId;
    @FXML
    private Button stokMan;
    @FXML
    ObservableList<User> userList = FXCollections.observableArrayList();
@FXML
        private TextField searchField;


    TableColumn<User, Void> actionsCol = new TableColumn<>("Actions");

    public void displayAllUserInTableView() {
        //idCo.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomCo.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomCo.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCo.setCellValueFactory(new PropertyValueFactory<>("email"));
        paswordCo.setCellValueFactory(new PropertyValueFactory<>("password"));
        RoleCo.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            ServiceUser serviceUser = new ServiceUser();
            List<User> users = serviceUser.selectAll();
            ObservableList<User> userList = FXCollections.observableArrayList(users);
            tableView.setItems(userList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (TableColumn<User, String> param) -> {
            final TableCell<User, String> cell = new TableCell<>() {
                final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                final HBox hb = new HBox(deleteIcon);

                {
                    deleteIcon.setStyle(
                            "-fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-fill:#ff1744;"
                    );

                    deleteIcon.setOnMouseClicked(event -> {
                        User userToDelete = getTableView().getItems().get(getIndex());

                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Remove Confirm");
                        confirmation.setHeaderText("Are you sure you want to delete this user?");
                        confirmation.setContentText(
                                "This action is irreversible.");

                        Optional<ButtonType> result = confirmation.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            try { ServiceUser service = new ServiceUser();

                                // Supprimer l'utilisateur de la table abonnement_utilisateur
                                ServiceUserAbonnement serviceAbonnement = new ServiceUserAbonnement();
                                serviceAbonnement.deleteAbonnementUserUser(userToDelete.getId());




                                service.deleteUser(userToDelete);
                                tableView.getItems().remove(userToDelete);

                                afficherSucces("Deletion successful", "User was successfully deleted !");
                            } catch (SQLException e) {
                                afficherErreur("Erreur de suppression", "An error occurred while deleting the user.");
                            }
                        }
                    });

                    hb.setSpacing(5);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(hb);
                    }
                }
            };

            return cell;
        };

        colModif.setCellFactory(cellFactory);
    }

    @FXML
    private void refreshUserTable(MouseEvent event) {
        displayAllUserInTableView();
    }


    @FXML
    void initialize() {
        displayAllUserInTableView();
        RoleC.setOnAction(this::onRoleSelectionChanged);
        RefId.setOnMouseClicked(this::refreshUserTable);
        //configureActionsColumn();
        RoleC.getItems().addAll(Role.ADHERANT, Role.ADMIN, Role.COACH);
        assert tableView != null : "fx:id=\"Tab_user\" was not injected: check your FXML file 'UserBack.fxml'.";
       // assert idCo != null : "fx:id=\"idCo\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert NomCo != null : "fx:id=\"NomCo\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert PrenomCo != null : "fx:id=\"PrenomCo\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert emailCo != null : "fx:id=\"emailCo\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert colModif != null : "fx:id=\"colModif\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert paswordCo != null : "fx:id=\"paswordCo\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert RoleC != null : "fx:id=\"RoleC\" was not injected: check your FXML file 'UserBack.fxml'.";
        assert RoleCo != null : "fx:id=\"RoleCo\" was not injected: check your FXML file 'UserBack.fxml'.";

    }

    /* private void openEditUserDialog(User user) {
         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification.fxml"));
             Parent parent = loader.load(); // Charger l'interface dans la variable parent
             Scene scene = new Scene(parent);
             Stage stage = new Stage();
             stage.setTitle("BackUser");
             stage.setScene(scene);


             stage.show();

         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }*/
    @FXML
    private void openCreatebackInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackUserManagementForm.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = tableView.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onRoleSelectionChanged(ActionEvent event) {
        Role selectedRole = RoleC.getValue();
        if (selectedRole != null) {
            try {
                ServiceUser serviceUser = new ServiceUser();
                List<User> users = serviceUser.selectByRole(selectedRole);
                ObservableList<User> userList = FXCollections.observableArrayList(users);
                tableView.setItems(userList);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
            }
        }
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherSucces(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);

        // Supprime le bouton de fermeture de l'alerte
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.OK);

        alert.showAndWait();
    }

    @FXML
    private void openUserBackHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = tableView.getScene();
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
            Scene scene = stokMan.getScene();
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
            Scene scene = stokMan.getScene();
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
            Scene scene = stokMan.getScene();
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
            Scene scene = stokMan.getScene();
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
            Scene scene = stokMan.getScene();
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
            Scene scene = stokMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openAbonnementInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackAbonementManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = tableView.getScene();
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
            Scene scene = tableView.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void openUpdate(ActionEvent actionEvent) {
        // Récupérer l'utilisateur sélectionné dans le tableau
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            // Afficher un message d'erreur ou retourner si aucun utilisateur n'est sélectionné
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userManagementFormUpdate.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Passer les données de l'utilisateur sélectionné au contrôleur de mise à jour
            userManagementFormUpdate updateController = loader.getController();
            updateController.initData(selectedUser);

            // Récupérer la scène actuelle
            Scene scene = tableView.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void displayOnlyMember(ActionEvent event) {
        try {
            ServiceUserAbonnement serviceUserAbonnement = new ServiceUserAbonnement();
            List<User> usersWithAbonnements = serviceUserAbonnement.getUsersWithAbonnements();
            ObservableList<User> userList = FXCollections.observableArrayList(usersWithAbonnements);
            tableView.setItems(userList);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs avec abonnements : " + e.getMessage());
        }
    }
    @FXML
    private void searchUsers(ActionEvent event) {
        String searchTerm = searchField.getText();
        if (searchTerm.isEmpty()) {
            displayAllUserInTableView();
        } else {
            try {
                ServiceUser serviceUser = new ServiceUser();
                List<User> users = serviceUser.searchByName(searchTerm);
                ObservableList<User> userList = FXCollections.observableArrayList(users);
                tableView.setItems(userList);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la recherche des utilisateurs : " + e.getMessage());
            }
        }
    }

}
