package com.example.demo;

import com.example.demo.model.Content;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentTest {
    private static Content content;

    @BeforeAll
    public static void setUp() {
        content = new Content("Test Title", "Test Image", "Test Content");
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test Title", content.getTitle());
    }

    @Test
    public void testSetTitle() {
        content.setTitle("New Title");
        assertEquals("New Title", content.getTitle());
    }

    @Test
    public void testGetContent() {
        assertEquals("Test Content", content.getContent());
    }

    @Test
    public void testSetContent() {
        content.setContent("New Content");
        assertEquals("New Content", content.getContent());
    }

    @Test
    public void testGetImage() {
        assertEquals("Test Image", content.getImage());
    }

    @Test
    public void testSetImage() {
        content.setImage("New Image");
        assertEquals("New Image", content.getImage());
    }

    // You can add similar tests for other properties like keywords, translation, and summary
}
