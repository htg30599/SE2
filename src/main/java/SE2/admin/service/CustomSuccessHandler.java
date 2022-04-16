package SE2.admin.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       CustomUserDetails customUserDetails= (CustomUserDetails) authentication.getPrincipal();
        String redirectUrl = request.getContextPath();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {

            if (grantedAuthority.getAuthority().equals("USER")) {
                redirectUrl = "/userHomepage";
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
                redirectUrl = "/adminHomepage";
                break;
            }
        }


      response.sendRedirect(redirectUrl);
    }
    }


