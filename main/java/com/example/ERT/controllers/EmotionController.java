package com.example.ERT.controllers;

import com.example.ERT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
public class EmotionController {
    //@Autowired
    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(EmotionController.class);
    private final UserService userService;

    @Autowired
    private NewController newController;

    @PostMapping("/detect-emotion")
    public ModelAndView detectEmotion(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        String url = "http://127.0.0.1:5000/predict";

        logger.info("Starting emotion detection process...");

        // Создание запроса с файлом для отправки на сервер Flask
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename(); // Указываем оригинальное имя файла
            }
        });

        // Отправка запроса на сервер Flask
        try {
            logger.info("Sending request to Flask server...");
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(body),
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            List<Map<String, Object>> responseBody = response.getBody();

            List<String> emotionsDf = new ArrayList<>();
            List<Double> valuesDf = new ArrayList<>();
            List<String> emotionsML = new ArrayList<>(Arrays.asList("Anger", "Contempt", "Disgust", "Fear", "Happy", "Sadness", "Surprise"));
            List<Double> valuesML = new ArrayList<>();
            // Извлекаем эмоции из каждого элемента списка responseBody
            for (Map<String, Object> item : responseBody) {
                // Обработка данных emotions_deepface
                Map<String, Object> emotionsDfData = (Map<String, Object>) item.get("emotions_deepface");
                if (emotionsDfData != null) {
                    for (Map.Entry<String, Object> entry : emotionsDfData.entrySet()) {
                        String emotion = entry.getKey();
                        Object valueObj = entry.getValue();
                        if (valueObj instanceof Number) {
                            Double value = ((Number) valueObj).doubleValue();
                            emotionsDf.add(emotion);
                            valuesDf.add(value);
                        } else {
                            return newController.viewErrorWithData("Убедитесь, что на изображении присутствует лицо!", file, userService.getUserByPrincipal(principal));
                        }
                    }
                }
                List<Double> emotionsMLData = (List<Double>) item.get("emotions_ml_model");
                if (emotionsMLData != null) {
                    valuesML.addAll(emotionsMLData);
                }
            }


            return newController.viewWithData(emotionsDf, valuesDf, emotionsML, valuesML, file, userService.getUserByPrincipal(principal));
        } catch (HttpClientErrorException.BadRequest e) {
            // В случае ошибки возвращаем на страницу с сообщением об ошибке
            String responseBody = e.getResponseBodyAsString();
            System.out.println("Response Body: " + responseBody); // Выводим тело ответа в консоль
            logger.error("Error while sending request to Flask server: {}", responseBody);
            return newController.viewError(responseBody);
        } catch (Exception e) {
            // В случае ошибки сервера также возвращаем на страницу с сообщением об ошибке
            logger.error("An unexpected error occurred: {}", e.getMessage());
            return newController.viewError(e.getMessage());
        }
    }
}