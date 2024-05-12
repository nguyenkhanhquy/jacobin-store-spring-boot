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
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping("/category")
    public String showCategory(@RequestParam(required = false) String cId, Model model) {
        try {
            int categoryId = Integer.parseInt(cId);
            // Lấy danh sách Product theo CategoryId từ service
            List<Product> listP = productService.select10FirstProductByCategoryId(categoryId);
            // Đặt danh sách vào model để truyền tới view
            model.addAttribute("ListP", listP);

            // Lấy danh sách Category từ service
            List<Category> listC = categoryService.selectAllCategory();
            // Đặt danh sách vào model để truyền tới view
            model.addAttribute("ListC", listC);

            model.addAttribute("cId", categoryId);
            model.addAttribute("tag", categoryId);
        } catch (NumberFormatException nfe) {
            return "redirect:/home";
        }

        return "customer/home_page";
    }

}
