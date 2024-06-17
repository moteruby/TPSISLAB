package com.example.ERT.controllers;

import com.example.ERT.ImagLoader;
import com.example.ERT.models.User;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final ResultService resultService;

    ImagLoader loader = new ImagLoader();
    String bg123x;
    {
        try {
            bg123x = loader.loadImageAsBase64("src/main/resources/static/images/chb.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("bg123x", bg123x);
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) throws IOException {
        model.addAttribute("bg123x", bg123x);
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model, Principal principal) {
        if (user.getEmail() == null || user.getPassword() == null) {
            model.addAttribute("bg123x", bg123x);
            return "registration";
        }
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с таким номером/email уже существует");
            model.addAttribute("bg123x", bg123x);
            return "registration";
        }
        return "redirect:/login";
    }

    @PostMapping("/acc/update")
    public String acc(@RequestParam("name") String name,
                      @RequestParam("icon") MultipartFile icon,
                      Principal principal, Model model) {
        User user = resultService.getUserByPrincipal(principal);
        User updatedUser = userService.updateUser(name, icon, user);
        model.addAttribute("user", updatedUser);
        return "redirect:/acc";
    }
}