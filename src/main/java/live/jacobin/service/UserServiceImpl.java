package live.jacobin.service;

import live.jacobin.entity.Role;
import live.jacobin.entity.User;
import live.jacobin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User selectUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User selectUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User selectUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkPhoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public boolean checkUserNameExists(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public List<User> selectUserByRole(Role role) {
        return userRepository.findByRole(role);
    }

}
