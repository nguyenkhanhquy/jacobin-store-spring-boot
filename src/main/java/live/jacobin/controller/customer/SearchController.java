package live.jacobin.controller.customer;

import live.jacobin.entity.Category;
import live.jacobin.entity.Product;
import live.jacobin.service.CategoryService;
import live.jacobin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public SearchController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping("/search")
    public String showSearch(@RequestParam(required = false) String pName, Model model) {
        model.addAttribute("pName", pName);

        // Lấy danh sách Category từ service
        List<Category> listC = categoryService.selectAllCategory();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListC", listC);

        // Lấy danh sách Product theo từ khóa tìm kiếm từ service
        List<Product> listP = productService.selectProductByPartialName(pName);
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListP", listP);

        model.addAttribute("isSearchPage", true);

        return "customer/home_page";
    }
}
