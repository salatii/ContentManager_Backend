package com.example.demo.model.taks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Task {
    String text;
    String task;

    public Task(String text) {
        this.text = text;
        this.task = "default";
    }

    public String getText() {
        return text;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPrompt(String task) {
        JSONObject obj = loadJson();
        String prompt = "";
        try {
            prompt = obj.getJSONObject("prompts").get(task).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prompt;
    }

    private JSONObject loadJson() {
        JSONObject obj = null;
        String filename = "src/main/resources/mandant.json";
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

    public abstract String prompting();

}
