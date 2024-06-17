package com.example.ERT.controllers;

import com.example.ERT.models.Result;
import com.example.ERT.models.User;
import com.example.ERT.repositories.ResultRepository;
import com.example.ERT.services.ResultService;
import com.example.ERT.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import java.security.Principal;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResultControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private ResultService resultService;

    @InjectMocks
    private ResultController resultController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resultController).build();
    }

    @Test
    void testResults() throws Exception {
        // Mock data
        User mockUser = new User();
        mockUser.setId(1L);

        List<Result> mockResults = new ArrayList<>();

        when(resultRepository.findResultsByUser(any(User.class))).thenReturn(mockResults);
        when(userService.getUserByPrincipal(any(Principal.class))).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/results").principal(() -> "username"))
                .andExpect(status().isOk())
                .andExpect(view().name("view_results"))
                .andExpect(model().attributeExists("results"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("k09n"))
                .andExpect(model().attributeExists("nnep1"));
    }

    @Test
    void testDeleteResult() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/result/remove/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/firstpage")); // Adjust redirect URL if needed
    }

    @Test
    void testViewResults() throws Exception {
        // Mock data
        User mockUser = new User();
        mockUser.setId(1L);

        Result mockResult = new Result();
        mockResult.setId(1L);

        when(resultRepository.findResultById(anyLong())).thenReturn(mockResult);
        when(userService.getUserByPrincipal(any(Principal.class))).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/result/view/{resultId}", 1L).principal(() -> "username"))
                .andExpect(status().isOk())
                .andExpect(view().name("emotions"))
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("k09n"))
                .andExpect(model().attributeExists("nnep1"));
    }

    @Test
    void testResultDownload() throws Exception {
        // Mock response entity
        byte[] content = "Mock report content".getBytes();
        ResponseEntity<byte[]> mockResponseEntity = new ResponseEntity<>(content, HttpStatus.OK);

        when(resultService.downloadReport(anyLong())).thenReturn(mockResponseEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/result/download/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

    }
}
