package SE2.admin.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
@Component
public class CustomerSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomerUserDetail customerUserDetail = (CustomerUserDetail) authentication.getPrincipal();
        String redirectUrl = request.getContextPath();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase("user")) {
                redirectUrl = "/";
                break;
            } else if (grantedAuthority.getAuthority().equalsIgnoreCase("admin")) {
                redirectUrl = "/adminHomepage";
                break;
            }
        }
        response.sendRedirect(redirectUrl);
    }
}
