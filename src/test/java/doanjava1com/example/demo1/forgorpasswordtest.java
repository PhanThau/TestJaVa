//package doanjava1com.example.demo1;
//import javax.mail.MessagingException;
//import javax.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.ui.Model;
//import static org.mockito.Mockito.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import java.io.UnsupportedEncodingException;
//
//@ExtendWith(MockitoExtension.class)
//public class forgorpasswordtest {
//    @InjectMocks
//    private AuthController authController;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private Model model;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    private static final String EMAIL = "test@example.com";
//    private static final String TOKEN = "randomTokenString";
//
//    @BeforeEach
//    void setUp() throws UnsupportedEncodingException, MessagingException {
//        when(request.getParameter("email")).thenReturn(EMAIL);
//        doNothing().when(userService).up(anyString(), eq(EMAIL));
//        doNothing().when(authController).sendEmail(eq(EMAIL), anyString());
//    }
//
//    @Test
//    void processForgotPassword_Success() {
//        // Given
//        String expectedUrl = "auth/forgot_password_form";
//
//        // When
//        String url = authController.processForgotPassword(request, model);
//
//        // Then
//        verify(userService).updateResetPassword(anyString(), eq(EMAIL));
//        verify(model).addAttribute(eq("message"), anyString());
//        assertThat(url).isEqualTo(expectedUrl);
//    }
//
//    @Test
//    void processForgotPassword_UserNotFound() {
//        // Given
//        when(userService.updateResetPassword(anyString(), eq(EMAIL))).thenThrow(new UserNotFoundException("User not found"));
//
//        // When
//        String url = authController.processForgotPassword(request, model);
//
//        // Then
//        verify(model).addAttribute(eq("error"), eq("User not found"));
//        assertThat(url).isEqualTo("auth/forgot_password_form");
//    }
//
//    @Test
//    void processForgotPassword_FailureSendingEmail() {
//        // Given
//        doThrow(new MessagingException("Failed to send email")).when(authController).sendEmail(eq(EMAIL), anyString());
//
//        // When
//        String url = authController.processForgotPassword(request, model);
//
//        // Then
//        verify(model).addAttribute(eq("error"), eq("Error while sending email"));
//        assertThat(url).isEqualTo("auth/forgot_password_form");
//    }
//
//    @Test
//    void processForgotPassword_UnsupportedEncodingException() {
//        // Given
//        doThrow(new UnsupportedEncodingException("Encoding problem")).when(authController).sendEmail(eq(EMAIL), anyString());
//
//        // When
//        String url = authController.processForgotPassword(request, model);
//
//        // Then
//        verify(model).addAttribute(eq("error"), eq("Error while sending email"));
//        assertThat(url).isEqualTo("auth/forgot_password_form");
//    }
//}
