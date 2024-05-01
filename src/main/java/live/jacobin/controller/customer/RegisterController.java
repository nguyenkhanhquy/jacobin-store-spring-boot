package live.jacobin.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.dto.UserDTO;
import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import live.jacobin.util.PasswordEncryptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final HttpServletRequest request;

    private final UserService userService;

    @Autowired
    public RegisterController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "customer/register_page";
    }

    @PostMapping("/register")
    public String requestRegister(@RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String dateOfBirth,
                                  @RequestParam String address,
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  @RequestParam String userName,
                                  @RequestParam String password,
                                  @RequestParam String passwordAgain,
                                  Model model) {

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .email(email)
                .phone(phone)
                .userName(userName)
                .build();

        String message;
        if (userService.checkEmailExists(user.getEmail())) {
            message = "Địa chỉ Email đã tồn tại. Vui lòng điền một địa chỉ Email khác.";
        } else if (userService.checkPhoneExists(user.getPhone())) {
            message = "Số điện thoại đã tồn tại. Vui lòng điền số điện thoại khác.";
        } else if (userService.checkUserNameExists(user.getUserName())) {
            message = "Tên đăng nhập đã tồn tại. Vui lòng điền tên đăng nhập khác.";
        } else if (!password.equals(passwordAgain)) {
            message = "Mật khẩu nhập lại không khớp. Vui lòng nhập lại.";
        } else {
            user.setPassword(PasswordEncryptorUtil.toSHA1(password));
            user.setRole(Role.CUSTOMER);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            return "common/verify_otp_page";
        }

        UserDTO userDTO = new UserDTO(user);

        model.addAttribute("user", userDTO);
        model.addAttribute("message", message);

        return "customer/register_page";
    }

}
