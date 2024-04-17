package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.util.SessionUtil;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private HttpServletRequest request;

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "customer/login_page";
    }

    @PostMapping("/login")
    public String requestLogin(@RequestParam String userName,
                               @RequestParam String password,
                               @RequestParam(required = false) String rememberMe, Model model) {

        boolean rmbm = "Y".equals(rememberMe);

        User user = userService.selectUserByUserName(userName);

        String message;

        // Trường hợp có lỗi thì forward (chuyển hướng) tới login_page
        if (user == null || !user.getPassword().equals(password)) {
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

//            // Nếu người dùng tích chọn "Ghi nhớ tài khoản này" thì lưu thông tin người dùng vào Cookie
//            if (rememberMe) {
//                CookieUtil.storeUserCookie(resp, user);
//            }
//
//            if (user.getRole().getRoleId() != 1) {
//                // Lấy thông tin giỏ hàng của người dùng
//                Cart cart = CartDB.selectCartByUser(SessionUtil.getLoginedUser(session));
//                session.setAttribute("cart", cart);
//            }
            // Forward (Chuyển hướng) tới trang home_page
            return "redirect:/home";
        }
    }

}
