package com.anhvu.it.chatapp.filter;

import com.anhvu.it.chatapp.utility.jwt.JWTProvider;
import com.anhvu.it.chatapp.utility.payload.response.MainResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try {
            org.springframework.security.core.userdetails.User userContext = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

            JWTProvider jwtProvider= new JWTProvider();
            jwtProvider.setNextExpired(Long.valueOf(100000000));
            jwtProvider.setData(userContext.getUsername());
            jwtProvider.setIssuer(request.getRequestURL().toString());

            String access_token = jwtProvider.generate();
            response.setContentType("APPLICATION_JSON_VALUE");
            Map<String, String> data = new HashMap<>();
            data.put("access_token", access_token);
            MainResponse<Map<String, String>> responsePayload = new MainResponse(data, "SUCCESS");
            new ObjectMapper().writeValue(response.getOutputStream(), responsePayload);

        } catch (Exception ex) {
            response.setContentType("APPLICATION_JSON_VALUE");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            MainResponse responsePayload = new MainResponse("" + ex.getMessage(), "FAILED", true);
            new ObjectMapper().writeValue(response.getOutputStream(), responsePayload);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("APPLICATION_JSON_VALUE");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        failed.printStackTrace();
        MainResponse responsePayload = new MainResponse("Wrong password or username!", "FAILED", true);
        new ObjectMapper().writeValue(response.getOutputStream(), responsePayload);
    }
}
