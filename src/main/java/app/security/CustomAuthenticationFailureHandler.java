package app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
                                        javax.servlet.http.HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // In chi tiết lỗi để kiểm tra
        logger.error("Authentication failed: {}", exception.getMessage());
        // Chuyển hướng tới trang login và thêm thông báo lỗi vào model
        response.sendRedirect("/auth/login?error=true&message=" + exception.getMessage());
    }
}
