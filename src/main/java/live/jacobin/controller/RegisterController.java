package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private HttpServletRequest request;

    private final UserService userService;

    public RegisterController(UserService userService) {
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
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  @RequestParam String userName,
                                  @RequestParam String password,
                                  @RequestParam String passwordAgain,
                                  Model model) {

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(Role.CUSTOMER);

        String message;
        if (userService.checkEmailExists(user.getEmail())) {
            message = "Địa chỉ Email đã tồn tại. " + "Vui lòng điền một địa chỉ Email khác.";
        } else if (userService.checkPhoneExists(user.getPhone())) {
            message = "Số điện thoại đã tồn tại. " + "Vui lòng điền số điện thoại khác.";
        } else if (userService.checkUserNameExists(user.getUserName())) {
            message = "Tên đăng nhập đã tồn tại. " + "Vui lòng điền tên đăng nhập khác.";
        } else if (!user.getPassword().equals(passwordAgain)) {
            message = "Mật khẩu không khớp. " + "Vui lòng nhập lại.";
        } else {
            password = PasswordEncryptorUtil.toSHA1(password);
            user.setPassword(password);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            message = "";
            System.out.println(password);
            System.out.println("verifyOTP");
        }

        model.addAttribute("user", user);
        model.addAttribute("message", message);

        return "customer/register_page";
    }

}
