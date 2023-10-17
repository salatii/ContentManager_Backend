package com.example.demo.model.taks;

public class Summarize extends Task {
    int wordCnt;

    public Summarize(String text, int cnt) {
        super(text);
        wordCnt = cnt;
        this.task = "summarization";
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{Summary_COUNT}", String.valueOf(wordCnt))
                .replace("{PLACEHOLDER_TEXT}", text);
        return replacedText;
    }
}
