package com.endiluamba.creditmanager.config;

import com.endiluamba.creditmanager.customers.service.AuthenticationService;
import com.endiluamba.creditmanager.customers.service.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private JwtTokenManager jwtTokenManager;
        
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        var email = "";
        var jwtToken = "";
        
        var requestTokenHeader = request.getHeader("Authorization");
        if(isTokenPresent(requestTokenHeader)) {
            jwtToken = requestTokenHeader.substring(7);
            email = jwtTokenManager.getUsernameFromToken(jwtToken);
        } else {
            logger.warn("JWT token does not start with 'Bearer' String");
        }
        
        if(isUsernameValidButNotInContext(email)) {
            addUsernameInContext(request, email, jwtToken);
        }
        chain.doFilter(request, response);
    }

    private boolean isTokenPresent(String requestTokenHeader) {
        return requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ");
    }

    private boolean isUsernameValidButNotInContext(String email) {
        return email != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void addUsernameInContext(HttpServletRequest request, String email, String jwtToken) {
        UserDetails userDetails = authenticationService.loadUserByUsername(email);
        if(jwtTokenManager.validateToken(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
