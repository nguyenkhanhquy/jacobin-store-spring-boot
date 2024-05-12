package live.jacobin.filter;

import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.User;
import live.jacobin.service.UserService;
import live.jacobin.util.CookieUtil;
import live.jacobin.util.SessionUtil;
import java.io.IOException;

@Component
public class CookieFilter implements Filter {

    private final UserService userService;

    public CookieFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Ép kiểu ServletRequest thành HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) request;

        // Lấy đối tượng HttpSession từ request
        HttpSession session = req.getSession();
        User userInSession = SessionUtil.getLoginedUser(session);

        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        // Cờ (flag) để kiểm tra Cookie.
        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null) {
            String userName = CookieUtil.getUserNameInCookie(req);

            if (userName != null) {
                User user = userService.selectUserByUserName(userName);
                SessionUtil.storeLoginedUser(session, user);
            }

            // Đánh dấu đã kiểm tra Cookie.
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }

        chain.doFilter(request, response);
    }
}
