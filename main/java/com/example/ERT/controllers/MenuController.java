package com.example.ERT.controllers;

import com.example.ERT.ImagLoader;
import com.example.ERT.models.User;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.StatisticsService;
import com.example.ERT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final ResultService resultService;
    private final StatisticsService statisticsService;
    private final UserService userService;

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

    @GetMapping("/firstpage")
    public String mainMenu(Principal principal, Model model) {
        model.addAttribute("user", statisticsService.getUserCount());
        model.addAttribute("result", statisticsService.getResultCount());
        model.addAttribute("usert", userService.getUserByPrincipal(principal));
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "firstpage";
    }

    @GetMapping("/view_results")
    public String viewResults(Principal principal, Model model) {
        User user = resultService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("results", resultService.getByUser(user));
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "view_results";
    }

    @GetMapping("/contact-info")
    public String contactInfo(Principal principal, Model model) {
        model.addAttribute(userService.getUserByPrincipal(principal));
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "contact-info";
    }

    @GetMapping("/new_images")
    public String newEmotions(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("results", resultService.getByUser(user));
        model.addAttribute("user", user);
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "download";
    }

    @GetMapping("/acc")
    public String acc(Principal principal, Model model) {
        model.addAttribute("user", resultService.getUserByPrincipal(principal));
        model.addAttribute("results", resultService.getByUser(userService.getUserByPrincipal(principal)));
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "username";
    }

}
