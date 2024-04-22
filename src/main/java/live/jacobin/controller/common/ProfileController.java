package live.jacobin.controller.common;

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

        return "common/profile_page";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String dateOfBirth,
                                @RequestParam String address,
                                @RequestParam String phone,
                                Model model) {

        HttpSession session = request.getSession();
        User userS = SessionUtil.getLoginedUser(session);

        String message;
        if (!phone.equals(userS.getPhone()) && userService.checkPhoneExists(phone)) {
            message = "Số điện thoại đã tồn tại. " + "Vui lòng điền số điện thoại khác.";
        } else {
            userS.setFirstName(firstName);
            userS.setLastName(lastName);
            userS.setDateOfBirth(dateOfBirth);
            userS.setAddress(address);
            userS.setPhone(phone);
            userService.saveUser(userS);

            message = "Cập nhật thành công!";
            model.addAttribute("message", message);

            return "common/success_page";
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address);
        user.setPhone(userS.getPhone());
        user.setRole(userS.getRole());

        model.addAttribute("user", user);
        model.addAttribute("message", message);

        return "common/profile_page";
    }

}
