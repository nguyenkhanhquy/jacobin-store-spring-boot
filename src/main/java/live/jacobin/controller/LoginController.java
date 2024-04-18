package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.entity.Role;
import live.jacobin.service.CartService;
import live.jacobin.util.CookieUtil;
import live.jacobin.util.PasswordEncryptorUtil;
import live.jacobin.util.SessionUtil;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    private final UserService userService;
    private final CartService cartService;

    public LoginController(HttpServletRequest request, HttpServletResponse response, UserService userService, CartService cartService) {
        this.request = request;
        this.response = response;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "customer/login_page";
    }

    @PostMapping("/login")
    public String requestLogin(@RequestParam String userName,
                               @RequestParam String password,
                               @RequestParam(required = false) String rememberMe,
                               Model model) {

        User user = userService.selectUserByUserName(userName);

        String message;

        // Trường hợp có lỗi thì forward (chuyển hướng) tới login_page
        if (user == null || !user.getPassword().equals(PasswordEncryptorUtil.toSHA1(password))) {
            message = "Tên đăng nhập hoặc mật khẩu không đúng!";

            // Lưu các thông tin vào model trước khi forward
            model.addAttribute("message", message);

            // Forward (Chuyển hướng) tới trang login_page
            return "customer/login_page";
        }
        else {
            // Lưu thông tin người dùng vào Session
            HttpSession session = request.getSession();
            SessionUtil.storeLoginedUser(session, user);

            // Nếu người dùng tích chọn "Ghi nhớ tài khoản này" thì lưu thông tin người dùng vào Cookie
            if ("Y".equals(rememberMe)) {
                CookieUtil.storeUserCookie(response, user);
            }

            if (user.getRole() != Role.CUSTOMER) {
                return "redirect:https://www.google.com/";
            }

            // Forward (Chuyển hướng) tới trang home_page
            return "redirect:/home";
        }
    }

}
