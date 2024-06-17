package com.example.ERT.controllers;

import com.example.ERT.ImagLoader;
import com.example.ERT.ZservicesTest.EMRService;
import com.example.ERT.ZservicesTest.ImageProcessorService;
import com.example.ERT.models.Result;
import com.example.ERT.models.User;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.StatisticsService;
import com.example.ERT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NewController {
    ArrayList<String> results = new ArrayList<>();


    private final ResultService resultService;
    ImagLoader loader = new ImagLoader();
    public ModelAndView viewWithData(List<String> emotionsDf, List<Double> valuesDf, List<String> emotionsMl,
                                     List<Double> valuesMl, MultipartFile file, User user) throws IOException {
        ModelAndView modelAndView = new ModelAndView("download");
        String k09n = loader.loadImageAsBase64("src/main/resources/static/images/f29f1c5c6d9ea.png");
        String nnep1 = loader.loadImageAsBase64("src/main/resources/static/images/logo.png");
        modelAndView.addObject("k09n", k09n);
        modelAndView.addObject("nnep1", nnep1);
        modelAndView.addObject("user", user);
        Result result = new Result();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not valid file");
        }
        try {
            result.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.setDescription("узнать больше о распознанных эмоциях");
        result.setEmotionsDf(emotionsDf);
        result.setEmotionsMl(emotionsMl);
        result.setValuesDf(valuesDf);
        result.setValuesMl(valuesMl);
        result.setUser(user);
        result.setTitle(fileName + " результат распознавания");
        resultService.createResult(result);
        modelAndView.addObject("results", resultService.getByUser(user));
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView viewError(String error) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/")
    public String mainPage(Model model) throws IOException {
        String bg123x = loader.loadImageAsBase64("src/main/resources/static/images/chb.jpg");
        model.addAttribute("bg123x", bg123x);
        return "mainPage";
    }

    public ModelAndView viewErrorWithData(String s, MultipartFile file, User user) throws IOException {
        ModelAndView modelAndView = new ModelAndView("download");
        String k09n = loader.loadImageAsBase64("src/main/resources/static/images/f29f1c5c6d9ea.png");
        String nnep1 = loader.loadImageAsBase64("src/main/resources/static/images/logo.png");
        modelAndView.addObject("k09n", k09n);
        modelAndView.addObject("nnep1", nnep1);
        modelAndView.addObject("user", user);
        Result result = new Result();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("not valid file");
        }
        try {
            result.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.setDescription("Убедитесь, что на изображении присутствует человеческое лицо");
        result.setEmotionsDf(null);
        result.setEmotionsMl(null);
        result.setValuesDf(null);
        result.setValuesMl(null);
        result.setUser(user);
        result.setTitle("Нельзя распознать эмоции на изображении");
        resultService.createResult(result);
        modelAndView.addObject("results", resultService.getByUser(user));
        return modelAndView;
    }

}
