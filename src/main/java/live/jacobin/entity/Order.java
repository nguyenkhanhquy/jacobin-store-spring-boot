package live.jacobin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Enumerated(EnumType.STRING)
    private OrderTrack orderTrack;

    @Temporal(TemporalType.DATE)
    private Date date;

    public String getOrderDateDefaultFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                new Locale.Builder().setLanguage("vi").setRegion("VN").build());
        return dateFormat.format(date);
    }

    private String phone;

    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> details = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private double totalPrice;

    public String totalPriceCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(totalPrice);
    }

    public double getTotal() {
        double total = 0.0;
        for (OrderItem item : details) {
            total += item.getTotal();
        }
        return total;
    }

    public String getTotalCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(this.getTotal());
    }

    public OrderItem addItem(OrderItem item) {
        details.add(item);
        return item;
    }

}
