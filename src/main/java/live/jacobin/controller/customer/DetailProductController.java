package live.jacobin.controller.customer;

import live.jacobin.entity.Category;
import live.jacobin.entity.Product;
import live.jacobin.service.CategoryService;
import live.jacobin.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DetailProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public DetailProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping("/detail-product")
    public String showDetail(@RequestParam(required = false) String pId, Model model) {
        try {
            int productId = Integer.parseInt(pId);

            // Lấy danh sách Category từ service
            List<Category> listC = categoryService.selectAllCategory();
            // Đặt danh sách vào model để truyền tới view
            model.addAttribute("ListC", listC);

            // Lấy Product theo productId từ service
            Product product = productService.selectProductById(productId);
            // Đặt Product vào model để truyền tới view
            model.addAttribute("product", product);
        }
        catch (NumberFormatException ignored) {
            return "redirect:/home";
        }

        return "customer/detail_product_page";
    }

}
