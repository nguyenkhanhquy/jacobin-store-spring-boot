package live.jacobin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.entity.User;
import live.jacobin.service.CartService;
import live.jacobin.service.UserService;
import live.jacobin.util.MailUtilGmail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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
    public String verifyOTPConfirm(@RequestParam String otp, Model model) throws MessagingException, UnsupportedEncodingException {
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

            // Gửi email đến email chào mừng email của user
            String to = user.getEmail();
            String from = "shop.javamail@gmail.com";
            String subject = "Chào mừng đến với Jacobin Store";
            String body = "Chào " + user.getFirstName() + ",\n\n"
                    + "Chúng tôi rất vui mừng thông báo rằng bạn đã đăng ký thành công tài khoản mới tại Jacobin Store!\n\n"
                    + "Chào mừng bạn đến với cửa hàng của chúng tôi và cảm ơn bạn đã chọn chúng tôi để trải nghiệm mua sắm trực tuyến.\n\n"
                    + "Hãy khám phá thế giới mua sắm tuyệt vời tại Jacobin Store ngay bây giờ.\n\n"
                    + "Chúc bạn có những trải nghiệm mua sắm thú vị và hài lòng!\n\n"
                    + "Trân trọng, Jacobin Store.";
            boolean isBodyHTML = false;
            MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

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
    public String sendCode(Model model) throws MessagingException, UnsupportedEncodingException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Đặt thời gian sống của session là 5 phút (300 giây)
        session.setMaxInactiveInterval(300);

        // Tạo và lưu giá trị OTP vào session
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        String otpString = String.valueOf(otp);
        session.setAttribute("otpSend", otpString);

        System.out.println("OTP sent: " + otpString);
        // Gửi email đến email của user
        String to = user.getEmail();
        String from = "shop.javamail@gmail.com";
        String subject = "Xác minh email";
        String body = "Chào " + user.getFirstName() + ",\n\n"
                + "Mã OTP của bạn là: " + otpString;
        boolean isBodyHTML = false;
        MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

        String message = "Đã gửi mã OTP tới email của bạn!";
        model.addAttribute("message", message);

        return "customer/verify_otp_page";
    }

}
