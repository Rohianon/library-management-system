package com.rohianon.library.fx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rohianon.library.fx.model.Book;
import com.rohianon.library.fx.model.PageResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

public class BookService {
    private static final String BASE_URL_PROPERTY = "library.api.baseUrl";
    private static final String PROPERTIES_FILE = "/application.properties";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public BookService() {
        this(loadBaseUrl());
    }

    public BookService(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private static String loadBaseUrl() {
        // 1. Check system property
        String url = System.getProperty(BASE_URL_PROPERTY);
        if (url != null && !url.isBlank()) {
            return url;
        }

        // 2. Check environment variable
        url = System.getenv("LIBRARY_API_BASE_URL");
        if (url != null && !url.isBlank()) {
            return url;
        }

        // 3. Load from application.properties
        try (InputStream input = BookService.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                Properties props = new Properties();
                props.load(input);
                url = props.getProperty(BASE_URL_PROPERTY);
                if (url != null && !url.isBlank()) {
                    return url;
                }
            }
        } catch (IOException e) {
            // Fall through to error
        }

        throw new IllegalStateException("Base URL not configured. Set " + BASE_URL_PROPERTY + " in application.properties");
    }

    public List<Book> getAllBooks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch books: HTTP " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), new TypeReference<List<Book>>() {});
    }

    public PageResponse<Book> getAllBooks(int page, int size) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "?page=" + page + "&size=" + size))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch books: HTTP " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), new TypeReference<PageResponse<Book>>() {});
    }

    public Book createBook(Book book) throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new IOException("Failed to create book: HTTP " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), Book.class);
    }

    public Book updateBook(Long id, Book book) throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(book);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to update book: HTTP " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), Book.class);
    }

    public void deleteBook(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new IOException("Failed to delete book: HTTP " + response.statusCode());
        }
    }
}
