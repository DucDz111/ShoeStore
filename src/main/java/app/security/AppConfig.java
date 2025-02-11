package app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Hoặc bất kỳ encoder nào bạn muốn
    }
    @Bean
    public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
        return new SecurityContextPersistenceFilter();
    }
}

