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
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<LineItem> items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Cart() {
        items = new ArrayList<LineItem>();
    }

    public int getCount() {
        return items.size();
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

//    public void addItem(LineItem item) {
//        int productId = item.getProduct().getProductId();
//        int quantity = item.getQuantity();
//        for (LineItem cartItem : items) {
//            if (cartItem.getProduct().getProductId() == productId) {
//                int quantityCartItem = cartItem.getQuantity();
//                quantity += quantityCartItem;
//                cartItem.setQuantity(quantity);
//                LineItemDB.update(cartItem);
//                return;
//            }
//        }
//        items.add(item);
//        LineItemDB.insert(item);
//    }
//
//    public void updateItem(LineItem item) {
//        int productId = item.getProduct().getProductId();
//        int quantity = item.getQuantity();
//        for (LineItem cartItem : items) {
//            if (cartItem.getProduct().getProductId() == productId) {
//                int quantityCartItem = cartItem.getQuantity();
//                if (quantity == -1) {
//                    quantity = quantityCartItem;
//                }
//                cartItem.setQuantity(quantity);
//                LineItemDB.update(cartItem);
//                return;
//            }
//        }
//        items.add(item);
//    }

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
