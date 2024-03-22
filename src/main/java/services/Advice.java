package services;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Advice {
    private final OkHttpClient httpClient;

    public Advice() {
        // Initialize HTTP client
        httpClient = new OkHttpClient();
    }

    public String callAdviceAPI() throws IOException {
        String url = "https://api.adviceslip.com/advice";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            String advice = jsonObject.getJSONObject("slip").getString("advice");

            System.out.println(advice);
            // Extract the advice from the response
            return advice;
        }
    }

}
