package controllers.Front.StockMateriel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;
public class CaptchaController {

    @FXML
    private ImageView captchaImageView;

    @FXML
    private TextField captchaInputField;

    private String captchaText;

    @FXML
    private void initialize() {
        refreshCaptcha();
    }

    @FXML
    private void refreshCaptcha() {
        generateCaptcha();
        captchaImageView.setImage(generateCaptchaImage());
    }

    @FXML
    private void verifyCaptcha() {
        String userInput = captchaInputField.getText().trim();
        if (userInput.equals(captchaText)) {
            System.out.println("Captcha verified!");
            // Add your logic for successful verification here
        } else {
            System.out.println("Captcha verification failed!");
            // Add your logic for failed verification here
        }
    }

    // Method to generate a random captcha text
    private void generateCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) { // Captcha length = 6
            captcha.append((char) (random.nextInt(26) + 'A')); // Random uppercase letter
        }
        captchaText = captcha.toString();
    }

    // Method to generate an image representing the captcha text
    private Image generateCaptchaImage() {
        // Code to generate an image from the captcha text (You can use libraries like Kaptcha or create your own)
        // Here, we'll just return a blank image for simplicity
        return new Image(getClass().getResourceAsStream("blank_image.png"));
    }

    public static void main(String[] args) {

    }
}

