package live.jacobin.controller.admin;

import live.jacobin.entity.Product;
import live.jacobin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/manager-product")
public class ManagerProductController {

    private final ProductService productService;

    @Autowired
    public ManagerProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showManagerProductPage(Model model) {
        // Lấy danh sách Product từ service
        List<Product> listP = productService.selectAllProduct();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListP", listP);

        return "admin/product/manager_product_page";
    }

}
