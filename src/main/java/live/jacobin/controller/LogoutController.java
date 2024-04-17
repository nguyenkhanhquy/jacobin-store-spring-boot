package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/logout")
    public String logout(Model model) {
//        // Xóa Cookie
//        CookieUtil.deleteUserCookie(resp);

        // Xoá Session
        HttpSession session = request.getSession();
        session.removeAttribute("loginedUser");
//        session.removeAttribute("cart");

        String message = "Đăng xuất thành công!";
        model.addAttribute("message", message);

        return "customer/success_page";
    }

}
