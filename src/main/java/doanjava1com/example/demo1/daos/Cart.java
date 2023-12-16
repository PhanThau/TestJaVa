package doanjava1com.example.demo1.daos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();

    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getClothId(), item.getClothId()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() + item.getQuantity());
                    return true;
                })
                .orElse(false);

        if (!isExist) {
            cartItems.add(item);
        }
    }

    public void removeItems(Long clothId) {
        cartItems.removeIf(item -> Objects.equals(item.getClothId(), clothId));
    }

    public void updateItems(Long clothId, int quantity) {
        cartItems.stream()
                .filter(item -> Objects.equals(item.getClothId(),clothId))
                .forEach(item -> item.setQuantity(quantity));
    }

}
