package controllers.Back.EventsReclamation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


import models.evenements;
import services.Mailing;
import services.ServiceEvenement;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class userBackEventManagementForm {
    @FXML
    private Button UserMan;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button Home;
    @FXML
    private Button CourMan;

    @FXML
    private Button EventMan;

    @FXML
    private Button GymMan;

    @FXML
    private Button HomaMan;

    @FXML
    private Label InscriMessageLabel;

    @FXML
    private Button LogOut;

    @FXML
    private Button ProductMan;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;


    @FXML
    private Button annuler;
    @FXML
    private DatePicker tfDate_event;

    @FXML
    private TextField tfDescr;

    @FXML
    private TextField tfLieu;

    @FXML
    private TextField tfNbrP;

    @FXML
    private TextField tfNom;

    @FXML
    private Button tfSave;
    @FXML
    private ComboBox<String> categComboBox;
    private  evenements eventSelectionne;
    @FXML
    private Label dateErrorLabel;

    @FXML
    private Button profile;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Label categorieErrorLabel;

    @FXML
    private Label descrErrorLabel;
    @FXML
    private Label allLabel;

    @FXML
    private Label lieuErrorLabel;

    @FXML
    private Label nbrPErrorLabel;

    @FXML
    private Label nomErrorLabel;





    void GoBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackEventManagement.fxml"));
            Parent parent = loader.load();

            // Récupérer la scène actuelle
            Scene scene = UserMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }}
    public void AnnulerForm(ActionEvent actionEvent) {
        GoBack();

    }
    private boolean validerChamps() {

        // Validation des champs obligatoires
        boolean nomEmpty = tfNom.getText().isEmpty();
        boolean dateEmpty= tfDate_event.getValue()==null;
        boolean descrEmpty = tfDescr.getText().isEmpty();
        boolean categEmpty = categComboBox.getValue() == null;
        boolean lieuEmpty = tfLieu.getText().isEmpty();
       // boolean nbrPEmpty = tfNbrP.getText().isEmpty();

        if (nomEmpty && dateEmpty && descrEmpty && categEmpty && lieuEmpty /*&& nbrPEmpty*/) {
            // Afficher un message indiquant que tous les champs sont obligatoires
            allLabel.setText("All fields are required.");
            return false;
        } else {
            // Réinitialiser le label de message
           allLabel.setText("");
        }

        if (nomEmpty) {
                nomErrorLabel.setText("Please enter the event title.");
            } else {
                nomErrorLabel.setText("");
            }
            if (dateEmpty) {
                dateErrorLabel.setText("Please select a date.");
            } else {
                dateErrorLabel.setText("");
            }
            if (descrEmpty) {
                descrErrorLabel.setText("Please enter the event description.");
            } else {
                descrErrorLabel.setText("");
            }
            if (categEmpty) {
                categorieErrorLabel.setText("Please select a category.");
            } else {
                categorieErrorLabel.setText("");
            }
            if (lieuEmpty) {
                lieuErrorLabel.setText("Please enter the event location.");
            } else {
                lieuErrorLabel.setText("");
            }
           /* if (nbrPEmpty) {
                nbrPErrorLabel.setText("Please enter the number of participants.");
            } else {
                nbrPErrorLabel.setText("");
            }*/

        // Validation supplémentaire (ex: nombre de participants)
      /*  try {
            int nbrParticipants = Integer.parseInt(tfNbrP.getText());
            if (nbrParticipants <= 0) {
                nbrPErrorLabel.setText("Please enter a positive number.");
                return false;
            }
        } catch (NumberFormatException e) {
            nbrPErrorLabel.setText("Please enter a valid integer.");
            return false;
        }*/

        return true;
    }

    @FXML
