package com.example.demo;

import com.example.demo.model.AiRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AiRequestTest {

    @Test
    public void testAiRequestConstructorAndGetters() {
        // Erstellen Sie eine AiRequest-Instanz
        AiRequest aiRequest = new AiRequest("SomeTask", "SomeContext");

        // Überprüfen Sie, ob die Konstruktorwerte korrekt gesetzt wurden
        assertEquals("SomeTask", aiRequest.getTask());
        assertEquals("SomeContext", aiRequest.getContext());
    }

    @Test
    public void testAiRequestSetters() {
        // Erstellen Sie eine AiRequest-Instanz
        AiRequest aiRequest = new AiRequest("Task1", "Context1");

        // Verwenden Sie die Setter-Methoden, um Werte zu ändern
        aiRequest.setTask("Task2");
        aiRequest.setContext("Context2");

        // Überprüfen Sie, ob die Setter korrekt funktionieren
        assertEquals("Task2", aiRequest.getTask());
        assertEquals("Context2", aiRequest.getContext());
    }
}