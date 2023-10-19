package com.example.demo;

import com.example.demo.model.GPT;
import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GptTest {
    private GPT gpt;

    @Test
    public void testPerformAITranslation() throws JSONException {
        String task = "translation";
        String context = "Das ist ein Beispiel.";
        String expectedResponse = "That is an example.";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Verify that the expected translation is returned
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testPerformAISummarization() throws JSONException {
        String task = "summarization";
        String context = "Der Eiffelturm in Paris, Frankreich, ist ein Wahrzeichen der französischen Architektur und eine beliebte Touristenattraktion. Er wurde 1889 als Herzstück der Weltausstellung von 1889 errichtet und ist seitdem zu einem der bekanntesten Wahrzeichen der Welt geworden.";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Verify that the expected summary is returned
        assertTrue(context.length() > actualResponse.length());
        assertTrue(countWords(actualResponse) > 5 && countWords(actualResponse) < 20);
        //assertTrue(actualResponse.charAt(actualResponse.length()-1) == '.');
    }

    @Test
    public void testPerformAIKeywords() throws JSONException {
        String task = "keywords";
        String context = "Hello, I'd like to order a pizza with salami topping.";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Mit split das Leerzeichen als Trennzeichen verwenden, um den String in Wörter aufzuteilen.
        String[] wordsArray = actualResponse.split(" ");

        // Verify that the expected translation is returned
        assertEquals(wordsArray.length, 3);
    }

    @Test
    public void testPerformAISpellchecking() throws JSONException {
        String task = "spellchecking";
        String context = "Wie aalt bist duh?";
        String expected = "Wie alt bist du?";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Verify that the expected translation is returned
        assertEquals(actualResponse, expected);
    }

    @Test
    public void testPerformAIRewording() throws JSONException {
        String task = "rewording";
        String context = "Der Klimawandel ist ein dringendes globales Problem, das unsere Zukunft beeinflusst.";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Verify that the expected translation is returned
        assertNotEquals(actualResponse, context);
    }

    @Test
    public void testPerformAITitleGeneration() throws JSONException {
        String task = "title";
        String context = "Harry Potter";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Den String anhand des Punkts aufteilen, um die Sätze zu erhalten.
        String[] sentences = actualResponse.split("\n");

        // Verify that the expected translation is returned
        assertEquals(sentences.length, 3);
    }

    @Test
    public void testPerformAIContentGeneration() throws JSONException {
        String task = "content";
        String context = "Harry Potter";

        // Mocking the GPT API call
        String actualResponse = gpt.performAI(task, context);

        // Den String anhand des Punkts aufteilen, um die Sätze zu erhalten.
        String[] sentences = actualResponse.split("\n");

        // Verify that the expected translation is returned
        assertEquals(sentences.length, 3);

    }

    public static int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0; // Wenn der Text null oder leer ist, gibt es keine Wörter.
        }
        // Verwenden Sie reguläre Ausdrücke, um Wörter zu identifizieren (Wörter sind durch Leerzeichen getrennt).
        String[] words = text.split("\\s+");
        // Die Länge des Arrays "words" entspricht der Anzahl der Wörter.
        return words.length;
    }

    // TODO test for request limit java.io.IOException: Server returned HTTP response code: 503 for URL
}
