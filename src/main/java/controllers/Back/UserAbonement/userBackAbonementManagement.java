package controllers.Back.UserAbonement;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Abonnement;
import services.ServiceAbonnement;
import services.ServiceUserAbonnement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class userBackAbonementManagement {

    @FXML
    private TextField searchType;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField prixTextField;
    @FXML
    private TextField typeTextSearch;

    @FXML
    private TableView<Abonnement> tableView;
    @FXML
    private TableColumn<Abonnement, Double> prixColumn;
    @FXML
    private TableColumn<Abonnement, String> actionsColumn;

    @FXML
    private TableColumn<Abonnement, String> typeColumn;
    @FXML
    private TableColumn<Abonnement, String> descriptionColumn;
    @FXML
    private Label cc_A;
    @FXML
    private TableColumn<Abonnement, String> colModif;
    public void initialize() {
        tableView.setOnMouseClicked(event -> {
            // Vérifier si l'événement est un simple clic
            if (event.getClickCount() == 1) {
                // Récupérer l'objet Abonnement de la ligne sélectionnée
                Abonnement abonnement = tableView.getSelectionModel().getSelectedItem();
                if (abonnement != null) {
                    // Remplir les TextField avec les données de l'Abonnement
                    typeTextField.setText(abonnement.getType().toString());
                    prixTextField.setText(String.valueOf(abonnement.getPrix()));
                    descriptionTextField.setText(abonnement.getDescription().toString());
                }
            }
        });


        // Charger les données de la base de données et les afficher dans la table
        loadAbonnements();
    }
    private void loadAbonnements() {
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
        try {
            String searchType = typeTextSearch.getText(); // Récupérer le texte de recherche
            List<Abonnement> abonnements;
            if (searchType != null && !searchType.isEmpty()) {
                abonnements = serviceAbonnement.selectAbonnementsByType(searchType);
            } else {
                abonnements = serviceAbonnement.selectAllAbonnements();
            }

            // Filtrer les abonnements pour afficher uniquement ceux dont le type commence par le texte de recherche
            List<Abonnement> filteredAbonnements = abonnements.stream()
                    .filter(abonnement -> abonnement.getType().startsWith(searchType))
                    .collect(Collectors.toList());

            tableView.getItems().setAll(filteredAbonnements);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter le bouton de suppression
        Callback<TableColumn<Abonnement, String>, TableCell<Abonnement, String>> cellFactory = (TableColumn<Abonnement, String> param) -> {
            final TableCell<Abonnement, String> cell = new TableCell<>() {
                final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                final HBox hb = new HBox(deleteIcon);

                {
                    deleteIcon.setStyle(
                            "-fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-fill:#ff1744;"
                    );

                    deleteIcon.setOnMouseClicked(event -> {
                        Abonnement abonnementToDelete = getTableView().getItems().get(getIndex());

                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Remove confirm");
                        confirmation.setHeaderText("Are you sure you want to delete this subscription\n ?");
                        confirmation.setContentText("\n" + "This action is irreversible.");

                        Optional<ButtonType> result = confirmation.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            try {
                                ServiceUserAbonnement serviceUser = new ServiceUserAbonnement();
                                serviceUser.deleteAbonnementUserAbonnement(abonnementToDelete.getId());
                                serviceAbonnement.deleteAbonnement(abonnementToDelete);
                                tableView.getItems().remove(abonnementToDelete);
                                afficherSucces("Remove Confirm", "Subscription was successfully deleted!");
                            } catch (SQLException e) {
                                afficherErreur("Erreur de l'abonnement", "\n" +
                                        "An error occurred while deleting the subscription.");
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
    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void openUserBackHome() {
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
            Scene scene = tableView.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openCourManagement(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackCoursManagement.fxml"));
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

    public void openGymManagement(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/GymCabine/userBackGymManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = tableView.getScene();
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
            Scene scene = tableView.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openReclamationManagement(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackReclamationManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = tableView.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openCreatebackInterface(ActionEvent actionEvent) {
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
    private void LogOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/loginFxml.fxml"));
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
    void handleSubmit(ActionEvent event) {
        Abonnement abonnement = tableView.getSelectionModel().getSelectedItem();
        if (abonnement == null) {
            // Aucune ligne sélectionnée, afficher un message d'erreur
            // Vous pouvez utiliser une boîte de dialogue ou une autre méthode pour afficher le message
            System.out.println("No subscription selected");
            return;
        }

        // Mettre à jour les données de l'abonnement avec les nouvelles valeurs des champs d'édition
        abonnement.setType(typeTextField.getText());
        abonnement.setPrix(Double.parseDouble(prixTextField.getText()));
        abonnement.setDescription(descriptionTextField.getText());
        // Mettre à jour l'abonnement dans la base de données
        try {
            ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
            serviceAbonnement.updateAbonnement(abonnement);
            // Rafraîchir les données dans le TableView
            loadAbonnements();
            // Vider les champs
            typeTextField.setText("");
            prixTextField.setText("");
            descriptionTextField.setText("");
        } catch (SQLException e) {
            // Gérer les erreurs
            e.printStackTrace();
        }
    }

   @FXML
   void handleCreate(ActionEvent event) {
       // Récupérer les valeurs des champs
       String type = typeTextField.getText();
       String prixText = prixTextField.getText();
       String description = descriptionTextField.getText();

       // Vérifier si les champs ne sont pas vides
       if (!type.isBlank() && !prixText.isBlank() && !description.isBlank()) {
           try {
               // Vérifier si le prix est un nombre valide
               double prix = Double.parseDouble(prixText);

               if (prix > 0) {
                   // Vérifier si le type d'abonnement est unique
                   ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
                   boolean typeExists = serviceAbonnement.typeExists(type);

                   if (!typeExists) {
                       // Créer un nouvel abonnement
                       Abonnement abonnement = new Abonnement(prix, type, description);

                       // Ajouter l'abonnement à la base de données
                       serviceAbonnement.insertAbonnement(abonnement);

                       // Rafraîchir les données dans le TableView
                       loadAbonnements();

                       // Vider les champs
                       typeTextField.setText("");
                       prixTextField.setText("");
                       descriptionTextField.setText("");
                   } else {
                       // Le type d'abonnement existe déjà
                       // Afficher un message d'erreur
                       cc_A.setText("This type of subscription already exists.");
                   }
               } else {
                   // Le prix doit être supérieur à zéro
                   // Afficher un message d'erreur
                   cc_A.setText("The price must be greater than zero.");
               }
           } catch (NumberFormatException e) {
               // Le prix n'est pas un nombre valide
               // Afficher un message d'erreur
               cc_A.setText("The price must be a valid number.");
           } catch (SQLException e) {
               // Gérer les erreurs
               e.printStackTrace();
           }
       } else {
           // Certains champs sont vides
           // Afficher un message d'erreur
           cc_A.setText("Please complete all fields.");
       }
   }
    @FXML
    private void openUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackUserManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = cc_A.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleSearchButton(ActionEvent event) {
        String searchType = typeTextSearch.getText(); // Utilisez le nouveau champ de recherche
        if (searchType != null && !searchType.isEmpty()) {
            try {
                ServiceAbonnement serviceAbonnement = new ServiceAbonnement();
                List<Abonnement> abonnements = serviceAbonnement.selectAbonnementsByType(searchType);
                tableView.getItems().setAll(abonnements);
            } catch (SQLException e) {
                // Gérer les erreurs
                e.printStackTrace();
            }
        } else {
            // Si le champ de recherche est vide, afficher tous les abonnements
            loadAbonnements();
        }
    }





}
