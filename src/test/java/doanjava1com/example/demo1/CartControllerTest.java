package doanjava1com.example.demo1;

import doanjava1com.example.demo1.Controllers.ClothController;
import doanjava1com.example.demo1.Services.CartService;
import doanjava1com.example.demo1.daos.Cart;
import doanjava1com.example.demo1.daos.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private ClothController clothController;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    // Khởi tạo môi trường kiểm thử cho trường hợp kiểm thử này.
    @Test
    void addToCart_ShouldAddNewItem_WhenCartIsEmpty() {
        // Given (Đặt điều kiện)
        Cart mockCart = new Cart(); // Tạo một đối tượng giỏ hàng mới, trống.
        // Mô phỏng phương thức cartService.getCart để trả về giỏ hàng trống của chúng ta khi được gọi.
        when(cartService.getCart(session)).thenReturn(mockCart);

        // When (Thực hiện)
        // Gọi phương thức addToCart với dữ liệu mặt hàng mẫu.
        String redirectUrl = clothController.addToCart(session, 1L, "Sweater", 30.0, 1);


        // Then (Kiểm tra)
        // Xác minh rằng cartService.updateCart được gọi đúng một lần, đảm bảo giỏ hàng được cập nhật.
        verify(cartService, times(1)).updateCart(eq(session), any(Cart.class));
        // Kiểm tra xem phương thức có trả về URL chuyển hướng mong đợi hay không.
        assertThat(redirectUrl).isEqualTo("redirect:/clothes");
    }


    @Test
    void addToCart_ShouldUpdateItemQuantity_WhenItemExistsInCart() {
        // Given (Đặt điều kiện)
        Cart mockCart = new Cart(); // Tạo một đối tượng giỏ hàng mới.
        // Điền trước giỏ hàng với một mặt hàng.
        mockCart.addItems(new Item(1L, "Sweater", 30.0, 1));
        // Mô phỏng phương thức cartService.getCart để trả về giỏ hàng của chúng ta đã được điền trước.
        when(cartService.getCart(session)).thenReturn(mockCart);

        // When (Thực hiện)
        // Thử thêm lại mặt hàng đó (theo id) nhưng với số lượng khác.
        clothController.addToCart(session, 1L, "Sweater", 30.0, 1);

        // Then (Kiểm tra)
        // Kiểm tra xem số lượng mặt hàng đã được cập nhật đúng là 2 không.
        verify(cartService, times(1)).updateCart(eq(session), argThat(cart ->
                cart.getCartItems().stream().anyMatch(item -> item.getQuantity() == 2)
        ));
    }


    @Test
    void addToCart_ShouldFail_WhenAddingItemWithNegativeQuantity() {
        // Given (Đặt điều kiện)
        Cart mockCart = new Cart(); // Tạo một đối tượng giỏ hàng mới, trống.
        // Mô phỏng phương thức cartService.getCart để trả về giỏ hàng trống của chúng ta.
        when(cartService.getCart(session)).thenReturn(mockCart);

        // When (Thực hiện)
        // Cố gắng thêm một mặt hàng với số lượng âm, thường không được cho phép.
        String redirectUrl = clothController.addToCart(session, 1L, "Sweater", 30.0, -1);

        // Then (Kiểm tra)
        // Kiểm tra xem phương thức không chuyển hướng như mong đợi, biểu thị cho sự thất bại.
        // Trong các tình huống thực tế, có thể sẽ xử lý bằng cách ném ra một ngoại lệ hoặc tương tự.
        assertThat(redirectUrl).isNotEqualTo("redirect:/clothes");
    }
}
