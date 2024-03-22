package services;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapGeo {

    private WebView webView;
    private WebEngine webEngine;

    public MapGeo() {
        webView = new WebView();
        webEngine = webView.getEngine();

        webEngine.load(getClass().getResource("/Map/map.html").toExternalForm());
    }


    public void addMarker(double latitude, double longitude) {
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Call the addMarker function with the desired latitude and longitude
                webEngine.executeScript("addMarker("+latitude+", "+longitude+");");
            }
        });
    }

    public void addMarkerBlue(double latitude, double longitude) {
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Call the addMarker function with the desired latitude and longitude
                webEngine.executeScript("addMarkerBlue("+latitude+", "+longitude+");");
            }
        });
    }
    public void addMarkerYellow(double latitude, double longitude) {
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Call the addMarker function with the desired latitude and longitude
                webEngine.executeScript("addMarkerYellow("+latitude+", "+longitude+");");
            }
        });
    }

    public WebView getWebView() {
        return webView;
    }
}