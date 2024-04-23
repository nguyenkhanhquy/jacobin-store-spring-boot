package live.jacobin.controller.admin;

import live.jacobin.entity.Category;
import live.jacobin.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard/manager-category")
public class ManagerCategoryController {

    private final CategoryService categoryService;

    public ManagerCategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showManagerCategoryPage(Model model) {
        // Lấy danh sách Category từ service
        List<Category> listC = categoryService.selectAllCategory();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListC", listC);

        model.addAttribute("ListC", listC);

        return "admin/manager_category_page";
    }

    @GetMapping("/add-category")
    public String showAddCategoryPage() {
        return "admin/add_category_page";
    }

    @PostMapping("/add-category")
    public String addCategory(@RequestParam String name, Model model) {
        String message;
        String messageError;
        if (categoryService.checkNameExists(name)) {
            messageError = "Tên danh mục đã tồn tại! Vui lòng điền tên khác.";
            message = "";
            model.addAttribute("name", name);
        }
        else {
            Category category = Category.builder()
                    .name(name)
                    .build();

            categoryService.saveCategory(category);

            int idC = categoryService.selectCategoryByName(name).getCategoryId();

            message = "Thêm thành công danh mục [" + name + "] có mã [" + idC + "]";
            messageError = "";
        }

        model.addAttribute("message", message);
        model.addAttribute("messageError", messageError);

        return "admin/add_category_page";
    }

}
