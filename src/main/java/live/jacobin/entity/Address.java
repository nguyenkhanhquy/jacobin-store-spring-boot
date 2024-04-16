package live.jacobin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private String province;

    private String district;

    private String subDistrict;

    private String street;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
