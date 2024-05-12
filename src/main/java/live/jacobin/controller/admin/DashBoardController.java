package live.jacobin.controller.admin;

import live.jacobin.entity.Product;
import live.jacobin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashBoardController {

    private final ProductService productService;

    @Autowired
    public DashBoardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Model model) {
        // Lấy danh sách Product từ service
        List<Product> listP = productService.selectAllProduct();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListP", listP);

        return "admin/dashboard/dashboard_page";
    }

}
