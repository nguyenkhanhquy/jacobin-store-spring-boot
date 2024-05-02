package live.jacobin.controller.admin;

import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/manager-staff")
public class ManagerStaffController {

    private final UserService userService;

    @Autowired
    public ManagerStaffController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showManagerCustomerPage(Model model) {
        // Lấy danh sách Staff từ service
        List<User> listU = userService.selectUserByRole(Role.STAFF);
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListU", listU);

        return "admin/staff/manager_staff_page";
    }
}
