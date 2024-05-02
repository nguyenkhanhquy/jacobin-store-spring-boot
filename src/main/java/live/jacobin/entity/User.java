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
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String userName;

    private String password;

    private String phone;

    private String email;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean locked = false;

}
