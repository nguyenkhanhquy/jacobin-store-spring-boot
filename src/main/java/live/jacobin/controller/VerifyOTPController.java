package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.entity.User;
import live.jacobin.service.CartService;
import live.jacobin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class VerifyOTPController {

    private final HttpServletRequest request;

    private final UserService userService;
    private final CartService cartService;

    public VerifyOTPController(HttpServletRequest request, UserService userService, CartService cartService) {
        this.request = request;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping(value = {"/verify-otp", "/verify-otp/*"})
    public String verifyOTP() {
        return "redirect:/home";
    }

    @PostMapping("/verify-otp/confirm")
    public String verifyOTPConfirm(@RequestParam String otp, Model model) {
        HttpSession session = request.getSession();
        String otpSend = (String) session.getAttribute("otpSend");

        String message;

        if (otp.equals(otpSend)) {
            User user = (User) session.getAttribute("user");
            // Lưu user mới
            userService.saveUser(user);

            // Thêm cart cho user mới
            Cart cart = new Cart();
            cart.setUser(user);
            cartService.saveCart(cart);

            session.removeAttribute("user");
            session.removeAttribute("otpSend");

            message = "Đăng ký thành công!";
            model.addAttribute("message", message);

            return "customer/success_page";
        } else {
            message = "Mã OTP không hợp lệ!";
        }

        model.addAttribute("message", message);

        return "customer/verify_otp_page";
    }

    @PostMapping("/verify-otp/send-code")
    public String sendCode(Model model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Đặt thời gian sống của session là 5 phút (300 giây)
        session.setMaxInactiveInterval(300);

        // Tạo và lưu giá trị OTP vào session
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        String otpString = String.valueOf(otp);
        session.setAttribute("otpSend", otpString);

        // Gửi email đến email của user
        System.out.println("OTP sent: " + otpString);

        String message = "Đã gửi mã OTP tới email của bạn!";
        model.addAttribute("message", message);

        return "customer/verify_otp_page";
    }

}
