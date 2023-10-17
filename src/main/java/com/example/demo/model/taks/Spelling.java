package com.example.demo.model.taks;

public class Spelling extends Task {

    public Spelling(String text) {
        super(text);
        this.task = "spellchecking";
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{PLACEHOLDER_TEXT}", text);
        return replacedText;
    }
}
