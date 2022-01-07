package com.anhvu.it.chatapp.filter;

import com.anhvu.it.chatapp.utility.jwt.JWTProvider;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().contains("/auth/")) {
            try {
                String authorization = request.getHeader("Authorization");
                if (authorization != null && authorization.startsWith("Bearer ")) {
                    String token = authorization.substring("Bearer ".length());
                    JWTProvider jwtProvider = new JWTProvider();
                    jwtProvider.setToken(token);
                    jwtProvider.verify();
                    String username = jwtProvider.getData();

                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("user"));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else throw new RuntimeException("Access token required");
            } catch (Exception e) {
                response.setContentType("APPLICATION_JSON_VALUE");
                MainResponse responsePayload = new MainResponse("Error: " + e.getMessage(), "FAILED", true);
                new ObjectMapper().writeValue(response.getOutputStream(), responsePayload);
            }
        }
        filterChain.doFilter(request, response);
    }

}
