package services;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Weather {

    private OkHttpClient client;
    private double temperatureCelsius;
    private double humidity;
    private String conditionText;
    private String weatherIcon;

    public Weather() {
        this.client = new OkHttpClient();
    }
    public Weather(double temperatureCelsius, double humidity, String conditionText, String weatherIcon) {
        this.temperatureCelsius = temperatureCelsius;
        this.humidity = humidity;
        this.conditionText = conditionText;
        this.weatherIcon = weatherIcon;
    }


    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void getWeather() throws IOException {
        Request request = new Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/current.json?q=Tunis")
                .get()
                .addHeader("X-RapidAPI-Key", "fe4429ae05mshe9b08fc10a6eaefp1e30c9jsn26fb0b6e7731")
                .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject json = new JSONObject(response.body().string());

        JSONObject current = json.getJSONObject("current");
        this.temperatureCelsius = current.getDouble("temp_c");
        this.humidity = current.getDouble("humidity");
        this.conditionText = current.getJSONObject("condition").getString("text");
        this.weatherIcon = current.getJSONObject("condition").getString("icon");

    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperatureCelsius=" + temperatureCelsius +
                ", humidity=" + humidity +
                ", conditionText='" + conditionText + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                '}';
    }
}

