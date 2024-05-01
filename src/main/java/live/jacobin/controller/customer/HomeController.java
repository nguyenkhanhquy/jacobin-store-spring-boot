package live.jacobin.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.entity.Category;
import live.jacobin.entity.Product;
import live.jacobin.service.CartService;
import live.jacobin.service.CategoryService;
import live.jacobin.service.ProductService;
import live.jacobin.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final HttpServletRequest request;

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public HomeController(HttpServletRequest request, CategoryService categoryService, ProductService productService, CartService cartService) {
        this.request = request;
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @RequestMapping(value = {"/", "/home"})
    public String showHomePage(Model model) {
        // Lấy danh sách Category từ service
        List<Category> listC = categoryService.selectAllCategory();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListC", listC);

        // Lấy danh sách Product từ service
        List<Product> listP = productService.select20FirstProduct();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListP", listP);

        // Lấy thông tin giỏ hàng của người dùng
        HttpSession session = request.getSession();
        Cart cart = cartService.selectCartByUser(SessionUtil.getLoginedUser(session));
        session.setAttribute("cart", cart);

        model.addAttribute("cId", null);

        return "customer/home_page";
    }

}
