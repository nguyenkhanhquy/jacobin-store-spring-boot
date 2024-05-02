package live.jacobin.controller.admin;

import live.jacobin.entity.Category;
import live.jacobin.entity.Order;
import live.jacobin.entity.OrderTrack;
import live.jacobin.service.OrderService;
import live.jacobin.util.MailUtilGmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/manager-order")
public class ManagerOrderController {

    private final OrderService orderService;

    @Autowired
    public ManagerOrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showManagerOrderPage(Model model) {
        // Lấy danh sách Order từ service
        List<Order> listO = orderService.selectAllOrders();
        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListO", listO);

        return "admin/order/manager_order_page";
    }

    @GetMapping("/detail-order")
    public String showManagerDetailOrderPage(@RequestParam(required = false) String orderId,
                                             Model model) {
        int oId;
        try {
            oId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            return "redirect:/dashboard/manager-order";
        }

        // Lấy order từ service
        Order order = orderService.selectOrderById(oId);
        if (order == null) {
            return "redirect:/dashboard/manager-order";
        }

        // Đặt order vào model để truyền tới view
        model.addAttribute("order", order);

        return "admin/order/manager_detail_order_page";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam int orderId,
                               RedirectAttributes redirectAttributes) {
        String message;
        String messageError;
        Order order = orderService.selectOrderById(orderId);
        try {
            order.setOrderTrack(OrderTrack.APPROVED);
            orderService.saveOrder(order);

            // Gửi email đến email của user
            String to = order.getUser().getEmail();
            String from = "shop.javamail@gmail.com";
            String subject = "Xác nhận đơn hàng";
            String body = "Chào " + order.getUser().getFirstName() + ",\n\n"
                    + "Chúng tôi báo rằng đơn hàng có mã [" + order.getOrderId() + "] của bạn đã được xác nhận!\n\n"
                    + "Đơn hàng sẽ sớm được gửi đến địa chỉ của bạn.\n\n"
                    + "Chúc bạn có những trải nghiệm mua sắm thú vị và hài lòng!\n\n"
                    + "Trân trọng, Jacobin Store.";
            boolean isBodyHTML = false;
            MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

            message = "Xác nhận thành công đơn hàng có mã [" + orderId + "]";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            messageError = "Xác nhận không thành công đơn hàng có mã [" + orderId + "]";
            redirectAttributes.addFlashAttribute("messageError", messageError);
        }

        return "redirect:/dashboard/manager-order";
    }
}
