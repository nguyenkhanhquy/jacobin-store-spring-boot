package live.jacobin.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.*;
import live.jacobin.service.CartService;
import live.jacobin.service.LineItemService;
import live.jacobin.service.OrderItemService;
import live.jacobin.service.OrderService;
import live.jacobin.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    private final HttpServletRequest request;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final LineItemService lineItemService;
    private final OrderService orderService;

    @Autowired
    public OrderController(HttpServletRequest request, CartService cartService, OrderItemService orderItemService, LineItemService lineItemService, OrderService orderService) {
        this.request = request;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.lineItemService = lineItemService;
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String order() {
        return "redirect:/home";
    }

    @PostMapping("/order")
    public String handleOrder(@RequestParam String phone,
                              @RequestParam String address,
                              @RequestParam String shippingMethod,
                              @RequestParam String paymentMethod,
                              @RequestParam String totalPrice,
                              Model model) {
        HttpSession session = request.getSession();
        User user = SessionUtil.getLoginedUser(session);

        Date currentDate = new Date();

        Order order = Order.builder()
                .orderTrack(OrderTrack.PROCESSING)
                .date(currentDate)
                .phone(phone)
                .address(address)
                .user(user)
                .details(new ArrayList<>())
                .shippingMethod(ShippingMethod.valueOf(shippingMethod))
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .totalPrice(totalPrice)
                .build();
        orderService.saveOrder(order);

        Cart cart = cartService.selectCartByUser(SessionUtil.getLoginedUser(session));
        List<LineItem> items = cart.getItems();
        // Tạo một danh sách tạm thời để lưu trữ các item cần xóa khỏi giỏ hàng sau khi đặt hàng
        List<LineItem> itemsToDelete = new ArrayList<>(items);

        for (LineItem item : itemsToDelete) {
            OrderItem orderItem = new OrderItem();
            orderItem.setNameProduct(item.getProduct().getName());
            orderItem.setSize(item.getProduct().getSize());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());

            orderItem.setOrder(order);
            OrderItem orderItemDB = order.addItem(orderItem);
            orderItemService.saveOrderItem(orderItemDB);

            int lineItemId = cart.removeItem(item);
            lineItemService.deleteLineItem(lineItemService.selectLineItemById(lineItemId));

            cartService.saveCart(cart);
        }
        orderService.saveOrder(order);
        session.setAttribute("cart", cart);

        String message = "Đặt hàng thành công!";
        model.addAttribute("message", message);

        return "common/success_page";
    }
}
