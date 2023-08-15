package com.example.demo.model;

import com.example.demo.dao.AiAPIException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GPT {
    public static String performAI(String prompt, String context) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "enter here api key";
        String model = "gpt-3.5-turbo-16k-0613"; // current model of chatgpt api

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            //TODO schauen wie prompt und context am besten funtionieren also mit : oder anders
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + " " + context + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // returns the extracted contents of the response.
            return extractContentFromResponse(response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    private static String extractContentFromResponse(String response) {
        //TODO remove "" from text
        try {
            JSONObject jsonObject = new JSONObject(response);
            // Zugriff auf die 'choices' Eigenschaft und deren Inhalt
            JSONArray choicesArray = jsonObject.getJSONArray("choices");

            // Zugriff auf den 'message' Inhalt innerhalb der ersten 'choices'
            JSONObject messageObject = choicesArray.getJSONObject(0).getJSONObject("message");
            String messageContent = messageObject.getString("content");
            //messageContent = messageContent.substring(1, messageContent.length() - 1);
            return messageContent;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new AiAPIException("Error while parsing response from openai");
        }
    }
}
