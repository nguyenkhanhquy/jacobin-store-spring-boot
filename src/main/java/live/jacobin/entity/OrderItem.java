package live.jacobin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.util.Locale;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

//    @OneToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    private String nameProduct;

    private String size;

    private int quantity = 1;

    private double price;

    @ManyToOne
    @JoinColumn(name = "customer_order_id", nullable = false)
    private Order order;

    public double getTotal() {
        return price * quantity;
    }

    public String getPriceCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(price);
    }

    public String getTotalCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(this.getTotal());
    }

}
