package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ControllerTest {
    // Add any necessary dependencies here.
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("1")
    public void test1_postContent() throws Exception {
        String jsonContent = "{\"title\":\"Sample Title\",\"image\":\"Sample Image\",\"content\":\"Sample Content\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("2")
    public void test2_getAllContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("3")
    public void test3_getContentById() throws Exception {
        int itemId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/content/" + itemId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("4")
    public void test4_updateContent() throws Exception {
        String jsonContent = "{\"title\":\"Sample Update\",\"image\":\"Sample Update\",\"content\":\"Sample Update\"}";
        int itemId = 1;
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/content/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("5")
    public void test5_createMultiple() throws Exception {
        String jsonContent = "[{\"title\":\"Sample Title\",\"image\":\"Sample Image\",\"content\":\"Sample Content\"}, {\"title\":\"Sample Title\",\"image\":\"Sample Image\",\"content\":\"Sample Content\"}]";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/content/create-multiple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("6")
    public void test6_deleteContent() throws Exception {
        int itemId = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/content/" + itemId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("7")
    public void test7_ai() throws Exception {
        String jsonAi = "{\"task\":\"translation\",\"context\":\"Hallo, wie geht es dir?\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/ai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAi))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
    }
}