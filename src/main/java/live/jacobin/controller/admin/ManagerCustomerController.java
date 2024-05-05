package live.jacobin.controller.admin;

import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
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
@RequestMapping("/dashboard/manager-customer")
public class ManagerCustomerController {

    private final UserService userService;

    @Autowired
    public ManagerCustomerController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showManagerCustomerPage(Model model) {
        // Lấy danh sách Customer từ service
        List<User> listU = userService.selectUserByRole(Role.CUSTOMER);
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListU", listU);

        return "admin/customer/manager_customer_page";
    }

    @GetMapping("/detail-customer")
    public String showManagerDetailCustomerPage(@RequestParam(required = false) String userId,
                                             Model model) {
        int uId;
        try {
            uId = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return "redirect:/dashboard/manager-customer";
        }

        // Lấy user từ service
        User user = userService.selectUserById(uId);
        if (user == null || user.getRole() != Role.CUSTOMER) {
            return "redirect:/dashboard/manager-customer";
        }

        // Đặt user vào model để truyền tới view
        model.addAttribute("user", user);

        return "admin/customer/manager_detail_customer_page";
    }

    @PostMapping("/lock")
    public String lockCustomer(@RequestParam int userId,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        String message;
        String messageError;
        User user = userService.selectUserById(userId);
        try {
            user.setLocked(true);
            userService.saveUser(user);
            message = "Khóa thành công tài khoản của khách hàng [" + userId + " - " + email + "]";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            messageError = "Khóa tài khoản của khách hàng [" + userId + " - " + email + "] không thành công";
            redirectAttributes.addFlashAttribute("messageError", messageError);
        }

        return "redirect:/dashboard/manager-customer";
    }
}
