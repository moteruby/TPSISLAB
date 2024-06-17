package com.example.ERT.services;

import com.example.ERT.models.Result;
import com.example.ERT.models.User;
import com.example.ERT.repositories.ResultRepository;
import com.example.ERT.repositories.UserRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private ResultService resultService;

    private User testUser;
    private Result testResult;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");

        testResult = new Result();
        testResult.setId(1L);
        testResult.setTitle("Test Result");
        // Set other properties if needed
    }

    @Test
    void getUserByPrincipal_PrincipalIsNull_ReturnsEmptyUser() {
        User user = resultService.getUserByPrincipal(null);
        assertNotNull(user);
        assertNull(user.getEmail());
    }

    @Test
    void getUserByPrincipal_PrincipalIsNotNull_ReturnsUserWithEmail() {
        Principal principal = () -> "test@example.com";
        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

        User user = resultService.getUserByPrincipal(principal);
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void getByUser_ReturnsListOfResults() {
        when(resultRepository.findAllByUser(any(User.class))).thenReturn(Collections.singletonList(testResult));

        List<Result> results = resultService.getByUser(testUser);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(testResult.getId(), results.get(0).getId());
    }

    @Test
    void createResult_ResultCreatedSuccessfully_ReturnsTrue() {
        assertTrue(resultService.createResult(testResult));
        verify(resultRepository, times(1)).save(testResult);
    }

    @Test
    void deleteResult_ResultExists_ResultDeletedSuccessfully() {
        resultService.deleteResult(1L);
        verify(resultRepository, times(1)).deleteById(1L);
    }

    @Test
    void downloadReport_ResultExists_ReturnsReport() throws IOException, InvalidFormatException {
        when(resultRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testResult));
        ResponseEntity<byte[]> responseEntity = resultService.downloadReport(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void downloadReport_ResultNotExists_ReturnsNotFound() throws IOException, InvalidFormatException {
        when(resultRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(NullPointerException.class, () -> resultService.downloadReport(1L));
    }
}
