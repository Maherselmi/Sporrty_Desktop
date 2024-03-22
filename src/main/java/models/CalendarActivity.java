package models;

import javafx.scene.paint.Color;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String eventName;
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    public CalendarActivity(ZonedDateTime date, String eventName) {
        this.date = date;
        this.eventName = eventName;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "CalendarActivity{" +
                "date=" + date +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}

