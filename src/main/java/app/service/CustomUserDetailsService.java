package app.service;
import app.entity.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user by username: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("✅ User tìm thấy: " + user.getUsername());
        System.out.println("🔑 Mật khẩu trong DB: " + user.getPassword());


        String role = user.getRole();

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())  // Sử dụng mật khẩu đã mã hóa trong cơ sở dữ liệu
                .authorities(new SimpleGrantedAuthority(role)) // Cấp quyền theo vai trò từ cơ sở dữ liệu
                .build();
    }
}
