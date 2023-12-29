package doanjava1com.example.demo1.Services;

import java.util.List;

import javax.transaction.Transactional;

import doanjava1com.example.demo1.Models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import doanjava1com.example.demo1.Models.Cloth;
import doanjava1com.example.demo1.Repositories.ClothRepository;

@Service
@Transactional
public class ClothServices {
    int pageSize = 12;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ClothRepository clothRepository;

    public Page<Cloth> listAll(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return clothRepository.findAll(pageable);
    }
    public Sort getSort(String sortField, String sortType) {
        return sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    public Page<Cloth> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        System.out.println(keyword);
        if (keyword != null) {
            return clothRepository.Search(pageable, keyword);
        }
        return clothRepository.findWithOutDelete(pageable);

    }
    public void save(Cloth product) {
        clothRepository.save(product);
    }

    public Cloth get(long id) {
        return clothRepository.findById(id).get();
    }

    public void delete(long id) {
        clothRepository.deleteById(id);
    }
    // Trong ClothServices.java
    public Page<Cloth> listByCategory(Long categoryId, int pageNum, String sortField, String sortType) {
        Category category = categoryService.get(categoryId);
        if (category == null) {
            // Xử lý khi không tìm thấy loại category
            return Page.empty();
        }

        // Thực hiện lấy danh sách sản phẩm theo loại
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortType.equals("asc") ? sortField : sortField).descending());
        return clothRepository.findByCategory(category, pageable);
    }


}
