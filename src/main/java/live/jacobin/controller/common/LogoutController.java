package live.jacobin.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import live.jacobin.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Autowired
    public LogoutController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        // Xóa Cookie
        CookieUtil.deleteUserCookie(response);

        // Xoá Session
        HttpSession session = request.getSession();
        session.removeAttribute("loginedUser");
        session.removeAttribute("cart");

        String message = "Đăng xuất thành công!";
        model.addAttribute("message", message);

        return "common/success_page";
    }

}
