package com.example.ERT.controllers;

import com.example.ERT.ImagLoader;
import com.example.ERT.models.User;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ResultService resultService;

    @Mock
    private ImagLoader loader;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdmin() throws IOException {
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);

        when(userService.getAllUsers()).thenReturn(List.of(new User()));
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());
        when(loader.loadImageAsBase64(anyString())).thenReturn("base64Image");

        String viewName = adminController.admin(model, principal);

        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(model, times(1)).addAttribute(eq("admin"), any());
        verify(model, times(1)).addAttribute(eq("k09n"), anyString());
        verify(model, times(1)).addAttribute(eq("nnep1"), anyString());

        assertEquals("admin", viewName);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUserBan() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/admin/user/ban/" + userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        verify(userService, times(1)).banUser(userId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUserDelete() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/admin/user/delete/" + userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUserEdit() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);

        when(userService.getUserById(userId)).thenReturn(optionalUser);

        // Act
        String viewName = adminController.userEdit(userId);

        // Assert
        assertEquals("redirect:/admin", viewName);

        // Verify
        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).changeUserRoles(user);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testViewUserInfo() throws IOException {
        Long userId = 1L;
        Principal principal = mock(Principal.class);
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);

        when(userService.getUserById(userId)).thenReturn(optionalUser);
        when(resultService.getByUser(user)).thenReturn(List.of());
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());
        when(loader.loadImageAsBase64(anyString())).thenReturn("base64Image");

        ModelAndView modelAndView = adminController.viewUserInfo(userId, principal);

        assertEquals("user-info", modelAndView.getViewName());
        assertEquals(user, modelAndView.getModel().get("user"));
        assertNotNull(modelAndView.getModel().get("results"));
        assertNotNull(modelAndView.getModel().get("admin"));
        assertNotNull(modelAndView.getModel().get("k09n"));
        assertNotNull(modelAndView.getModel().get("nnep1"));

        verify(userService, times(1)).getUserById(userId);
        verify(resultService, times(1)).getByUser(user);
        verify(userService, times(1)).getUserByPrincipal(principal);
    }
}