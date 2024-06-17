package com.example.ERT.controllers;

import com.example.ERT.ImagLoader;
import com.example.ERT.models.User;
import com.example.ERT.repositories.ResultRepository;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor//@RequestMapping("/result")
public class ResultController {

    //private final ResultService resultService;

    private final UserService userService;
    private final ResultRepository resultRepository;
    private final ResultService resultService;
    ImagLoader loader = new ImagLoader();
    String k09n;
    {
        try {
            k09n = loader.loadImageAsBase64("src/main/resources/static/images/f29f1c5c6d9ea.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    String nnep1;
    {
        try {
            nnep1 = loader.loadImageAsBase64("src/main/resources/static/images/logo.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/results")
    public String results(User user, Model model) {
        model.addAttribute("results", resultRepository.findResultsByUser(user));
        model.addAttribute("user", user);
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "view_results";
    }

    @GetMapping("/result/remove/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return "redirect:/firstpage";
    }

    @GetMapping("/result/view/{resultId}")
    public String viewResults(@PathVariable Long resultId, Principal principal, Model model){
        model.addAttribute("result", resultRepository.findResultById(resultId));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "emotions";
    }

    @GetMapping("/result/download/{id}")
    public ResponseEntity<byte[]> resultDownload(@PathVariable Long id) throws IOException, InvalidFormatException {
        return resultService.downloadReport(id);
    }

}
