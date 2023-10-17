package com.example.demo.model.taks;

public class Rewording extends Task{
    public Rewording(String text) {
        super(text);
        this.task = "rewording";
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{PLACEHOLDER_TEXT}", text);
        return replacedText;
    }
}
