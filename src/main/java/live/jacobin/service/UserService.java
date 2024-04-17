package live.jacobin.service;

import live.jacobin.entity.User;

public interface UserService {

    void saveUser(User user);

    void deleteUser(User user);

    User selectUserById(int userId);

    User selectUserByUserName(String userName);

    User selectUserByEmail(String email);

    public boolean checkEmailExists(String email);

    public boolean checkPhoneExists(String phone);

    public boolean checkUserNameExists(String userName);

}