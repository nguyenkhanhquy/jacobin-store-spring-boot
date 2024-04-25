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
public class LineItem {

    @Id
    @Column(name = "line_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lineItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity = 1;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public double getTotal() {
        return product.getPrice() * quantity;
    }

    public String getTotalCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(this.getTotal());
    }

}
