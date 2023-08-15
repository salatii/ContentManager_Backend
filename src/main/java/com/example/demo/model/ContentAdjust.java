package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ContentAdjust {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    @Column(length = 100000)
    private String image;
    @Column(length = 100000)
    private String content;

    // TODO meta
    Pair<String, String> keywords;
    Pair<String, String> translation;
    Pair<String, String> summary;
    Pair<String, String> grammarly;

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
