package com.giftthedeveloper.UrlShortener.Url;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("api")
public class ShortenedUrlController {

    public static final String BASE_URL= "http://localhost/";

    @Autowired
    ShortenedUrlRepository repository;
    
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/create")
    public ResponseEntity<ShortenedUrl> createShortUrl (@RequestBody() ShortenedUrl instance) {
        ShortenedUrl existingUrl = repository.findByOriginalUrl(instance.getOriginalUrl());
        if (existingUrl != null ) {
            return ResponseEntity.ok(existingUrl);
        } else {
            String newShortUrl = generateShortUrl(instance.getOriginalUrl());
            instance.setShortUrl(newShortUrl);
            System.out.println(instance);
            repository.save(instance);
            return ResponseEntity.ok(instance);
        }
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl) {
        // System.out.println(shortUrl);
        ShortenedUrl shortenedUrl = repository.findByShortUrl(BASE_URL + shortUrl);
        // System.out.println(shortenedUrl);
        if (shortenedUrl != null) {
            return new RedirectView(shortenedUrl.getOriginalUrl());
        } else {
            return new RedirectView("http://localhost/error");
        }
    }

    @GetMapping("/all")
    public List<ShortenedUrl> getAllUrls() {
        return repository.findAll();
    }

    @DeleteMapping("/delete/{shortUrl}")
    public String deleteShortUrl(@PathVariable String shortUrl) {
        System.out.println(shortUrl);
        ShortenedUrl shortenedUrl = repository.findByShortUrl(BASE_URL + shortUrl);
        System.out.println(shortenedUrl);
        if (shortenedUrl != null ) {
            repository.delete(shortenedUrl);
            return "Url sucessfully deleted";
        } else {
            return "Url not found";
        }
    }
    

    public String generateShortUrl(String originalUrl) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(originalUrl.getBytes());
        String encoded = Base64.getEncoder().encodeToString(hash);
        // Take the first 6 characters as the short URL
        return BASE_URL + encoded.substring(0, 6);
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null; // Handle error
    }
}
}
