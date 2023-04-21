package kz.atirau.spring.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import kz.atirau.spring.security.JWTutil;
import kz.atirau.spring.services.adminService.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTutil jwTutil;
    private AdminDetailsService adminDetailsService;

    @Autowired
    public JWTFilter(JWTutil jwTutil, AdminDetailsService adminDetailsService) {
        this.jwTutil = jwTutil;
        this.adminDetailsService = adminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);

            if(jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token in Bearer Header");
            }else{
                try {
                    String username = jwTutil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = adminDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword()
                            , userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch (JWTVerificationException exception){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
