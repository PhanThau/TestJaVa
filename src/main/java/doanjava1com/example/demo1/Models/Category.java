package doanjava1com.example.demo1.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="category_id")
    private Long id;
    @Column(name = "name", nullable = false,length = 255)
    private String name;
    @Column(name = "description",nullable = true,length = 255)
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Cloth> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Category() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Category(Long id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
