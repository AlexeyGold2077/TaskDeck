package com.alexeygold2077.taskdeck.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApiErrorResponseFactory {

    public Map<String, Object> buildError(
            int statusCode,
            String message,
            String path,
            List<String> details
    ) {
        return buildError(HttpStatus.valueOf(statusCode), message, path, details);
    }

    public Map<String, Object> buildError(
            HttpStatus status,
            String message,
            String path,
            List<String> details
    ) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        error.put("path", path);

        if (details != null && !details.isEmpty()) {
            error.put("details", details);
        }

        return error;
    }
}
