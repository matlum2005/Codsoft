package com.studentmgmt.service;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentmgmt.model.Student;

public class HttpService {
    private static final String BASE_URL = "http://localhost:8080/api/students";
private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

public static CompletableFuture<List<Student>> getAllStudents() {
        System.err.println("Calling API: GET " + BASE_URL);
        return httpClient.sendAsync(createGetRequest(BASE_URL), HttpResponse.BodyHandlers.ofString())
                .handle((response, ex) -> {
                    if (ex != null) {
                        if (ex.getCause() instanceof ConnectException) {
                            throw new RuntimeException("Connection failed - ensure backend is running on http://localhost:8080", ex);
                        }
                        throw new RuntimeException("Failed to connect to backend: " + ex.getMessage(), ex);
                    }
                    System.err.println("API Response status: " + response.statusCode() + " for " + BASE_URL);
                    try {
                        if (response.statusCode() == 200) {
                            return objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                        }
                        throw new RuntimeException("Failed to fetch students. Status: " + response.statusCode());
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing response: " + e.getMessage(), e);
                    }
                });
    }

public static CompletableFuture<Student> createStudent(Student student) {
        try {
            String json = objectMapper.writeValueAsString(student);
            System.err.println("Calling API: POST " + BASE_URL);
            return httpClient.sendAsync(
                    HttpRequest.newBuilder()
                            .uri(URI.create(BASE_URL))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            ).handle((response, ex) -> {
                if (ex != null) {
                    if (ex.getCause() instanceof ConnectException) {
                        throw new RuntimeException("Connection failed - ensure backend is running on http://localhost:8080", ex);
                    }
                    throw new RuntimeException("Failed to create student: " + ex.getMessage(), ex);
                }
                System.err.println("API Response status: " + response.statusCode() + " for POST " + BASE_URL);
                try {
                    if (response.statusCode() == 201) {
                        return objectMapper.readValue(response.body(), Student.class);
                    }
                    throw new RuntimeException("Failed to create student. Status: " + response.statusCode());
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing response: " + e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new RuntimeException("JSON serialization error", e));
        }
    }

    public static CompletableFuture<Student> updateStudent(Long id, Student student) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = objectMapper.writeValueAsString(student);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/" + id))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(json))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    return objectMapper.readValue(response.body(), Student.class);
                }
                throw new RuntimeException("Failed to update student: " + response.statusCode());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static CompletableFuture<Void> deleteStudent(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/" + id))
                        .DELETE()
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 204) {
                    throw new RuntimeException("Failed to delete student: " + response.statusCode());
                }
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static CompletableFuture<List<Student>> searchStudents(String keyword) {
        return httpClient.sendAsync(createGetRequest(BASE_URL + "/search?keyword=" + keyword), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        if (response.statusCode() == 200) {
                            return objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                        }
                        return List.of();
                    } catch (Exception e) {
                        return List.of();
                    }
                });
    }

    private static HttpRequest createGetRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }
}
