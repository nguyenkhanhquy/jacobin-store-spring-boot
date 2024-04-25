package live.jacobin.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.dto.UserDTO;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import live.jacobin.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final HttpServletRequest request;

    private final UserService userService;

    @Autowired
    public ProfileController(HttpServletRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {

        HttpSession session = request.getSession();
        User user = SessionUtil.getLoginedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        UserDTO userDTO = new UserDTO(user);

        model.addAttribute("user", userDTO);

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
        User user = SessionUtil.getLoginedUser(session);

        String message;
        if (!phone.equals(user.getPhone()) && userService.checkPhoneExists(phone)) {
            message = "Số điện thoại đã tồn tại. " + "Vui lòng điền số điện thoại khác.";

            UserDTO userDTO = new UserDTO(user);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setDateOfBirth(dateOfBirth);
            userDTO.setAddress(address);

            model.addAttribute("user", userDTO);
            model.addAttribute("message", message);

            return "common/profile_page";
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setAddress(address);
        user.setPhone(phone);
        userService.saveUser(user);

        message = "Cập nhật thành công!";
        model.addAttribute("message", message);

        return "common/success_page";
    }

}
