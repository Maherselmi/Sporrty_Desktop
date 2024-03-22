package controllers.Authentification;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import controllers.Front.UserFrontHome;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Role;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;
import services.ServiceUser;
import utils.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class SignIn {
    @FXML
    private TextField PasswordField1;

    @FXML
    private Label captchaTextLabel;


    @FXML
    private TextField captchaTextField;


    @FXML
    private ResourceBundle resources;

  @FXML
    private URL location;

    @FXML
    private TextField EmailLabel;
    @FXML
    private PasswordField PasswordLabelP;
    @FXML
    private HBox PasswordLabel;


    @FXML
    private Button LoginButton;

    @FXML
    private CheckBox LoginShowPassword;

    @FXML
    private Label LoginMessageLabel;
    @FXML
    private Hyperlink forget;
    @FXML
    private TextField VisiblePasswordField;

    private static final String OAUTH_CALLBACK_URL = "http://localhost/callback";
    private String userId = "";
    private String userName = "";
    private String userEmail = "";
    private String userProfilePictureUrl = "";
  //  private int id; // Ajout de l'ID de l'utilisateur

    @FXML
    void initialize() {


        // Générer et afficher le Captcha
        String captchaText = generateCaptcha();
        captchaTextLabel.setText(captchaText);
        assert EmailLabel != null : "fx:id=\"EmailLabel\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert PasswordLabel != null : "fx:id=\"PasswordLabel\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'SignIn.fxml'.";

        assert LoginMessageLabel != null : "fx:id=\"LoginMessageLabel\" was not injected: check your FXML file 'SignIn.fxml'.";
    }

    @FXML
    private void handleForgetPasswordLink(ActionEvent event) {
        try {
            // Charger la nouvelle vue depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification/ForgetPassword.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle
            Scene scene = forget.getScene();

            // Changer la scène pour afficher la nouvelle vue
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement de la vue
        }
    }

    @FXML
    void authentificateWithFacebook(MouseEvent event) {
        String authUrl = "https://www.facebook.com/v12.0/dialog/oauth?" +
                "client_id=1569098910531330" +
                "&redirect_uri=http://localhost/callback" +
                "&scope=email"; // Adjust scope as needed

        loadLoginPage(authUrl);
    }

    private void loadLoginPage(String url) {
        // Create a new WebView
        WebView newWebView = new WebView();
        WebEngine newWebEngine = newWebView.getEngine();

        // Load the login page in the new WebView
        newWebEngine.load(url);
        newWebEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (newValue.startsWith(OAUTH_CALLBACK_URL)) {
                System.out.println("OAUTH Call back");
                // Extract code from URL
                String[] parts = newValue.split("code=");
                if (parts.length > 1) {
                    String code = parts[1];
                    // Handle OAuth callback
                    handleOAuthCallback(code);
                }
            }
        });
        // Show the new WebView in a new stage
        showWebView(newWebView);
    }
    private Stage showWebView(WebView newWebView) {
        Stage stage = new Stage();
        stage.setScene(new Scene(newWebView));
        stage.show();
        return stage;
    }

    public void handleOAuthCallback(String code) {
        String appId = "1080980596556235";
        String appSecret = "00514a42f31eb9e369157b571d16d451";
        String redirectUri = "http://localhost/callback";

        try {
            String tokenEndpoint = "https://graph.facebook.com/v12.0/oauth/access_token";
            String params = String.format("client_id=%s&redirect_uri=%s&client_secret=%s&code=%s",
                    URLEncoder.encode(appId, "UTF-8"),
                    URLEncoder.encode(redirectUri, "UTF-8"),
                    URLEncoder.encode(appSecret, "UTF-8"),
                    URLEncoder.encode(code, "UTF-8"));

            URL url = new URL(tokenEndpoint + "?" + params);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the response to extract the access token
                JSONObject jsonResponse = new JSONObject(response.toString());
                String accessToken = jsonResponse.getString("access_token");

                // Now make a request to get user information using the access token
                String userInfoEndpoint = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accessToken;
                URL userInfoUrl = new URL(userInfoEndpoint);
                HttpURLConnection userInfoCon = (HttpURLConnection) userInfoUrl.openConnection();
                userInfoCon.setRequestMethod("GET");

                int userInfoResponseCode = userInfoCon.getResponseCode();
                if (userInfoResponseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader userInfoIn = new BufferedReader(new InputStreamReader(userInfoCon.getInputStream()));
                    StringBuffer userInfoResponse = new StringBuffer();
                    while ((inputLine = userInfoIn.readLine()) != null) {
                        userInfoResponse.append(inputLine);
                    }
                    userInfoIn.close();

                    // Parse the user information response
                    JSONObject userInfoJson = new JSONObject(userInfoResponse.toString());
                    userId = userInfoJson.getString("id");
                    userName = userInfoJson.getString("name");
                    userEmail = userInfoJson.getString("email");

                    // Check if the user has a profile picture
                    if (userInfoJson.has("picture")) {
                        userProfilePictureUrl = userInfoJson.getJSONObject("picture").getJSONObject("data").getString("url");
                        System.out.println("User Profile Picture URL: " + userProfilePictureUrl);
                    } else {
                        System.out.println("User has no profile picture available.");
                    }

                    System.out.println("User ID: " + userId);
                    System.out.println("User Name: " + userName);
                    System.out.println("User Email: " + userEmail);
                    Stage webViewStage = showWebView(new WebView()); // Create a dummy WebView to retrieve its stage
                    webViewStage.close();

                    handleDatabaseCheck();
                } else {
                    System.out.println("Error in fetching user information. Response code: " + userInfoResponseCode);
                }
            } else {
                System.out.println("Error in exchanging code for access token. Response code: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    private void handleDatabaseCheck() {
        if(!Objects.equals(userEmail, ""))
        {
            ServiceUser userService = new ServiceUser();
            try {
                User facebookUser = userService.selectByEmail(userEmail);
                if (facebookUser != null){
                    if(facebookUser.getRole().equals("ADHERANT"))
                    {
                        openUserInterface(facebookUser); // Passer l'objet User à la méthode
                    }else{
                        openAdminInterface();
                    }

                }else{
                    String[] parts = userName.split(" ");
                    String firstName = parts[0];
                    String lastName = "";

                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++) {
                            lastName += parts[i];
                            if (i < parts.length - 1) {
                                lastName += " ";
                            }
                        }
                    }

                    User newUser = new User(firstName,lastName,userEmail,"", Role.valueOf("ADHERANT"));
                    userService.insertUser(newUser);
                    openUserInterface(userService.selectByEmail(userEmail)); // Passer l'objet User à la méthode
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void LoginButtonOnAction(ActionEvent e) {
        // Vérifier le Captcha
        String captchaText = captchaTextLabel.getText();
        String userInput = captchaTextField.getText();
        if (!userInput.equals(captchaText)) {
            LoginMessageLabel.setText("Captcha incorrect");
            return;
        }
        String password = getPasswordFromPasswordField();
        String email = EmailLabel.getText();
        if (email == null || email.isBlank()) {
            LoginMessageLabel.setText("Email is required");
            return;
        }
        if (password == null || password.isBlank()) {
            LoginMessageLabel.setText("Password is required");
            return;
        }


        if (!email.isBlank() && !password.isBlank()) {
            ValidateLogin(password);
        }
    }

  /*  private String getPasswordFromPasswordField() {
        for (Node node : PasswordLabel.getChildren()) {
            if (node instanceof PasswordField) {
                return ((PasswordField) node).getText();
            }
        }
        return "";
    }
*/
  private String getPasswordFromPasswordField() {
      if (LoginShowPassword.isSelected()) {
          return PasswordField1.getText().trim();
      } else {
          return PasswordLabelP.getText().trim();
      }
  }

    /*
  public void ValidateLogin(String password) {

      DBConnection dbConnection = DBConnection.getInstance(); // ou toute autre méthode pour obtenir une instance de DBConnection
      Connection connect = dbConnection.getCnx();



       String verifyLogin = "SELECT id, role FROM user WHERE email=? AND password=?";
       try {
           PreparedStatement statement = connect.prepareStatement(verifyLogin);
           statement.setString(1, EmailLabel.getText());
           String hashedPassword = hashPassword(password);
           statement.setString(2, hashedPassword);

           ResultSet queryResult = statement.executeQuery();
           if (queryResult.next()) {
               int id = queryResult.getInt("id"); // Récupérer l'ID de l'utilisateur connecté
               String role = queryResult.getString("role"); // Récupérer le rôle de l'utilisateur
               if (role.equals("ADMIN")) {
                   LoginMessageLabel.setText("Connected as Admin");
                   openAdminInterface();
               } else if (role.equals("ADHERANT")) {
                   LoginMessageLabel.setText("Connected as Adherant");
                   openUserInterface(id); // Appel de la méthode avec l'ID de l'utilisateur connecté
               } else {
                   LoginMessageLabel.setText("Invalid role");

               }

           } else {

               return null;
           }
       } catch (SQLException | NoSuchAlgorithmException ex) {
           ex.printStackTrace();
       }
   }
*/
public User ValidateLogin(String password) {
    DBConnection dbConnection = DBConnection.getInstance(); // ou toute autre méthode pour obtenir une instance de DBConnection
    Connection connect = dbConnection.getCnx();

    try {
        // Hasher le mot de passe entré par l'utilisateur
         String hashedPassword = hashPassword(password);

        // Vérifier si l'utilisateur existe avec les identifiants fournis
        String verifyLogin = "SELECT id, nom, prenom, email,password, role, image_user FROM user WHERE email=? AND password=?";
        PreparedStatement statement = connect.prepareStatement(verifyLogin);
        statement.setString(1, EmailLabel.getText());
        // Utiliser le mot de passe du champ PasswordField ou PasswordField1 en fonction de l'état de LoginShowPassword

        if (LoginShowPassword.isSelected()) {
            hashedPassword = hashPassword(PasswordField1.getText().trim());
        } else {
            hashedPassword = hashPassword(PasswordLabelP.getText().trim());
        }
        statement.setString(2, hashedPassword);


        ResultSet queryResult = statement.executeQuery();
        if (queryResult.next()) {
            // Utilisateur trouvé, récupérer ses informations
            int id = queryResult.getInt("id");
            String nom = queryResult.getString("nom");
            String prenom = queryResult.getString("prenom");
            String passwordData = queryResult.getString("password");
            String email = queryResult.getString("email");
            String role = queryResult.getString("role");
            String image_user = queryResult.getString("image_user");

            // Redirection en fonction du rôle de l'utilisateur
            if (role.equals("ADMIN")) {
                LoginMessageLabel.setText("Connected as Admin");
                openAdminInterface();
            } else if (role.equals("ADHERANT")) {
                LoginMessageLabel.setText("Connected as Adherant");

                openUserInterface(new User(id, nom, prenom, email, passwordData, Role.valueOf(role),image_user)); // Passer l'objet User à la méthode
            } else {
                LoginMessageLabel.setText("Invalid role");
            }

            // Retourner l'objet User pour une utilisation ultérieure si nécessaire
            return new User(id, nom, prenom, email, role);
        } else {
            // Aucun utilisateur trouvé avec ces identifiants
            LoginMessageLabel.setText("No user with this account");
            return null;
        }
    } catch (SQLException | NoSuchAlgorithmException ex) {
        ex.printStackTrace();
        return null;
    }
}


    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    @FXML
    private void openUserInterface(User user) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            UserFrontHome controller = loader.getController();
            if (user != null) {
                controller.setUserName(user.getNom());
                controller.setUser(user);
                // Définir l'utilisateur dans le contrôleur
                System.out.println("envoyer avec succes");
            } else {
                // Gérer le cas où l'objet user est nul
                System.err.println("User object is null");
                return;
            }
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            Stage currentStage = (Stage) LoginMessageLabel.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
    @FXML
    private void openUserInterface(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/UserFrontHome.fxml"));
            Parent parent = loader.load();

            // Accéder au contrôleur de la nouvelle fenêtre
            UserFrontHome controller = loader.getController();
            controller.setId(userId); // Définir l'ID de l'utilisateur dans le contrôleur

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            Stage currentStage = (Stage) LoginMessageLabel.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
*/
    @FXML
    private void openAdminInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/userBackHome.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("UserBackHome");
            stage.setScene(scene);
            Stage currentStage = (Stage) LoginMessageLabel.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    private String generateCaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // Configure Kaptcha properties as needed
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha.createText();
    }
    /////////////////////////////////////////*    SHOW Password Login    */////////////////////////////////////////////////////////////////////////////////////
    public void ShowPassword(){
        if(LoginShowPassword.isSelected()){
            PasswordField1.setText( PasswordLabelP.getText());
            PasswordField1.setVisible(true);
            PasswordLabelP.setVisible(false);
        }
        else {
            PasswordLabelP.setText(PasswordField1.getText());
            PasswordField1.setVisible(false);
            PasswordLabelP.setVisible(true);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////






}



