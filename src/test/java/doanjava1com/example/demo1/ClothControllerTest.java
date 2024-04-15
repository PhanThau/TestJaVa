package doanjava1com.example.demo1;
import doanjava1com.example.demo1.Controllers.ClothController;
import doanjava1com.example.demo1.Models.Cloth;
import doanjava1com.example.demo1.Services.ClothServices;
import doanjava1com.example.demo1.Utils.FileUploadUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)

public class ClothControllerTest {
    @Mock
    private ClothServices clothServices;

    @InjectMocks
    private ClothController yourController;

    private Cloth cloth;

    @BeforeEach
    void setUp() {
        // Initialize your cloth object here if needed
        cloth = new Cloth();
    }

    @Test
    void saveClothWithFile() throws Exception {
        // Given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "testfile.png", "image/png", "test data".getBytes());
        String expectedFileName = "testfile.png";
        // Mocking static method saveFile from FileUploadUtil
        try (var mockedStatic = mockStatic(FileUploadUtil.class)) {
            // When
            String viewName = yourController.saveCloth(cloth, multipartFile);

            // Then
            assertThat(cloth.getPhotourl()).isEqualTo(expectedFileName);
            verify(clothServices, times(2)).save(cloth); // Called twice if file is not empty
            mockedStatic.verify(() -> FileUploadUtil.saveFile(contains("photos/"), eq(expectedFileName), eq(multipartFile)));
            assertThat(viewName).isEqualTo("redirect:/clothes");
        }
    }

    @Test
    void saveClothWithoutFile() throws Exception {
        // Given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "", "image/png", new byte[0]);

        // When
        String viewName = yourController.saveCloth(cloth, multipartFile);

        // Then
        assertThat(cloth.getPhotourl()).isEmpty();
        verify(clothServices).save(cloth); // Called once if file is empty
        verifyNoMoreInteractions(clothServices);
        assertThat(viewName).isEqualTo("redirect:/clothes");
    }
}
