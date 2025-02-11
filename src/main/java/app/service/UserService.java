package app.service;

import app.entity.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void registerUser(User user) {
        // Mã hóa mật khẩu trước khi lưu vào database
        if (!user.getPassword().startsWith("$2a$")) {


            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRole("ROLE_USER");  // Thiết lập vai trò cho người dùng
        userRepository.save(user);  // Lưu người dùng vào database
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElse(null);
    }
    public static void main(String[] args) {
        // Mã hóa mật khẩu
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("123");

        // Sau đó, thêm vào cơ sở dữ liệu với mật khẩu đã mã hóa
        System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
    }
}
