package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {
    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String url, HttpServletRequest request) {
        String shortCode = service.shortenUrl(url);

        // Build the full short URL dynamically
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String shortUrl = baseUrl + "/api/redirect/" + shortCode;

        return "Shortened URL: " + shortUrl;
    }

    @GetMapping("/redirect/{shortCode}")
    public String getOriginalUrl(@PathVariable String shortCode){
        return service.getOriginalUrl(shortCode);
    }
}
