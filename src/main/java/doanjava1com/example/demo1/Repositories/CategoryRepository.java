package doanjava1com.example.demo1.Repositories;

import doanjava1com.example.demo1.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
