package live.jacobin.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import live.jacobin.util.PasswordEncryptorUtil;
import live.jacobin.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangePasswordController {

    private final HttpServletRequest request;

    private final UserService userService;

    @Autowired
    public ChangePasswordController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "common/change_password_page";
    }

    @PostMapping("/change-password")
    public String requestChangePassword(@RequestParam String password,
                                        @RequestParam String newPassword,
                                        @RequestParam String newPasswordAgain,
                                        Model model) {

        HttpSession session = request.getSession();
        User user = SessionUtil.getLoginedUser(session);

        String message;
        if (!PasswordEncryptorUtil.toSHA1(password).equals(user.getPassword())) {
            message = "Mật khẩu hiện tại không chính xác!";
        } else if (!newPassword.equals(newPasswordAgain)) {
            message = "Mật khẩu nhập lại không khớp!";
        } else {
            message = "Đổi mật khẩu thành công!";
            newPassword = PasswordEncryptorUtil.toSHA1(newPassword);
            user.setPassword(newPassword);
            userService.saveUser(user);
            model.addAttribute("message", message);
            return "common/success_page";
        }

        model.addAttribute("message", message);

        return "common/change_password_page";
    }

}
