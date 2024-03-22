package services;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChatBot {

    private static final int TYPING_SPEED_MILLISECONDS = 100;
    private final TextArea responseLabel;

    @FXML
    private TextArea chatBox;
    private int typingIndex = 0;

    public ChatBot(){
        AnchorPane root = new AnchorPane();

// Load and display the GIF of the talking bot as background
        Image botImage = new Image(getClass().getResourceAsStream("/assets/gif/bot-emotions-principle.gif"));
        ImageView botImageView = new ImageView(botImage);

// Create a bubble-like appearance for the text response
        responseLabel = new TextArea();
        responseLabel.setEditable(false);
        responseLabel.setWrapText(true);
        responseLabel.setMaxHeight(100);
        responseLabel.setMaxWidth(200); // Set maximum width for the response label
        responseLabel.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-background-radius: 15px;"); // Apply CSS styling for bubble appearance

// Add both the bot image and response label to the root AnchorPane
        root.getChildren().addAll(botImageView, responseLabel);

// Position the responseLabel within the AnchorPane
        AnchorPane.setLeftAnchor(responseLabel, 500.0); // Set left anchor
        AnchorPane.setTopAnchor(responseLabel, 150.0); // Set top anchor

// Set up the scene
        Scene scene = new Scene(root, botImage.getWidth(), botImage.getHeight()); // Set scene size to match the image size
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Chat Bot");
        stage.show();
    }
    public void startTypingAnimation(String text) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(TYPING_SPEED_MILLISECONDS), event -> {
            if (typingIndex <= text.length()) {
                responseLabel.setText(text.substring(0, typingIndex++));
            }
        }));
        timeline.setCycleCount(text.length() + 1);
        timeline.play();
    }
}
