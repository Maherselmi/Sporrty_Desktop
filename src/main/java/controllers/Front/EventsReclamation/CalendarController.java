package controllers.Front.EventsReclamation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import models.CalendarActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.evenements;
import services.ServiceEvenement;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    // Define a palette of pastel colors
    private final String[] pastelColors = {"#FFB6C1", "#FFD700", "#87CEEB", "#98FB98", "#FFA07A", "#9370DB", "#F08080", "#90EE90", "#FF69B4", "#ADD8E6"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        calendar.getChildren().clear();
        drawCalendar();

    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    public void addEvent(CalendarActivity event, double rectangleWidth, double rectangleHeight) {
        // Create a label for the event name
        Text eventNameText = new Text(event.getEventName());
        eventNameText.setFill(Color.web("#A6E3E9")); // Example pastel color

        // Create a StackPane to hold the event label
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(rectangleWidth, rectangleHeight);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        stackPane.getChildren().addAll(rectangle, eventNameText);

        // Add the StackPane to the calendar display
        calendar.getChildren().add(stackPane);

    }


    private void showEventDetailsDialog(String eventName) {
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            evenements event = serviceEvenement.searchByName(eventName);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event Details");
            alert.setHeaderText(null);

            // Set the content text of the alert to display event details
            alert.setContentText("Event Name: " + event.getNom() + "\n"
                    // Add more details here as needed
            );

            // Show the dialog
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Create a new alert dialog

    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear());
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

   /* private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    // On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            CalendarActivity activity = calendarActivities.get(k);
            String color = getColorForEvent(activity);
            Label label = new Label(activity.getEventName());
            label.setStyle("-fx-background-color: " + color + "; -fx-padding: 3px; -fx-border-radius: 5px;");
            calendarActivityBox.getChildren().add(label);
            label.setOnMouseClicked(mouseEvent -> {
                // On Label clicked
                System.out.println(activity.getEventName());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        // calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }*/
   private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
       VBox calendarActivityBox = new VBox();

       // Pour stocker les noms d'événements déjà ajoutés
       Set<String> addedEventNames = new HashSet<>();

       for (int k = 0; k < calendarActivities.size(); k++) {
           if (k >= 2) {
               Text moreActivities = new Text("...");
               calendarActivityBox.getChildren().add(moreActivities);
               moreActivities.setOnMouseClicked(mouseEvent -> {
                   // On ... click print all activities for given date
                   System.out.println(calendarActivities);
               });
               break;
           }
           CalendarActivity activity = calendarActivities.get(k);
           String color = getColorForEvent(activity);
           Label label = new Label(activity.getEventName());
           label.setStyle("-fx-background-color: " + color + "; -fx-padding: 3px; -fx-border-radius: 5px;");

           // Vérifier si le nom de l'événement a déjà été ajouté
           if (!addedEventNames.contains(activity.getEventName())) {
               calendarActivityBox.getChildren().add(label);
               addedEventNames.add(activity.getEventName()); // Ajouter le nom à l'ensemble
           }

           label.setOnMouseClicked(mouseEvent -> {
               // On Label clicked
               showEventDetailsDialog(activity.getEventName());
           });
       }
       calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
       calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
       calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
       // calendarActivityBox.setStyle("-fx-background-color:GRAY");
       stackPane.getChildren().add(calendarActivityBox);
   }



    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        LocalDate localDate = dateFocus.toLocalDate(); // Convertir ZonedDateTime en LocalDate
        int year = localDate.getYear(); // Récupérer l'année
        int month = localDate.getMonthValue(); // Récupérer le mois

        // Récupérer les événements pour le mois spécifié
        List<evenements> events = null;
        try {
            events = ServiceEvenement.getInstance().getEventsForMonth(year, month);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (evenements event : events) {
            LocalDate eventDate = event.getDate().toLocalDate(); // Convertir java.sql.Date en LocalDate
            int dayOfMonth = eventDate.getDayOfMonth();

            // Create a ZonedDateTime object for the event date at midnight
            ZonedDateTime eventDateTime = eventDate.atStartOfDay(dateFocus.getZone());

            // Create a CalendarActivity object using event information
            CalendarActivity calendarActivity = new CalendarActivity(eventDateTime, event.getNom());

            // Add the CalendarActivity to the map
            if (!calendarActivityMap.containsKey(dayOfMonth)) {
                calendarActivityMap.put(dayOfMonth, new ArrayList<>(List.of(calendarActivity)));
            } else {
                List<CalendarActivity> activities = calendarActivityMap.get(dayOfMonth);
                activities.add(calendarActivity);
                calendarActivityMap.put(dayOfMonth, activities);
            }
        }

        return calendarActivityMap;
    }

    private String getColorForEvent(CalendarActivity activity) {
        // Calculer un index basé sur le nom de l'événement
        int index = Math.abs(activity.getEventName().hashCode()) % pastelColors.length;

        // Retourner la couleur correspondante dans la palette
        return pastelColors[index];
    }
}
