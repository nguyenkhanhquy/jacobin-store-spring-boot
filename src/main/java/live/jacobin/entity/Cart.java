package live.jacobin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<LineItem> items = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public int getCount() {
        int totalCount = 0;
        for (LineItem item : items) {
            totalCount += item.getQuantity();
        }
        return totalCount;
    }

    public double getTotal() {
        double total = 0.0;
        for (LineItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public String getTotalCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(this.getTotal());
    }

    public LineItem addItem(LineItem item) {
        item.setCart(this);
        int productId = item.getProduct().getProductId();
        int quantity = item.getQuantity();

        for (LineItem cartItem : items) {
            if (cartItem.getProduct().getProductId() == productId) {
                int quantityCartItem = cartItem.getQuantity();
                quantity += quantityCartItem;
                cartItem.setQuantity(quantity);
                return cartItem;
            }
        }
        items.add(item);
        return item;
    }

    public LineItem updateItem(LineItem item) {
        int productId = item.getProduct().getProductId();
        int quantity = item.getQuantity();

        LineItem cartItemUpdate = null;

        for (LineItem cartItem : items) {
            if (cartItem.getProduct().getProductId() == productId) {
                int quantityCartItem = cartItem.getQuantity();
                if (quantity == -1) {
                    quantity = quantityCartItem;
                }
                cartItem.setQuantity(quantity);
                cartItemUpdate = cartItem;
                break;
            }
        }
        return cartItemUpdate;
    }

    public int removeItem(LineItem item) {
        int productId = item.getProduct().getProductId();
        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getProductId() == productId) {
                items.remove(i);
                return lineItem.getLineItemId();
            }
        }
        return 0;
    }

}
