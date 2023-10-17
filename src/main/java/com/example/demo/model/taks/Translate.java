package com.example.demo.model.taks;

import org.json.JSONException;

public class Translate extends Task {
    String srcLng;
    String trgLng;

    public Translate(String text, String source, String target) {
        super(text);
        this.task = "translation";
        this.srcLng = source;
        this.trgLng = target;
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{SRC_LNG}", srcLng)
                .replace("{TRG_LNG}", trgLng)
                .replace("{PLACEHOLDER_TEXT}", text);
        return replacedText;
    }
}
