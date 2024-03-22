package controllers.Front.EventsReclamation;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CalendarActivity;

public class EventDetailsWindow extends Stage {
    public EventDetailsWindow(CalendarActivity event) {
        VBox root = new VBox();
        // Créez et configurez les éléments d'interface utilisateur pour afficher les détails de l'événement
        Label eventNameLabel = new Label("Event Name: " + event.getEventName());
        Label eventDateLabel = new Label("Event Date: " + event.getDate());
        // Ajoutez les éléments d'interface utilisateur au conteneur racine
        root.getChildren().addAll(eventNameLabel, eventDateLabel);
        // Configurez la scène
        Scene scene = new Scene(root, 300, 200);
        setScene(scene);
    }
}
