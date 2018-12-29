package filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@WebFilter(urlPatterns = "/user/wishlist")
public class UserFilter implements Filter {
    private final UserService userService;
    private final static String LOGIN_URL = "/user/login";

    @Autowired
    public UserFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {
            final String token = httpRequest.getHeader("token");
            if (token == null) {
                throw new ValidationException("Nu ești autentificat.");
            }

            final Integer realUserId = userService.validate(token);
            final Integer userId = httpRequest.getIntHeader("userId");
            if (!userId.equals(realUserId)) {
                throw new ValidationException("Nu poți accesa contul altui utilizator.");
            }

            // continue in controller
            chain.doFilter(request, response);
        }
        catch (Exception e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(UNAUTHORIZED.value());
        }
    }

}
