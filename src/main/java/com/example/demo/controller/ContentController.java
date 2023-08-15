package com.example.demo.controller;

import com.example.demo.dao.ContentCreationException;
import com.example.demo.dao.ContentRepository;
import com.example.demo.model.AiRequest;
import com.example.demo.model.Content;
import com.example.demo.model.GPT;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentController {

    @Autowired
    private ContentRepository contentRepository;

    @GetMapping(path = "/content")
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @GetMapping("/content/{id}")
    public Content getContentById(@PathVariable Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + id));
    }

    @PostMapping(path = "/content")
    public Content createContent(@RequestBody Content content) {
        return contentRepository.save(content);
    }

    @PostMapping("/content/create-multiple")
    @ResponseStatus(HttpStatus.OK)
    public List<Content> createMultipleContents(@RequestBody List<Content> createContents) {
        for (Content content : createContents) {
            if (content.getTitle() == null || content.getImage() == null || content.getContent() == null) {
                throw new IllegalArgumentException("Title, image, and content are required.");
            }
        }
        return contentRepository.saveAll(createContents);
    }

    @PutMapping("/content/{id}")
    public Content updateContent(@PathVariable Long id, @RequestBody Content contentDetails) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + id));

        content.setTitle(contentDetails.getTitle());
        content.setImage(contentDetails.getImage());
        content.setContent(contentDetails.getContent());

        return contentRepository.save(content);
    }

    @DeleteMapping("/content/{id}")
    public void deleteContent(@PathVariable Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + id));

        contentRepository.delete(content);
    }

    @GetMapping(path = "/rss")
    public List<Content> fillContentWithRSS() {
        List<Content> contentList = new ArrayList<>();

        try {
            URL feedUrl = new URL("https://www.computerbild.de/rss/35011529.xml");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                String title = entry.getTitle();
                String image = entry.getEnclosures().get(0).getUrl();
                String content = entry.getDescription().getValue();
                Content contentObj = new Content(title, image, content);
                contentList.add(contentObj);
            }
            return createMultipleContents(contentList);
        } catch (Exception e) {
            throw new ContentCreationException("Failed to create content: " + e.getMessage());
        }
    }

    @PostMapping(path = "/ai")
    public String performeAi(@RequestBody AiRequest request) {
        return GPT.performAI(request.getPrompt(), request.getContext());
    }
}
