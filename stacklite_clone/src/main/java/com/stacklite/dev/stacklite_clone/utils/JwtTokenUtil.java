package com.stacklite.dev.stacklite_clone.utils;

import java.io.Serializable;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.stacklite.dev.stacklite_clone.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.validity:300000}")
    private Integer tokenValidity;

    @Value("${jwt.signing.key:D8dbDxiMDfPQ0/kB5H7bM3ww4MQ84k8zljJzZ8ihrhw=}")
    private String signingKey;

    @Value("${jwt.authorities.key:roles}")
    private String authoritiesKey;

    private Key key;

    @PostConstruct
    public void init() {
        final byte[] signingKeyBytes = Base64.getDecoder().decode(signingKey);
        key = new SecretKeySpec(signingKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String getUserId(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication getAuthenticationToken(final String token, final Authentication authentication,
            final Optional<User> userDetails) {
        final JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(authoritiesKey).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // private Boolean isTokenExpired(String token) {
    // final Date expiration = getExpirationDateFromToken(token);
    // return expiration.before(new Date());
    // }

    // generate token for user
    public String generateToken(Optional<User> userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.get().getId().toString());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        System.out.println("--" + tokenValidity);
        return Jwts.builder().setSubject(subject).claim(authoritiesKey, claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(key).compact();
    }

    // validate token
    public boolean validateJwtToken(String authToken) throws Exception {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (Exception e) {

            throw e;
        }
    }

}
