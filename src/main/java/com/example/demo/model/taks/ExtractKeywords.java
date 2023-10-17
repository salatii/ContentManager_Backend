package com.example.demo.model.taks;

public class ExtractKeywords extends Task {
    int keywordsCnt;

    public ExtractKeywords(String text, int cnt) {
        super(text);
        this.keywordsCnt = cnt;
        this.task = "keywords";
    }

    @Override
    public String prompting() {
        String placeholder = getPrompt(getTask());
        String replacedText = placeholder
                .replace("{KEYWORDS_COUNT}", String.valueOf(keywordsCnt))
                .replace("{PLACEHOLDER_TEXT}", text);

        return replacedText;
    }
}
