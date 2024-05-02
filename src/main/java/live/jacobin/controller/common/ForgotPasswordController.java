package live.jacobin.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class ForgotPasswordController {

    private final HttpServletRequest request;

    private final UserService userService;

    @Autowired
    public ForgotPasswordController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "common/forgot_password_page";
    }

    @PostMapping("/forgot-password")
    public String requestForgotPassword(@RequestParam String email, Model model) {
        User user = userService.selectUserByEmail(email);

        String message;
        if (user == null) {
            message = "Email không tồn tại trên hệ thống!";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("action", "forgot-password");
            return "redirect:/verify-otp";
        }

        model.addAttribute("email", email);
        model.addAttribute("message", message);

        return "common/forgot_password_page";
    }

    @GetMapping("/forgot-password/create-new-password")
    public String showCreateNewPasswordPage() {
        HttpSession session = request.getSession();

        if(session.getAttribute("action") == "forgot-password") {
            return "common/create_new_password_page";
        }

        return "redirect:/home";
    }

    @PostMapping("/forgot-password/create-new-password")
    public String requestCreateNewPassword(@RequestParam String newPassword,
                                           @RequestParam String newPasswordAgain,
                                           Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String message;
        if (!newPassword.equals(newPasswordAgain)) {
            message = "Mật khẩu nhập lại không khớp.";
        } else {
            message = "Đặt lại mật khẩu mới thành công!";
            newPassword = PasswordEncryptorUtil.toSHA1(newPassword);
            user.setPassword(newPassword);
            userService.saveUser(user);

            session.removeAttribute("action");
            session.removeAttribute("user");

            model.addAttribute("message", message);

            return "common/success_page";
        }

        model.addAttribute("message", message);

        return "common/create_new_password_page";
    }

}
