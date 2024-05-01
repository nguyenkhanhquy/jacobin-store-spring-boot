package live.jacobin.controller.admin;

import live.jacobin.entity.Category;
import live.jacobin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/manager-category")
public class ManagerCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public ManagerCategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showManagerCategoryPage(Model model) {
        // Lấy danh sách Category từ service
        List<Category> listC = categoryService.selectAllCategory();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListC", listC);

        return "admin/category/manager_category_page";
    }

    @GetMapping("/add-category")
    public String showAddCategoryPage() {
        return "admin/category/add_category_page";
    }

    @PostMapping("/add-category")
    public String addCategory(@RequestParam String name,
                              RedirectAttributes redirectAttributes) {
        String message;
        String messageError;
        if (categoryService.checkNameExists(name)) {
            messageError = "Danh mục [" + name + "] đã tồn tại! Vui lòng chọn tên danh mục khác!";
            redirectAttributes.addFlashAttribute("messageError", messageError);
            redirectAttributes.addFlashAttribute("name", name);
        }
        else {
            Category category = Category.builder()
                    .name(name)
                    .build();

            categoryService.saveCategory(category);

            int idC = categoryService.selectCategoryByName(name).getCategoryId();
            message = "Thêm thành công danh mục [" + name + "] có mã [" + idC + "]";
            redirectAttributes.addFlashAttribute("message", message);
        }

        return "redirect:/dashboard/manager-category/add-category";
    }

    @GetMapping("/edit-category")
    public String showEditCategoryPage(@RequestParam(required = false) String categoryId,
                                       Model model) {
        int cId;
        try {
            cId = Integer.parseInt(categoryId);
        } catch (NumberFormatException e) {
            return "redirect:/dashboard/manager-category";
        }

        Category category = categoryService.selectCategoryById(cId);
        if (category == null) {
            return "redirect:/dashboard/manager-category";
        }

        model.addAttribute("category", category);

        return "admin/category/edit_category_page";
    }

    @PostMapping("/edit-category")
    public String editCategory(@RequestParam int categoryId,
                               @RequestParam String name,
                               RedirectAttributes redirectAttributes) {
        String message;
        String messageError;
        Category category = categoryService.selectCategoryById(categoryId);
        if (!category.getName().equals(name) && categoryService.checkNameExists(name)) {
            messageError = "Đã tồn tại một danh mục có tên [" + name + "]";
            redirectAttributes.addFlashAttribute("messageError", messageError);
            redirectAttributes.addFlashAttribute("name", name);

            return "redirect:/dashboard/manager-category/edit-category?categoryId=" + categoryId;
        }

        category.setName(name);
        categoryService.saveCategory(category);
        message = "Chỉnh sửa thành công danh mục có mã [" + categoryId + "]";
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/dashboard/manager-category";
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam int categoryId,
                                 @RequestParam String name,
                                 RedirectAttributes redirectAttributes) {
        String message;
        String messageError;
        Category category = categoryService.selectCategoryById(categoryId);
        try {
            categoryService.deleteCategory(category);
            message = "Xoá thành công danh mục [" + categoryId + " - " + name + "]";
            redirectAttributes.addFlashAttribute("message", message);
        }
        catch (Exception e) {
            messageError = "Trong danh mục [" + categoryId + " - " + name + "] vẫn còn sản phẩm, hãy xoá sản phẩm thuộc danh mục này trước!";
            redirectAttributes.addFlashAttribute("messageError", messageError);
        }

        return "redirect:/dashboard/manager-category";
    }

}
