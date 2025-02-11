package app.security;

import app.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import app.security.CustomAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final CustomAuthenticationFailureHandler failureHandler;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder; // Tránh inject trong chính lớp này
    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          CustomAuthenticationFailureHandler failureHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.failureHandler = failureHandler;
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/register", "/auth/login", "/",  "/css/**", "/js/**", "/images/**","/favicon.ico").permitAll() // Cho phép truy cập đăng ký và đăng nhập
                .anyRequest().authenticated()  // Các yêu cầu khác cần phải đăng nhập
                .and()
                .formLogin()
                .loginPage("/auth/login")  // Trang đăng nhập tùy chỉnh
                .defaultSuccessUrl("/", true)// URL mặc định khi đăng nhập thành công
                .failureUrl("/auth/login?error=true")  // URL khi đăng nhập thất bại
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout=true");  // URL khi người dùng đăng xuất

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}


