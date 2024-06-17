package com.example.ERT.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emotion-recognition")
public class EmotionRecognitionController {
/*
    @PostMapping
    public String recognizeEmotion(@RequestBody Map<String, Object> payload, Model model) {
        System.out.println("Received emotions: " + payload);
        model.addAttribute("results", payload);
        return "view";
    }*/
}
