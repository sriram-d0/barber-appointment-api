package com.barbershop.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static ResponseEntity<?> success(Object data, String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<?> success(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<?> error(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}
