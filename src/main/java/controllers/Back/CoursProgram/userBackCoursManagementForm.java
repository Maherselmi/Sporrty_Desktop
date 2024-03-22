package controllers.Back.CoursProgram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.cours;
import models.programme;
import services.Mailing;
import services.servicesCours;
import services.servicesProgramme;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userBackCoursManagementForm {


    @FXML
    private ResourceBundle resources;
    @FXML
    private TableView<cours> tabCours;
    @FXML
    private URL location;

    @FXML
    private HBox root;

    @FXML
    private Text imageFullPath;
    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Button HomaMan;

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
    private Button tfajouter;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfcoach;

    @FXML
    private TextField tfduree;

    @FXML
    private ComboBox<String> tfComboBoxType;

    @FXML
    private TextField tfPrix;
    @FXML
    private ComboBox<String> ProgramComboBox;

    @FXML
    private Button tfannuler;

    @FXML
    private Label InscriMessageLabel;

    @FXML
    private TextField lienVideo;
    @FXML
    private ComboBox<String> tfComboBox;
    @FXML
    private Label nameerror;

    @FXML
    private Label coacherror;

    @FXML
    private Label dayserror;

    @FXML
    private Label typeerror;

    @FXML
    private Label priceerror;

    @FXML
    private Label durationerror;
    @FXML
    private ComboBox<String> tfComboBoxProg;
    @FXML
    private Button tfmodifier;
    private Object contenu;

    private cours coursSelectionne;

    @FXML
    void AnnulerForm(ActionEvent event) {
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
    void LogOut(ActionEvent event) {

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
    void OnSelctedType(ActionEvent event) {

        String selectedType = tfComboBox.getValue();
        System.out.println("Type sélectionné : " + selectedType);

    }



    int parsePositiveInt(String text) throws NumberFormatException {
        int value = Integer.parseInt(text);
        if (value <= 0) {
            throw new NumberFormatException("The value must be a positive integer.");
        }
        return value;
    }



    @FXML
    void ajouterCours(ActionEvent event) {
        try {
            boolean isValid = true;
            // Validate input fields
            if (tfnom.getText().isEmpty()) {
                nameerror.setText("Name is required");
                isValid = false;
            } else {
                nameerror.setText("");
            }

            if (tfcoach.getText().isEmpty()) {
                coacherror.setText("Coach is required");
                isValid = false;
            } else {
                coacherror.setText("");
            }

            if (tfComboBoxType.getValue() == null || tfComboBoxType.getValue().isEmpty()) {
                dayserror.setText("Days selection is required");
                isValid = false;
            } else {
                dayserror.setText("");
            }

            if (tfComboBox.getValue() == null || tfComboBox.getValue().isEmpty()) {
                typeerror.setText("Type selection is required");
                isValid = false;
            } else {
                typeerror.setText("");
            }

            if (tfduree.getText().isEmpty() || !isPositiveInt(tfduree.getText())) {
                durationerror.setText("Valid duration is required");
                isValid = false;
            } else {
                durationerror.setText("");
            }

            if (tfPrix.getText().isEmpty() || !isPositiveInt(tfPrix.getText())) {
                priceerror.setText("Valid price is required");
                isValid = false;
            } else {
                priceerror.setText("");
            }

            if (imageFullPath.getText().isEmpty() ) {
                System.out.println("Image not given");
                isValid=false;
            }
            if(lienVideo.getText().isEmpty()){
                System.out.println("Image not given");
                isValid=false;
            }

            if (!isValid) {
                return;
            }

            servicesProgramme servicesProgramme = new servicesProgramme();
            programme id_programme = servicesProgramme.selectByName(ProgramComboBox.getValue());

            // If all input is valid, proceed with adding or updating the course
            servicesCours sc = new servicesCours();
            if (coursSelectionne != null) {
                // Update the properties of the selected course
                coursSelectionne.setNom(tfnom.getText());
                coursSelectionne.setCoach(tfcoach.getText());
                coursSelectionne.setJours(tfComboBoxType.getValue());
                coursSelectionne.setDuree(parsePositiveInt(tfduree.getText()));
                coursSelectionne.setType(tfComboBox.getValue());
                coursSelectionne.setPrix(Integer.parseInt(tfPrix.getText()));
                coursSelectionne.setId_programme(id_programme);
                coursSelectionne.setImage(imageFullPath.getText());
                System.out.println(imageFullPath.getText());
                coursSelectionne.setLienVideo(lienVideo.getText());

                sc.updateOne(coursSelectionne);
            } else {
                // Create a new course if none is selected
                String jours = tfComboBoxType.getValue();
                String type = tfComboBox.getValue();

                cours cours = new cours(tfnom.getText(), tfcoach.getText(), jours, parsePositiveInt(tfduree.getText()), type, Integer.parseInt(tfPrix.getText()),id_programme, imageFullPath.getText(),lienVideo.getText());
                sc.insertOne(cours);
                Mailing.sendHtmlNotification("kaabinawel9@gmail.com","Cours Notification",tfnom.getText(),"Added");

            }

            // Load the new interface after adding/updating the course
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/CoursProgram/userBackCoursManagement.fxml"));
            Parent parent = loader.load();
            Scene scene = UserMan.getScene();
            scene.setRoot(parent);
        } catch (SQLException | IOException | NumberFormatException e) {
            // Handle errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setContentText("You have an error entering your data!");
            alert.show();
        }
    }


    boolean isPositiveInt(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @FXML
    void onSelectDayOfWeek(ActionEvent event) {
        // Retrieve the selected value in the ComboBox
        String selectedDay = tfComboBoxType.getValue(); // Replace "tfComboBoxType" with the actual name of your ComboBox

        // You can do something with the selected value, for example, display it
        System.out.println("Selected day : " + selectedDay);

    }

    // modifier Cours
    public void setTabCoursToUpdate(cours cours) {
        coursSelectionne = cours;

        // Set values for your text fields
        tfnom.setText(cours.getNom());
        tfcoach.setText(cours.getCoach());
        tfduree.setText(String.valueOf(cours.getDuree()));
        tfPrix.setText(String.valueOf(cours.getPrix()));
        lienVideo.setText(cours.getLienVideo());
        imageFullPath.setText(cours.getImage());


        // Set items for ComboBoxes
        tfComboBoxType.setItems(FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        tfComboBox.setItems(FXCollections.observableArrayList("Individuel", "In Groups"));

        // Select the corresponding values in ComboBoxes
        tfComboBoxType.setValue(cours.getJours());
        tfComboBox.setValue(cours.getType());


        servicesProgramme servicesProgramme = new servicesProgramme();

        try {
            ProgramComboBox.setValue(servicesProgramme.selectById(cours.getId_programme().getId()).getNom());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML


    private void afficherErreur(String aucunCoursSélectionné, String s, String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherSucces(String modificationRéussie, String s, String titre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText((String) contenu);

        // Supprime le bouton de fermeture de l'alerte
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.OK);

        alert.showAndWait();

    }





    @FXML
    void openCourManagement(ActionEvent event) {




    }

    @FXML
    void openEventManagement(ActionEvent event) {
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

    @FXML
    void openGymManagement(ActionEvent event) {
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

    @FXML
    void openProductManagement(ActionEvent event) {
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

    @FXML
    void openReclamationManagement(ActionEvent event) {
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

    @FXML
    void openStockManagement(ActionEvent event) {
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

    @FXML
    void openUserBackHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = HomaMan.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void openUserManagement(ActionEvent event) {
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
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert side_ankerpane != null : "fx:id=\"side_ankerpane\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert HomaMan != null : "fx:id=\"HomaMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert UserMan != null : "fx:id=\"UserMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert StockMan != null : "fx:id=\"StockMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert CourMan != null : "fx:id=\"CourMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert EventMan != null : "fx:id=\"EventMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert btn_workbench1111 != null : "fx:id=\"btn_workbench1111\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert LogOut != null : "fx:id=\"LogOut\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert GymMan != null : "fx:id=\"GymMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert ProductMan != null : "fx:id=\"ProductMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert ReclamMan != null : "fx:id=\"ReclamMan\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfajouter != null : "fx:id=\"tfajouter\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfcoach != null : "fx:id=\"tfcoach\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfduree != null : "fx:id=\"tfduree\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfComboBoxType != null : "fx:id=\"tfComboBoxType\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfPrix != null : "fx:id=\"tfPrix\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfannuler != null : "fx:id=\"tfannuler\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert InscriMessageLabel != null : "fx:id=\"InscriMessageLabel\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfComboBox != null : "fx:id=\"tfComboBox\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfComboBoxProg != null : "fx:id=\"tfComboBoxProg\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";
        assert tfmodifier != null : "fx:id=\"tfmodifier\" was not injected: check your FXML file 'userBackCoursManagementForm.fxml'.";

        ObservableList<String> daysOfWeek = FXCollections.observableArrayList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        tfComboBoxType.setItems(daysOfWeek);

        assert tfComboBox != null : "fx:id=\"tfComboBox\" was not injected: check your FXML file 'CoursFXML.fxml'.";
        ObservableList<String> listeType = FXCollections.observableArrayList("individuel", "In Groups");
        tfComboBox.setItems(listeType);


        servicesProgramme servicesProgramme = new servicesProgramme();
        ObservableList<String> listProgram = FXCollections.observableArrayList();

        try {
            for(programme p : servicesProgramme.selectAll()){
                listProgram.add(p.getNom());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ProgramComboBox.setItems(listProgram);
    }


    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            imageFullPath.setText(file.getName());
        }
        String fileName = imageFullPath.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                // Get the resource URL for the uploads directory
                URL resourceUrl = getClass().getClassLoader().getResource("/uploads");
                if (resourceUrl == null) {
                    // If the directory doesn't exist, create it
                    File uploadsDirectory = new File("src/main/resources/uploads");
                    if (!uploadsDirectory.exists()) {
                        uploadsDirectory.mkdirs();
                    }
                    resourceUrl = uploadsDirectory.toURI().toURL();
                }

                // Copy the uploaded file to the uploads directory
                Files.copy(new File(file.getPath()).toPath(), Paths.get(resourceUrl.toURI()).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace(); // Handle exception properly based on your application's requirements
            }
        }
    }


}



