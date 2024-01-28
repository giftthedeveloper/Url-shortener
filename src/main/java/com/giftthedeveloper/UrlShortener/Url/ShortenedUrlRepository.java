package com.giftthedeveloper.UrlShortener.Url;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortenedUrlRepository extends MongoRepository<ShortenedUrl, String>{

    ShortenedUrl findByOriginalUrl(String originalUrl);
    ShortenedUrl findByShortUrl(String shortUrl);
    ShortenedUrl findByCustomUrl(String customUrl);
    
}
