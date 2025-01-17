package app.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String showCart() {
        // Bạn có thể thêm logic xử lý dữ liệu giỏ hàng ở đây
        return "cart";  // Điều này sẽ trả về cart.html
    }
}

