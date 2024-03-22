package services;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class ChatGPTClient {

    private static final String API_URL = "https://chatgpt-api8.p.rapidapi.com/";
    private static final String RAPID_API_KEY = "fe4429ae05mshe9b08fc10a6eaefp1e30c9jsn26fb0b6e7731";
    private static final String RAPID_API_HOST = "chatgpt-api8.p.rapidapi.com";

    private OkHttpClient client;

    public ChatGPTClient() {
        this.client = new OkHttpClient();
    }

    public String sendMessage(String message) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[\r\n" +
                "    {\r\n" +
                "        \"content\": \"" + message + "\",\r\n" +
                "        \"role\": \"user\"\r\n" +
                "    }\r\n" +
                "]");
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", RAPID_API_KEY)
                .addHeader("X-RapidAPI-Host", RAPID_API_HOST)
                .build();

        Response response = client.newCall(request).execute();

        JSONObject jsonObject = new JSONObject(response.body().string());

        String responseTextBody = jsonObject.getString("text");
        return responseTextBody;
    }
}

