package doanjava1com.example.demo1.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long clothId;
    private String clothName;
    private Double price;
    private int quantity;
}
