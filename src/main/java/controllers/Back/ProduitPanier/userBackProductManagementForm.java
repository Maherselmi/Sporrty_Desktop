package controllers.Back.ProduitPanier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Produit;
import services.Mailing;
import services.ServiceProduit;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Properties;

public class userBackProductManagementForm {


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
    private Text errorUploadingImage;

    @FXML
    private Text uploadImagePath;

    @FXML
    private Button LogOut;

    @FXML
    private ComboBox<String> ProductCategorieField;

    @FXML
    private TextArea ProductDescriptionField;

    @FXML
    private Button ProductMan;

    @FXML
    private TextField ProductNomField;

    @FXML
    private TextField ProductPrixField;

    @FXML
    private TextField ProductQuantiteField;

    @FXML
    private Button ReclamMan;

    @FXML
    private Button StockMan;

    @FXML
    private Button UserMan;

    @FXML
    private Button addT;

    @FXML
    private Button btn_workbench1111;

    @FXML
    private Button btn_workbench1111121;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Label errorNom;

    @FXML
    private Label errorPrix;

    @FXML
    private Label errorQuantite;

    @FXML
    private Label errorCategorie;
    @FXML
    private Label errorDescription;
    private Produit SelectedProduit;

    @FXML
    private ImageView imageUploaded;

    void goBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/ProduitPanier/userBackProductManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = errorDescription.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            uploadImagePath.setText(file.getName());
        }
        String fileName = uploadImagePath.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                URL resourceUrl = getClass().getClassLoader().getResource("/assets");
                if (resourceUrl == null) {
                    File uploadsDirectory = new File("src/main/resources/upload/products");
                    if (!uploadsDirectory.exists()) {
                        uploadsDirectory.mkdirs();
                    }
                    resourceUrl = uploadsDirectory.toURI().toURL();
                }

                Files.copy(new File(file.getPath()).toPath(), Paths.get(resourceUrl.toURI()).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                Image image = new Image(new FileInputStream(file));
                imageUploaded.setImage(image);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void cancelProduct(ActionEvent event) {
        goBack();
    }

    @FXML
    void submitProduct(ActionEvent event) {
        // Clear previous error messages
        clearErrors();

        // Validate input
        boolean isValid = true;

        if (ProductNomField.getText().isEmpty()) {
            errorNom.setText("Product name is required");
            isValid = false;
        }

        try {
            float ProductPrix = Float.parseFloat(ProductPrixField.getText());
            if (ProductPrix <= 0) {
                errorPrix.setText("Price must be greater than 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorPrix.setText("Invalid price format");
            isValid = false;
        }

        try {
            int ProductQte = Integer.parseInt(ProductQuantiteField.getText());
            if (ProductQte <= 0) {
                errorQuantite.setText("Quantity must be greater than 0");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            errorQuantite.setText("Invalid quantity format");
            isValid = false;
        }

        if (ProductCategorieField.getValue() == null) {
            errorCategorie.setText("Please select a category");
            isValid = false;
        }

        if (ProductDescriptionField.getText().isEmpty())
        {
            errorDescription.setText("Product name is required");
            isValid = false;
        }

        if (isValid) {
            try {
                ServiceProduit sp = new ServiceProduit();

                String ProductNom = ProductNomField.getText();
                float ProductPrix = Float.parseFloat(ProductPrixField.getText());
                int ProductQte = Integer.parseInt(ProductQuantiteField.getText());
                String ProductDescription = ProductDescriptionField.getText();
                String ProductCategory = ProductCategorieField.getValue();
                String image = uploadImagePath.getText();

                if(SelectedProduit != null)
                {
                    Produit product = new Produit(SelectedProduit.getId(),ProductNom, ProductPrix, ProductQte, ProductDescription, ProductCategory,image);
                    sp.updateOne(product);
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Product Notification",ProductNom,"Updated");

                }else{
                    Produit product = new Produit(ProductNom, ProductPrix, ProductQte, ProductDescription, ProductCategory,image);
                    sp.insertOne(product);
                    Mailing.sendHtmlNotification("ademaloui744@gmail.com","Product Notification",ProductNom,"Added");

                }
                goBack();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void   SendEmail(String user, String pass, String to, String sub, String msg)
    {
        Properties prop= new Properties();

        prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
        prop.put("mail.smtp.auth",true);
        prop.put("mail.smtp.starttls.enable",true);
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");


        Session session= Session.getInstance(prop, new javax.mail.Authenticator()
        {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication()
            {
                return new javax.mail.PasswordAuthentication(user, pass);

            }

        });

        try
        {
            Message message1= new MimeMessage(session);

            message1.setFrom( new InternetAddress(user));
            message1.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message1.setSubject(sub);
            message1.setText(msg);

            Transport.send(message1);

            JOptionPane.showMessageDialog(null,"message sent");

        }

        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }

    private void clearErrors() {
        errorNom.setText("");
        errorPrix.setText("");
        errorQuantite.setText("");
        errorCategorie.setText("");
    }


    @FXML
    private void openUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/UserAbonement/userBackUserManagement.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void openUserBackHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackerrorDescription.fxml"));
            Parent parent = loader.load(); // Charger l'interface dans le parent

            // Récupérer la scène actuelle
            Scene scene = errorDescription.getScene();
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
            Scene scene = errorDescription.getScene();
            // Remplacer le contenu de la scène actuelle par le contenu de la nouvelle interface
            scene.setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProduct(Produit selectedProduit) {
        SelectedProduit = selectedProduit;

        ProductNomField.setText(selectedProduit.getNom());
        ProductPrixField.setText(String.valueOf(selectedProduit.getPrix()));
        ProductQuantiteField.setText(String.valueOf(selectedProduit.getQte()));
        ProductDescriptionField.setText(selectedProduit.getDescription());
        ProductCategorieField.setValue(selectedProduit.getCategorie());
        uploadImagePath.setText(selectedProduit.getImage());
    }
}
