package live.jacobin.controller;

import live.jacobin.entity.Category;
import live.jacobin.entity.Product;
import live.jacobin.service.CategoryService;
import live.jacobin.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
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

//        // Xuất danh sách ra console
//        System.out.println("Danh sách Product:");
//        for (Product product : listP) {
//            System.out.println(product); // Sử dụng toString()
//        }

        model.addAttribute("cId", null);

        return "home_page";
    }

}
