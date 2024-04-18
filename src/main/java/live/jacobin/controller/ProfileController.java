package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import live.jacobin.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final HttpServletRequest request;

    private final UserService userService;

    public ProfileController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {

        HttpSession session = request.getSession();
        User user = SessionUtil.getLoginedUser(session);

        model.addAttribute("user", user);

        return "customer/profile_page";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String dateOfBirth,
                                @RequestParam String email,
                                @RequestParam String phone,
                                Model model) {

        HttpSession session = request.getSession();
        User userS = SessionUtil.getLoginedUser(session);

        String message;
        if (!email.equals(userS.getEmail()) && userService.checkEmailExists(email)) {
            message = "Địa chỉ Email đã tồn tại. " + "Vui lòng điền một địa chỉ Email khác.";
        } else if (!phone.equals(userS.getPhone()) && userService.checkPhoneExists(phone)) {
            message = "Số điện thoại đã tồn tại. " + "Vui lòng điền số điện thoại khác.";
        } else {
            userS.setFirstName(firstName);
            userS.setLastName(lastName);
            userS.setDateOfBirth(dateOfBirth);
            userS.setEmail(email);
            userS.setPhone(phone);
            userService.saveUser(userS);

            message = "Cập nhật thành công!";
            model.addAttribute("message", message);

            return "customer/success_page";
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(userS.getEmail());
        user.setPhone(userS.getPhone());
        user.setRole(userS.getRole());

        model.addAttribute("user", user);
        model.addAttribute("message", message);

        return "customer/profile_page";
    }

}
