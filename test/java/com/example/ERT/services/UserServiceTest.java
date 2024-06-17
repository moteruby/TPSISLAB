package com.example.ERT.services;

import com.example.ERT.models.User;
import com.example.ERT.models.enums.Role;
import com.example.ERT.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Mocking behavior for UserRepository and PasswordEncoder if needed
    }

    @Test
    void createUser_Successfully() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setPhoneNumber("123456789");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // When
        boolean created = userService.createUser(user);

        // Then
        assertTrue(created);
        assertTrue(user.isActive());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_Fail_EmailAlreadyExists() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(new User());

        // When
        boolean created = userService.createUser(user);

        // Then
        assertFalse(created);
        verify(userRepository, never()).save(user);
    }

    @Test
    void createUser_Fail_PhoneNumberAlreadyExists() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPhoneNumber("123456789");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(new User());

        // When
        boolean created = userService.createUser(user);

        // Then
        assertFalse(created);
        verify(userRepository, never()).save(user);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(
                List.of(new User(), new User())
        );

        // When
        var users = userService.getAllUsers();

        // Then
        assertNotNull(users);
        assertEquals(2, users.size());
    }


    @Test
    void getUserByPrincipal_WithNonNullPrincipal_ReturnsUserFromRepository() {
        // Given
        Principal principal = () -> "test@example.com";
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(principal.getName())).thenReturn(user);

        // When
        User result = userService.getUserByPrincipal(principal);

        // Then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void getUserById_ReturnsUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
    }

    @Test
    void banUser_ActiveUser_BansUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.banUser(userId);

        // Then
        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void banUser_InactiveUser_UnbansUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setActive(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.banUser(userId);

        // Then
        assertTrue(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeUserRoles_UserIsAdmin_RolesChangedToUser() {
        // Given
        User user = new User();
        user.getRoles().add(Role.ROLE_ADMIN);

        // When
        userService
                .changeUserRoles(user);

        // Then
        assertTrue(user.getRoles().contains(Role.ROLE_USER));
        assertFalse(user.getRoles().contains(Role.ROLE_ADMIN));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeUserRoles_UserIsUser_RolesChangedToAdmin() {
        // Given
        User user = new User();
        user.getRoles().add(Role.ROLE_USER);

        // When
        userService.changeUserRoles(user);

        // Then
        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
        assertFalse(user.getRoles().contains(Role.ROLE_USER));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_UserExists_DeletesUser() {
        // Given
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void updateUser_WithIcon_UpdatesUser() {
        // Given
        User user = new User();
        user.setName("Old Name");
        user.setIcon("old_icon.jpg");

        MockMultipartFile iconFile = new MockMultipartFile("icon", "icon.jpg", "image/jpeg", "icon data".getBytes());

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L); // Simulating save operation that sets the ID
            return savedUser;
        });

        // When
        User updatedUser = userService.updateUser("New Name", iconFile, user);

        // Then
        assertNotNull(updatedUser);
        assertEquals("New Name", updatedUser.getName());
        assertNotEquals("old_icon.jpg", updatedUser.getIcon());
        // Add more assertions as needed
    }

}
