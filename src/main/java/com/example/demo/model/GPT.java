package com.example.demo.model;

import com.example.demo.dao.AiAPIException;
import com.example.demo.model.taks.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GPT {
    private static String url = "https://api.openai.com/v1/chat/completions";
    private static String apiKey = "enter api key here";
    private static  String model = "gpt-3.5-turbo-16k-0613"; // current model of chatgpt api

    public static String performAI(String task, String context) throws JSONException {
        Task prompt = null;
        JSONObject promptJSON = loadJson("src/main/resources/mandant.json");
        JSONObject properties = null;
        try {
            properties = promptJSON.getJSONObject("properties");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (task) {
            case "translation":
                System.out.println("Performing translation...");
                prompt = new Translate(context, properties.get("srcLng").toString(), properties.get("trgLng").toString());
                break;
            case "summarization":
                System.out.println("Performing summarization...");
                prompt = new Summarize(context,  Integer.parseInt(properties.get("summary").toString()));
                break;
            case "spellchecking":
                System.out.println("Performing spellchecking...");
                prompt = new Spelling(context);
                break;
            case "keywords":
                System.out.println("Extracting keywords...");
                prompt = new ExtractKeywords(context, Integer.parseInt(properties.get("keywords").toString()));
                break;
            case "rewording":
                System.out.println("Performing rewording...");
                prompt = new Rewording(context);
                break;
            case "title":
                System.out.println("Performing generate title...");
                prompt = new TitleGeneration(context, properties.get("type").toString(), properties.get("tonality").toString());
                break;
            case "content":
                System.out.println("Performing generate content...");
                prompt = new ContentGeneration(context, properties.get("type").toString(), properties.get("tonality").toString(),  Integer.parseInt(properties.get("generation").toString()));
                break;
            default:
                System.out.println("Invalid action.");
                throw new AiAPIException("Invalid action");
        }
        String promptString =  prompt.prompting();

        JSONObject fewshotJSON = loadJson("src/main/resources/few-shot.json");
        JSONArray fewshotArray = fewshotJSON.getJSONArray(task);
        // Den neuen Eintrag hinzufügen
        JSONObject newEntry = new JSONObject();
        newEntry.put("role", "user");
        newEntry.put("content", promptString);
        fewshotArray.put(newEntry);

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\":" + fewshotArray.toString() + "}";
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

    private static JSONObject loadJson(String filename) {
        JSONObject obj = null;
        try {
            obj = parseJSONFile(filename);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
}
