package doanjava1com.example.demo1.APIControllers;

import doanjava1com.example.demo1.Models.Cloth;
import doanjava1com.example.demo1.Services.ClothServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/API/clothes")
public class APIClothController {
    @Autowired
    private ClothServices clothServices;
    private ClothServices clothServices2;

    @GetMapping("/page/{pageNum}")
    public List<Cloth> list(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
                            @Param("sortType") String sortType, @Param("keyword") String keyword) {
        Page<Cloth> page = clothServices.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
        return page.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cloth> get(@PathVariable(name = "id") Integer id) {
        System.out.println("get1");
        try {
            Cloth book = clothServices.get(id);
            if (book == null) {
                return new ResponseEntity<Cloth>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Cloth>(book, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Cloth>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public void add(@RequestBody Cloth book) {
        System.out.println("add");
        clothServices.save(book);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@RequestBody Cloth book, @PathVariable(name = "id") Long id) {
        System.out.println("edit");
        try {
            Cloth Foundbook = clothServices.get(id);
            if (Foundbook == null) {
                return new ResponseEntity<Cloth>(HttpStatus.NOT_FOUND);
            }
            book.setId(id);
            clothServices.save(book);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Integer id) {
        System.out.println("Delete");
        clothServices.delete(id);
    }

}
