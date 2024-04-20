package live.jacobin.controller.admin;

import live.jacobin.entity.Category;
import live.jacobin.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManagerCategoryController {

    private final CategoryService categoryService;

    public ManagerCategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard/manager-category")
    public String showManagerCategoryPage(Model model) {
        // Lấy danh sách Category từ service
        List<Category> listC = categoryService.selectAllCategory();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListC", listC);

        model.addAttribute("ListC", listC);

        return "admin/manager_category_page";
    }

//    @GetMapping("/dashboard/manager-category/")
}
