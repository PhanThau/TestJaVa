package doanjava1com.example.demo1;

import doanjava1com.example.demo1.Controllers.CategoryController;
import doanjava1com.example.demo1.Models.Category;
import doanjava1com.example.demo1.Services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @Test
    void deleteCategory_WhenCategoryExists() {
        // Arrange
        long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryService.get(categoryId)).thenReturn(category);

        // Act
        String viewName = categoryController.deletecategory(categoryId);

        // Assert
        verify(categoryService).delete(categoryId);
        assertEquals("redirect:/categories", viewName);
        System.out.println("Test case deleteCategory_WhenCategoryExists: Category exists and is deleted successfully.");
    }

    @Test
    void deleteCategory_WhenCategoryDoesNotExist() {
        // Arrange
        long categoryId = 1L;
        when(categoryService.get(categoryId)).thenReturn(null);

        // Act
        String viewName = categoryController.deletecategory(categoryId);
        //view name = notfound

        // Assert
        verify(categoryService, never()).delete(categoryId);
        assertEquals("notfoundd", viewName);
        System.out.println("Test case deleteCategory_WhenCategoryDoesNotExist: No category found for deletion.");
    }
}
