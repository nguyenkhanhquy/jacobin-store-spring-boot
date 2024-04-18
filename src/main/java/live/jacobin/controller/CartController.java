package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.service.CartService;
import live.jacobin.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    private final HttpServletRequest request;

    private final CartService cartService;

    public CartController(final HttpServletRequest request, CartService cartService) {
        this.request = request;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String showCartPage(Model model) {
        //HttpSession session = request.getSession();

        // Lấy thông tin giỏ hàng của người dùng
        //Cart cart = cartService.selectCartByUser(SessionUtil.getLoginedUser(session));
        //session.setAttribute("cart", cart);

        return "customer/cart_page";
    }

}
