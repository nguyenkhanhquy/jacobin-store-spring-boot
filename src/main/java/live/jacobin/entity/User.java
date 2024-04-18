package live.jacobin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String userName;

    private String password;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<Address> address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean locked = false;

}
