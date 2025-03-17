package com.example.AddressBookManagement.security;

import com.example.AddressBookManagement.Utils.JwtUtil;
import com.example.AddressBookManagement.model.AuthUser;
import com.example.AddressBookManagement.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {  // ✅ Extending OncePerRequestFilter

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUserRepository authUserRepository; // ✅ Inject UserRepository

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // ✅ Extract JWT Token from Header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(token);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);

            if (userOptional.isPresent()) {
                AuthUser user = userOptional.get();

                // ✅ Validate Token & Check If It Matches Stored Token
                if (jwtUtil.validateToken(token) && token.equals(user.getResetToken())) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or expired token");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
