package live.jacobin.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String userName;

    private String phone;

    private String email;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String address;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
    }

}
