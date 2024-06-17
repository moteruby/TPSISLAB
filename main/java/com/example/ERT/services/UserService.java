package com.example.ERT.services;

import com.example.ERT.models.User;
import com.example.ERT.models.enums.Role;
import com.example.ERT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        String phone = user.getPhoneNumber();
        if (userRepository.findByEmail(email) != null) return false;
        if (userRepository.findByPhoneNumber(phone) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        userRepository.save(user);
        return true;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public Optional<User> getUserById(Long UserId){
        Optional<User> user = userRepository.findById(UserId);
        return user;
    }

    //public List<User> list() {
   //     return userRepository.findAll();
    //}

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
            } else {
                user.setActive(true);
            }
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user) {
        Set<Role> roles = user.getRoles();

        if (roles.contains(Role.ROLE_ADMIN)) {
            roles.clear();
            roles.add(Role.ROLE_USER);
        } else {
            roles.clear();
            roles.add(Role.ROLE_ADMIN);
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
    }

    @Transactional
    public User updateUser(String name, MultipartFile icon, User user) {
        if (name == null || name.isEmpty()) {
            name = user.getName();
        }

        String fileName = user.getIcon();
        if (!icon.isEmpty() && icon.getSize() > 0) {
            fileName = StringUtils.cleanPath(icon.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Недопустимый путь к файлу");
            }
            try {
                String encodedIcon = Base64.getEncoder().encodeToString(icon.getBytes());
                user.setIcon(encodedIcon);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось сохранить файл иконки", e);
            }
        }

        user.setName(name);
        // остальные поля уже установлены, обновим только необходимые

        return userRepository.save(user);
    }
}
