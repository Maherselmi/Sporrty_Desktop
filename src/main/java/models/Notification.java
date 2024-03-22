package models;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private static VBox notificationBox = new VBox(); // Conteneur pour les notifications
    private static ScrollPane scrollPane = new ScrollPane(notificationBox); // ScrollPane pour les notifications
    private static List<String> notifications = new ArrayList<>(); // Liste des notifications

    // Méthode pour ajouter une notification
    public static void addNotification(String message) {
        notifications.add(message);
        refreshNotifications(); // Rafraîchir l'affichage des notifications
    }

    // Méthode pour rafraîchir l'affichage des notifications
    private static void refreshNotifications() {
        notificationBox.getChildren().clear(); // Effacer les anciennes notifications
        notifications.forEach(message -> {
            Label label = new Label(message);
            label.setStyle("-fx-background-color: lightgrey; -fx-padding: 5px;");
            notificationBox.getChildren().add(label); // Ajouter la nouvelle notification
        });
    }

    // Méthode pour obtenir le ScrollPane des notifications
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }
}
