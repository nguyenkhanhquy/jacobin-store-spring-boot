package live.jacobin.controller.admin;

import live.jacobin.entity.Category;
import live.jacobin.entity.Product;
import live.jacobin.service.CategoryService;
import live.jacobin.service.ProductService;
import live.jacobin.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/dashboard/manager-product")
public class ManagerProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ManagerProductController(final ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showManagerProductPage(Model model) {
        // Lấy danh sách Product từ service
        List<Product> listP = productService.selectAllProduct();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListP", listP);

        return "admin/product/manager_product_page";
    }

    @GetMapping("/add-product")
    public String showAddProductPage(Model model) {
        // Lấy danh sách Category từ service
        List<Category> listC = categoryService.selectAllCategory();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListC", listC);

        return "admin/product/add_product_page";
    }

    @PostMapping("/add-product")
    public String addProduct(@RequestParam String name,
                             @RequestParam int categoryId,
                             @RequestParam String size,
                             @RequestParam double price,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam("file") MultipartFile multipart,
                             RedirectAttributes redirectAttributes) {
        Category category = categoryService.selectCategoryById(categoryId);

        Product product = Product.builder()
                .name(name)
                .category(category)
                .size(size)
                .price(price)
                .title(title)
                .description(description)
                .build();
        productService.saveProduct(product);

        List<Product> listP = productService.selectAllProduct();
        int idP;
        if (!listP.isEmpty()) {
            Product lastP = listP.getLast();
            idP = lastP.getProductId();
        } else {
            idP = 1;
        }

        String fileName = multipart.getOriginalFilename();

        String message;
        String messageError;

        try {
            String newFileName = S3Util.urlFolder + idP + fileName.substring(fileName.lastIndexOf('.'));
            S3Util.uploadFile(newFileName, multipart.getInputStream());
            String urlImage = "https://" + S3Util.bucketName + ".s3.amazonaws.com/" + newFileName;
            product.setImage(urlImage);
            productService.saveProduct(product);
            message = "Thêm thành công sản phẩm có mã [" + idP + "]";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (IOException ex) {
            messageError = "Error uploading file: " + ex.getMessage();
            redirectAttributes.addFlashAttribute("messageError", messageError);
        }

        return "redirect:/dashboard/manager-product/add-product";
    }

}
