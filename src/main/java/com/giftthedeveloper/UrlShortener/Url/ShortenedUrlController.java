package com.giftthedeveloper.UrlShortener.Url;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@RestController
@RequestMapping("api")
public class ShortenedUrlController {

    @Autowired
    ShortenedUrlRepository repository;

    @PostMapping("/create")
    public ResponseEntity<ShortenedUrl> createShortUrl (@RequestBody() ShortenedUrl instance) {
        ShortenedUrl existingUrl = repository.findByOriginalUrl(instance.getOriginalUrl());
        if (existingUrl != null ) {
            return ResponseEntity.ok(existingUrl);
        } else {
            String newShortUrl = generateShortUrl(instance.getOriginalUrl());
            instance.setShortUrl(newShortUrl);
            System.out.println(instance);
            return ResponseEntity.ok(instance);
        }
    }
    

    public String generateShortUrl(String originalUrl) {
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(originalUrl.getBytes());
        String encoded = Base64.getEncoder().encodeToString(hash);
        // Take the first 6 characters as the short URL
        return "http://localhost/" + encoded.substring(0, 6);
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null; // Handle error
    }
}
}
