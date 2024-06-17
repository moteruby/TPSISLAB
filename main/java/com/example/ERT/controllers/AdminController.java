package com.example.ERT.controllers;

import com.example.ERT.ImagLoader;
import com.example.ERT.models.User;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ResultService resultService;
    ImagLoader loader = new ImagLoader();

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) throws IOException {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admin", userService.getUserByPrincipal(principal));
        String k09n = loader.loadImageAsBase64("src/main/resources/static/images/f29f1c5c6d9ea.png");
        String nnep1 = loader.loadImageAsBase64("src/main/resources/static/images/logo.png");
        model.addAttribute("k09n", k09n);
        model.addAttribute("nnep1", nnep1);
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/delete/{id}")
    public String userDelete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/edit/{userId}")
    public String userEdit(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        User user = optionalUser.get();
        userService.changeUserRoles(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{userId}")
    public String userEditG(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        User user = optionalUser.get();
        userService.changeUserRoles(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{userId}")
    public ModelAndView viewUserInfo(@PathVariable Long userId, Principal principal) throws IOException {
        ModelAndView modelAndView = new ModelAndView("user-info");
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            modelAndView.addObject("results", resultService.getByUser(user));
            modelAndView.addObject("user", user);
            modelAndView.addObject("admin", userService.getUserByPrincipal(principal));
            //
            ImagLoader loader = new ImagLoader();
            String k09n = loader.loadImageAsBase64("src/main/resources/static/images/f29f1c5c6d9ea.png");
            modelAndView.addObject("k09n", k09n);
            String nnep1 = loader.loadImageAsBase64("src/main/resources/static/images/logo.png");
            modelAndView.addObject("nnep1", nnep1);
            //
        } else {
            modelAndView.setViewName("user-not-found");
        }
        return modelAndView;
    }
}
