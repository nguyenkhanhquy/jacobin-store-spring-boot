package live.jacobin.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.entity.User;
import live.jacobin.service.CartService;
import live.jacobin.service.UserService;
import live.jacobin.util.MailUtilGmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Random;

@Controller
public class VerifyOTPController {

    private final HttpServletRequest request;

    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public VerifyOTPController(HttpServletRequest request, UserService userService, CartService cartService) {
        this.request = request;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping(value = {"/verify-otp", "/verify-otp/*"})
    public String verifyOTP() {
        HttpSession session = request.getSession();

        if(session.getAttribute("action") == "forgot-password") {
            return "common/verify_otp_page";
        }
        
        return "redirect:/home";
    }

    @PostMapping("/verify-otp/confirm-forgot-password")
    public String confirmOTPConfirmForgotPassword(@RequestParam String otp, Model model) {
        HttpSession session = request.getSession();
        String otpSend = (String) session.getAttribute("otpSend");

        String messageError;
        if (otp.equals(otpSend)) {
            session.removeAttribute("otpSend");
            return "redirect:/forgot-password/create-new-password";
        } else {
            messageError = "Mã OTP không hợp lệ!";
        }

        model.addAttribute("messageError", messageError);

        return "common/verify_otp_page";
    }

    @PostMapping("/verify-otp/confirm")
    public String verifyOTPConfirm(@RequestParam String otp, Model model) throws MessagingException, UnsupportedEncodingException {
        HttpSession session = request.getSession();

        Long otpExpirationTime = (Long) session.getAttribute("otpExpirationTime");
        if (otpExpirationTime != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime > otpExpirationTime) {
                // Thuộc tính "otpSend" đã hết hạn, loại bỏ nó khỏi session
                session.removeAttribute("otpSend");
                session.removeAttribute("otpExpirationTime");
            }
        }

        String otpSend = (String) session.getAttribute("otpSend");

        String message;
        String messageError;

        if (otp.equals(otpSend)) {
            User user = (User) session.getAttribute("user");
            // Lưu user mới
            userService.saveUser(user);

            // Thêm cart cho user mới
            Cart cart = new Cart();
            cart.setUser(user);
            cartService.saveCart(cart);

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

            session.removeAttribute("user");
            session.removeAttribute("otpSend");

            return "common/success_page";
        } else {
            messageError = "Mã OTP không hợp lệ!";
            model.addAttribute("messageError", messageError);
        }

        return "common/verify_otp_page";
    }

    @PostMapping("/verify-otp/send-code")
    public String sendCode(Model model) throws MessagingException, UnsupportedEncodingException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Tạo và lưu giá trị OTP vào session
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        String otpString = String.valueOf(otp);
        session.setAttribute("otpSend", otpString);

        long otpExpirationTime = System.currentTimeMillis() + (300 * 1000); // Thời gian hiện tại + 5 phút (300 giây)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = sdf.format(otpExpirationTime);
        session.setAttribute("otpExpirationTime", otpExpirationTime);

        // Gửi email đến email của user
        String to = user.getEmail();
        String from = "shop.javamail@gmail.com";
        String subject = "Xác minh email";
        String body = "Chào " + user.getFirstName() + ",\n\n"
                + "Mã OTP của bạn là: " + otpString + ".\n\n"
                + "Mã OTP khả dụng đến: " + formattedDate + ".";
        boolean isBodyHTML = false;
        MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

        String message = "Đã gửi mã OTP tới email của bạn!";
        model.addAttribute("message", message);

        return "common/verify_otp_page";
    }

}
