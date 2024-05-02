package live.jacobin.service;

import live.jacobin.entity.Role;
import live.jacobin.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void deleteUser(User user);

    User selectUserById(int userId);

    User selectUserByUserName(String userName);

    User selectUserByEmail(String email);

    boolean checkEmailExists(String email);

    boolean checkPhoneExists(String phone);

    boolean checkUserNameExists(String userName);

    List<User> selectUserByRole(Role role);

}