package live.jacobin.controller.admin;

import live.jacobin.entity.Order;
import live.jacobin.entity.OrderTrack;
import live.jacobin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/dashboard/manager-revenue")
public class RevenueController {

    private final OrderService orderService;

    @Autowired
    public RevenueController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showManagerRevenuePage(Model model) {
        // Lấy danh sách Order theo tháng hiện tại từ service
        List<Order> listO = orderService.selectOrdersInCurrentMonth();
        // Loại bỏ các đơn hàng có OrderTrack là "PROCESSING"
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : listO) {
            if (!order.getOrderTrack().equals(OrderTrack.PROCESSING)) {
                filteredOrders.add(order);
            }
        }

        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListO", filteredOrders);

        // Tính tổng giá tiền của các đơn hàng trong danh sách
        double totalRevenue = 0.0;
        for (Order order : filteredOrders) {
            totalRevenue += order.getTotalPrice();
        }

        // Định dạng tổng giá trị doanh thu
        String formattedTotalRevenue = getTotalRevenueCurrencyFormat(totalRevenue);

        // Đặt totalRevenue vào model để truyền tới view
        model.addAttribute("totalRevenue", formattedTotalRevenue);

        return "admin/revenue/manager_revenue_page";
    }

    @PostMapping
    public String filterManagerRevenue(@RequestParam(required = false) Integer ngay,
                                       @RequestParam(required = false) Integer thang,
                                       @RequestParam(required = false) Integer nam,
                                       Model model) {
        List<Order> listO = null;

        if (ngay != null) {
            // Lấy danh sách Order theo ngày, tháng và năm từ service
            listO = orderService.selectOrdersInDay(nam, thang, ngay);
        } else if (thang != null) {
            // Lấy danh sách Order theo tháng và năm từ service
            listO = orderService.selectOrdersInMonth(nam, thang);
        } else {
            // Lấy danh sách Order theo năm từ service
            listO = orderService.selectOrdersInYear(nam);
        }

        // Loại bỏ các đơn hàng có OrderTrack là "PROCESSING"
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : listO) {
            if (!order.getOrderTrack().equals(OrderTrack.PROCESSING)) {
                filteredOrders.add(order);
            }
        }

        // Đặt danh sách vào model để truyền tới view
        model.addAttribute("ListO", filteredOrders);

        // Tính tổng giá tiền của các đơn hàng trong danh sách
        double totalRevenue = 0.0;
        for (Order order : filteredOrders) {
            totalRevenue += order.getTotalPrice();
        }

        // Định dạng tổng giá trị doanh thu
        String formattedTotalRevenue = getTotalRevenueCurrencyFormat(totalRevenue);

        // Đặt totalRevenue vào model để truyền tới view
        model.addAttribute("totalRevenue", formattedTotalRevenue);

        model.addAttribute("ngay", ngay);
        model.addAttribute("thang", thang);
        model.addAttribute("nam", nam);

        return "admin/revenue/manager_revenue_page";
    }


    // Phương thức để định dạng tổng giá trị doanh thu
    private String getTotalRevenueCurrencyFormat(double totalRevenue) {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(totalRevenue);
    }
}
