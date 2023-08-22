package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    @Column(length = 100000)
    private String image;
    @Column(length = 100000)
    private String content;

    // TODO meta
    @Column(length = 1000)
    private String keywords = "";
    @Column(length = 100000)
    private String translation = "";
    @Column(length = 100000)
    private String summary = "";

    public Content(String title, String image, String content) {
        this.title = title;
        this.image = image;
        this.content = content;
    }

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

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
