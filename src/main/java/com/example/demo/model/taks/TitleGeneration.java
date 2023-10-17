package com.example.demo.model.taks;

public class TitleGeneration extends Task {
    String type;
    String tonality;

    public TitleGeneration(String text, String type, String tonality) {
        super(text);
        this.task = "title_generation";
        this.type = type;
        this.tonality = tonality;
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{PLACEHOLDER_TEXT}", text)
                .replace("{PLACEHOLDER_TYPE}", type)
                .replace("{PLACEHOLDER_TONALITY}", tonality);
        return replacedText;
    }
}
