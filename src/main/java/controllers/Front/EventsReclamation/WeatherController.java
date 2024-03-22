package controllers.Front.EventsReclamation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.Weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label conditionText;

    @FXML
    private Label humidity;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label temperatureCelsius;

    @FXML
    private Label temperatureLabel;

    @FXML
    private ImageView weatherIcon;

    @FXML
    private ImageView weatherIconImageView;

    @FXML
    void initialize() {
        assert conditionLabel != null : "fx:id=\"conditionLabel\" was not injected: check your FXML file 'weather1.fxml'.";
        assert conditionText != null : "fx:id=\"conditionText\" was not injected: check your FXML file 'weather1.fxml'.";
        assert humidity != null : "fx:id=\"humidity\" was not injected: check your FXML file 'weather1.fxml'.";
        assert humidityLabel != null : "fx:id=\"humidityLabel\" was not injected: check your FXML file 'weather1.fxml'.";
        assert temperatureCelsius != null : "fx:id=\"temperatureCelsius\" was not injected: check your FXML file 'weather1.fxml'.";
        assert temperatureLabel != null : "fx:id=\"temperatureLabel\" was not injected: check your FXML file 'weather1.fxml'.";
        assert weatherIcon != null : "fx:id=\"weatherIcon\" was not injected: check your FXML file 'weather1.fxml'.";
        assert weatherIconImageView != null : "fx:id=\"weatherIconImageView\" was not injected: check your FXML file 'weather1.fxml'.";
        Weather weather = new Weather();
        try {
            weather.getWeather();
            InputStream inputStream = new URL("https:"+weather.getWeatherIcon()).openStream();
            Image image = new Image(inputStream);
            weatherIcon.setImage(image);
            temperatureCelsius.setText(String.valueOf(weather.getTemperatureCelsius()));
            conditionText.setText(weather.getConditionText());
            humidity.setText("Today's humidity is "+weather.getHumidity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
