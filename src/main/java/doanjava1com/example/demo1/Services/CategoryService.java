package doanjava1com.example.demo1.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doanjava1com.example.demo1.Models.Category;
import doanjava1com.example.demo1.Repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }
    public Category get(long id) {
        return categoryRepository.findById(id).get();
    }
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
