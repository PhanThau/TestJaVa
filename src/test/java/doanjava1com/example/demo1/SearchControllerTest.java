package doanjava1com.example.demo1;

import doanjava1com.example.demo1.Models.Cloth;
import doanjava1com.example.demo1.Repositories.ClothRepository;
import doanjava1com.example.demo1.Services.ClothServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClothServiceTest {

    @Mock
    private ClothRepository clothRepository;

    @InjectMocks
    private ClothServices clothService;

    private List<Cloth> sampleClothes;


    @BeforeEach
    void setUp() {
        Cloth cloth1 = new Cloth(); // Cấu hình thuộc tính cho cloth1
        Cloth cloth2 = new Cloth(); // Cấu hình thuộc tính cho cloth2
        sampleClothes = Arrays.asList(cloth1, cloth2);
    }

    @Test
    void searchClothes_ShouldReturnAll_WhenNoKeywordProvided() {
        Page<Cloth> expectedPage = new PageImpl<>(sampleClothes);
        when(clothRepository.findWithOutDelete(PageRequest.of(0, 10))).thenReturn(expectedPage);

        Page<Cloth> result = clothService.searchClothes("", 0, 10);

        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    @Test
    void searchClothes_ShouldReturnByTitle_WhenKeywordIsTitle() {
        Page<Cloth> expectedPage = new PageImpl<>(sampleClothes);
        when(clothRepository.Search(any(), eq("Shirt"))).thenReturn(expectedPage);

        Page<Cloth> result = clothService.searchClothes("Shirt", 0, 10);

        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    @Test
    void searchClothes_ShouldReturnByPrice_WhenKeywordIsPrice() {
        Page<Cloth> expectedPage = new PageImpl<>(sampleClothes);
        when(clothRepository.Search(any(), eq("20"))).thenReturn(expectedPage);

        Page<Cloth> result = clothService.searchClothes("20", 0, 10);

        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }

    @Test
    void searchClothes_ShouldReturnByCategory_WhenKeywordIsCategory() {
        Page<Cloth> expectedPage = new PageImpl<>(sampleClothes);
        when(clothRepository.Search(any(), eq("Dress"))).thenReturn(expectedPage);

        Page<Cloth> result = clothService.searchClothes("Dress", 0, 10);

        assertThat(result.getContent()).isEqualTo(expectedPage.getContent());
    }
}
