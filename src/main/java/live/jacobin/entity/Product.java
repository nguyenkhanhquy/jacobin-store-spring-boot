package live.jacobin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String name;

    private String image;

    private double price;

    private String title;

    @Column(length = 1000)
    private String description;

    private String size;

    @ElementCollection
    private List<String> sizes = new ArrayList<>(Arrays.asList("S", "M", "L", "XL", "XXL", "XXXL"));

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public String getPriceCurrencyFormat() {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currency = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currency.format(price);
    }

}