public void ajouterEvenement(ActionEvent actionEvent) {
    try {
        ServiceEvenement se = new ServiceEvenement();
        if (eventSelectionne != null) {
            if (!validerChamps()) {
                return; // Arrête l'exécution si la validation échoue
            }
           LocalDate localDate = tfDate_event.getValue();

            Date date = Date.valueOf(localDate.now());

            // Mettre à jour les propriétés de l'événement sélectionné
            eventSelectionne.setNom(tfNom.getText());
            eventSelectionne.setDescri(tfDescr.getText());
            eventSelectionne.setCategorie(categComboBox.getValue());
            eventSelectionne.setDate(date);
            eventSelectionne.setLieu(tfLieu.getText());
            //eventSelectionne.setNbrP(Integer.parseInt(tfNbrP.getText()));
            se.updateOne(eventSelectionne);
            Mailing.sendHtmlNotification("ademaloui744@gmail.com","Event Notification",tfNom.getText(),"Updated");

        } else {
            if (!validerChamps()) {
                return; // Arrête l'exécution si la validation échoue
            }
            // Récupérer la catégorie sélectionnée dans le ComboBox
           String categorie = categComboBox.getValue();

            //evenements ev = new evenements(tfNom.getText(), tfDescr.getText(), categorie, Date.valueOf(LocalDate.now()), tfLieu.getText(), Integer.parseInt(tfNbrP.getText()));
            evenements ev = new evenements(tfNom.getText(), tfDescr.getText(), categorie, Date.valueOf(LocalDate.now()), tfLieu.getText());

            se.insertOne(ev);
            Mailing.sendHtmlNotification("nourallani14@gmail.com","Event Notification",tfNom.getText(),"Added");

        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/EventsReclamation/userBackEventManagement.fxml"));
        Parent parent = loader.load(); // Charger l'interface dans le parent

        // Récupérer la scène actuelle
        Scene scene = UserMan.getScene();
        // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
        scene.setRoot(parent);
    } catch (SQLException | IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input error");
        alert.setContentText("You have an error entering your data!");
        alert.show();
    }
}
    public void setEvenementToUpdate(evenements event) {
        eventSelectionne = event;
        tfNom.setText(event.getNom());
        tfDescr.setText(event.getDescri());
        tfLieu.setText(event.getLieu());
       // tfNbrP.setText(String.valueOf(event.getNbrP()));
        // Assurez-vous d'initialiser votre ComboBox avec les catégories disponibles
        categComboBox.setItems(FXCollections.observableArrayList(
                "Group training",
                "Fitness classes",
                "Yoga sessions",
                "Special events",
                "Dance classes",
                "Sports clinics",
                "Family activities",
                "Cycling"
                ));
        // Ensuite, vous pouvez sélectionner la catégorie correspondante
        categComboBox.setValue(event.getCategorie());

        // Si vous utilisez un DatePicker pour la date, vous pouvez le remplir ainsi :
        LocalDate date = event.getDate().toLocalDate();
        tfDate_event.setValue(date);
    }


    public void openUserBackHome(ActionEvent actionEvent) {
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

    public void openUserManagement(ActionEvent actionEvent) {
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

    public void openStockManagement(ActionEvent actionEvent) {
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

    public void openCourManagement(ActionEvent actionEvent) {
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

    public void openEventManagement(ActionEvent actionEvent) {
    }

    public void LogOut(ActionEvent actionEvent) {
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

    public void openGymManagement(ActionEvent actionEvent) {
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

    public void openProductManagement(ActionEvent actionEvent) {
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

    public void openReclamationManagement(ActionEvent actionEvent) {
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



    public void onSelectCateg(ActionEvent actionEvent) {
        String selectedType = categComboBox.getValue();
        System.out.println("Categorie sélected : " + selectedType);
    }


    @FXML
    void initialize() {
        assert Home != null : "fx:id=\"Home\" was not injected: check your FXML file 'userBackCoursManagement.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert annuler != null : "fx:id=\"annuler\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert profile != null : "fx:id=\"profile\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
       assert categComboBox != null : "fx:id=\"categComboBox\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert tfDate_event != null : "fx:id=\"tfDate_event\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        tfDate_event.setValue(LocalDate.now());
        assert tfDescr != null : "fx:id=\"tfDescr\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert tfLieu != null : "fx:id=\"tfLieu\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert tfNbrP != null : "fx:id=\"tfNbrP\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert tfNom != null : "fx:id=\"tfNom\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";
        assert tfSave != null : "fx:id=\"tfSave\" was not injected: check your FXML file 'userBackEventManagementForm.fxml'.";

        ObservableList<String> categ = FXCollections.observableArrayList( "Entraînement en groupe",
                "Cours de fitness",
                "Sessions de yoga",
                "Evénements spéciaux",
                "Séances de bien-être",
                "Ateliers de nutrition",
                "Cours de danse",
                "Cliniques sportives",
                "Activités familiales",
                "Challenges et compétitions");
        categComboBox.setItems(categ);

    }
   /* private void onRoleSelectionChanged(ActionEvent event) {
        String selectedRole = comboTri.getValue();
        if (selectedRole != null) {
            try {
                ServiceEvenement se = new ServiceEvenement();
                List<evenements> ev;
                switch(selectedRole) {
                    case "Date (décroissant)":
                        ev = se.selectAllOrderedByDateDescending();
                        break;
                    case "Date (croissant)":
                        ev = se.selectAllOrderedByDateAscending();
                        break;
                    case "Date (plus récent)":
                        ev = se.selectAllOrderedByDateDesc();
                        break;
                    case "Date (plus ancien)":
                        ev = se.selectAllOrderedByOldestDate();
                        break;
                    default:
                        // Handle default case or throw an error
                        break;
                }
                ObservableList<evenements> userList = FXCollections.observableArrayList(ev);
                tbEvents.setItems(userList);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la récupération des personnes : " + e.getMessage());
            }
        }
    } */


}
