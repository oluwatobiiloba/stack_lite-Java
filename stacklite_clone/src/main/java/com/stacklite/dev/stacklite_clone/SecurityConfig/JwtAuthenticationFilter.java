package com.stacklite.dev.stacklite_clone.SecurityConfig;

import com.stacklite.dev.stacklite_clone.Services.UserDetailsServiceImpl;
import com.stacklite.dev.stacklite_clone.handlers.JwtAuthenticationEntryPoint;
import com.stacklite.dev.stacklite_clone.utils.JwtTokenUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        String[] whitelistRoutes = getEnvironment().getProperty("app.security.whitelist-routes", "").split(",");

        for (String whitelistedRoute : whitelistRoutes) {
            if (requestPath.startsWith(whitelistedRoute)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authHeader = request.getHeader("Authorization");
        String token;
        Integer userId;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                boolean isVerfied = jwtService.validateJwtToken(token);
                userId = Integer.parseInt(jwtService.getUserId(token));
                if (isVerfied) {
                    UserDetails userDetails = userDetailsService.loadUserById(userId);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (Exception e) {
                handleAuthenticationException(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private void handleAuthenticationException(HttpServletRequest request, HttpServletResponse response,
            Exception e) throws IOException, ServletException {

        String errorMessage = null;

        if (e instanceof ExpiredJwtException) {
            errorMessage = "Session Expired, Please Login again";
        } else if (e instanceof SignatureException) {
            errorMessage = "Invalid Token";
        } else if (e instanceof MalformedJwtException) {
            errorMessage = "Invalid Token";
        } else if (e instanceof UnsupportedJwtException) {
            errorMessage = "Invalid Token";
        } else if (e instanceof IllegalArgumentException) {
            errorMessage = "Invalid Token - IAE";
        } else if (e instanceof MissingClaimException) {
            errorMessage = "Invalid Token - MCE";
        } else if (e instanceof PrematureJwtException) {
            errorMessage = "Invalid Token - PJE";
        } else if (e instanceof InvalidClaimException) {
            errorMessage = "Invalid Token - ICE";
        }

        AuthenticationException authenticationException = new AuthenticationServiceException(errorMessage, e);

        //noinspection ThrowablePrintedToSystemOut
        jwtAuthenticationEntryPoint.commence(request, response, authenticationException);
    }
}
