package com.example.demo.model.taks;

import com.example.demo.model.Content;

public class ContentGeneration extends Task {

    String type;
    String tonality;
    int wordCnt;

    public ContentGeneration(String text, String type, String tonality, int cnt) {
        super(text);
        this.task = "content_generation";
        this.type = type;
        this.tonality = tonality;
        this.wordCnt = cnt;
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{PLACEHOLDER_TEXT}", text)
                .replace("{PLACEHOLDER_TYPE}", type)
                .replace("{WORD_COUNT}", String.valueOf(wordCnt))
                .replace("{PLACEHOLDER_TONALITY}", tonality);
        return replacedText;
    }
}
