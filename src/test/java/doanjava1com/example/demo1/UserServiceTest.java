package doanjava1com.example.demo1;

import doanjava1com.example.demo1.Exception.UserNotFoundException;
import doanjava1com.example.demo1.Models.User;
import doanjava1com.example.demo1.Repositories.UserRepository;

import doanjava1com.example.demo1.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final String EMAIL = "user@example.com";
    private final String EMPTY_EMAIL = "";
    private final String TOKEN = "token123";

    @BeforeEach
    void setUp() {
        // Optional setup can be done here if needed
    }

    @Test
    void updateResetPassword_WhenUserExists() throws UserNotFoundException {
        System.out.println("Test case 1: Người dùng có tồn tại");
        // Arrange
        User user = new User();
        user.setEmail(EMAIL);
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(user);

        // Act
        userService.updateResetPassword(TOKEN, EMAIL);

        // Assert
        verify(userRepository).getUserByEmail(EMAIL);
        assertEquals(TOKEN, user.getTokenforgotpassword());
        assertNotNull(user.getTimeexpire());
        assertTrue(LocalDateTime.now().plusSeconds(39).isBefore(user.getTimeexpire())); // Check time setting logic
        verify(userRepository).save(user);
    }

    @Test
    void updateResetPassword_WhenUserDoesNotExist_ShouldThrowException() {
        System.out.println("Test case 2: Người dùng không tồn tại");
        // Arrange
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.updateResetPassword(TOKEN, EMAIL);
        });

        verify(userRepository).getUserByEmail(EMAIL);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateResetPassword_WhenEmailIsEmpty_ShouldThrowIllegalArgumentException() {
        System.out.println("Test case 3: Email  không được bỏ trống");
        // Arrange
        // No setup needed specifically for this test case since email is empty

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateResetPassword(TOKEN, EMPTY_EMAIL);
        }, "Email should not be empty");

        verify(userRepository, never()).getUserByEmail(EMPTY_EMAIL);
        verify(userRepository, never()).save(any(User.class));
    }
}
