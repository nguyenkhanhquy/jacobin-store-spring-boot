package live.jacobin.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import live.jacobin.util.SessionUtil;

import java.io.IOException;

public class ManagerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Ép kiểu ServletRequest thành HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) request;

        // Ép kiểu ServletResponse thành HttpServletResponse
        HttpServletResponse resp = (HttpServletResponse) response;

        // Lấy đối tượng HttpSession từ request
        HttpSession session = req.getSession();
        User userInSession = SessionUtil.getLoginedUser(session);

        // Kiểm tra nếu là manager thì cho qua, nếu không thì chuyển hướng sang home
        if (userInSession == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (userInSession.getRole() == Role.MANAGER) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

}
