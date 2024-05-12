package live.jacobin.controller.admin;

import jakarta.servlet.http.HttpSession;
import live.jacobin.dto.UserDTO;
import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import live.jacobin.util.PasswordEncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/add-staff")
    public String showAddStaffPage() {
        return "admin/staff/add_staff_page";
    }

    @PostMapping("/add-staff")
    public String addStaff(@RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String dateOfBirth,
                                  @RequestParam String address,
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  @RequestParam String userName,
                                  @RequestParam String password,
                                  @RequestParam String passwordAgain,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .email(email)
                .phone(phone)
                .userName(userName)
                .build();

        String messageError;
        if (userService.checkEmailExists(user.getEmail())) {
            messageError = "Địa chỉ Email đã tồn tại. Vui lòng điền một địa chỉ Email khác.";
        } else if (userService.checkPhoneExists(user.getPhone())) {
            messageError = "Số điện thoại đã tồn tại. Vui lòng điền số điện thoại khác.";
        } else if (userService.checkUserNameExists(user.getUserName())) {
            messageError = "Tên đăng nhập đã tồn tại. Vui lòng điền tên đăng nhập khác.";
        } else if (!password.equals(passwordAgain)) {
            messageError = "Mật khẩu nhập lại không khớp. Vui lòng nhập lại.";
        } else {
            user.setPassword(PasswordEncryptorUtil.toSHA1(password));
            user.setRole(Role.STAFF);
            userService.saveUser(user);

            user = userService.selectUserByEmail(user.getEmail());
            String message = "Thêm thành công nhân viên [" + user.getUserId() + " - " + user.getEmail() + "]";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/dashboard/manager-staff/add-staff";
        }

        UserDTO userDTO = new UserDTO(user);

        model.addAttribute("user", userDTO);
        model.addAttribute("messageError", messageError);

        return "admin/staff/add_staff_page";
    }

    @GetMapping("/detail-staff")
    public String showManagerDetailStaffPage(@RequestParam(required = false) String userId,
                                             Model model) {
        int uId;
        try {
            uId = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return "redirect:/dashboard/manager-staff";
        }

        // Lấy user từ service
        User user = userService.selectUserById(uId);
        if (user == null || user.getRole() != Role.STAFF) {
            return "redirect:/dashboard/manager-staff";
        }

        // Đặt user vào model để truyền tới view
        model.addAttribute("user", user);

        return "admin/staff/manager_detail_staff_page";
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
            message = "Khóa thành công tài khoản của nhân viên [" + userId + " - " + email + "]";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            messageError = "Khóa tài khoản của nhân viên [" + userId + " - " + email + "] không thành công";
            redirectAttributes.addFlashAttribute("messageError", messageError);
        }

        return "redirect:/dashboard/manager-staff";
    }
}
