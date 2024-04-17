package live.jacobin.util;

import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.User;

public class SessionUtil {

    // Lưu trữ thông tin người dùng đã login vào Session.
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        // Trên Thymeleaf có thể truy cập thông qua ${session.loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }

    // Lấy thông tin người dùng lưu trữ trong Session.
    public static User getLoginedUser(HttpSession session) {
        return (User) session.getAttribute("loginedUser");
    }

}
