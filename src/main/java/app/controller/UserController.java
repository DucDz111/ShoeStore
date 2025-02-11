    package app.controller;

    import app.entity.User;
    import app.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.security.crypto.password.PasswordEncoder;

    @Controller
    @RequestMapping("/auth")
    public class UserController {

        @Autowired
        private UserService userService;
        @Autowired
        @Lazy
        private PasswordEncoder passwordEncoder;
        // Lấy thông tin username của user đang đăng nhập và thêm vào model
        @ModelAttribute
        public void addUsernameToModel(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
                String username = authentication.getName();
                model.addAttribute("username", username);// Lấy tên người dùng từ Authentication
                System.out.println("Tên người dùng trong @ModelAttribute: " + username);  // In ra console để kiểm tra
            } else {
                System.out.println("Chưa có người dùng đăng nhập hoặc là anonymousUser.");

            }
        }

        // Hiển thị trang đăng ký
        @GetMapping("/register")
        public String showRegisterForm() {
            return "register";
        }

        // Xử lý đăng ký người dùng mới
        @PostMapping("/register")
        public String registerUser(@RequestParam String username,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String phone,
                                   Model model) {
            // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
            if (userService.findByEmail(email) != null) {
                model.addAttribute("error", "Email already in use!");
                return "register";  // Nếu email đã tồn tại, quay lại trang đăng ký
            }
            // Tạo người dùng mới và mã hóa mật khẩu
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            user.setPassword(passwordEncoder.encode(password));  // Mã hóa mật khẩu

            user.setPhone(phone);

            // Lưu người dùng vào cơ sở dữ liệu
            userService.registerUser(user);

            return "redirect:/auth/login?success";
        }

        // Hiển thị trang đăng nhập
        @GetMapping("/login")
        public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                    @RequestParam(value = "logout", required = false) String logout,
                                    Model model) {
            if (error != null) {
                model.addAttribute("error", "Invalid username or password!");
            }
            if (logout != null) {
                model.addAttribute("message", "You have been logged out successfully.");
            }


            return "login";
        }
            @RequestMapping("/logout")
                    public String logout() {
                SecurityContextHolder.clearContext(); // Xóa thông tin xác thực
                return "redirect:/auth/login?logout=true"; // Quay về trang đăng nhập sau khi đăng xuất

            }
        }

