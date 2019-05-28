package com.micavanco.bookshelf.configuration;

import com.micavanco.bookshelf.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.micavanco.bookshelf.configuration.SecurityConstants.*;

@Component
public class JwtTokenProvider implements Serializable {

    public String getUsernameFromToken(String token) {
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
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Authentication authentication)
    {
        User user = (User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date expiredDate = new Date(now.getTime()+TOKEN_EXPIRATION_TIME);

        return  "Bearer "+Jwts.builder()
                .setSubject(authentication.getName())
                .claim("password", user.getPassword())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            final String username = getUsernameFromToken(token);
            return (
                    username.equals(userDetails.getUsername()));
        }catch(SignatureException ex)
        {
            System.out.println("Invalid JWT Signature");
        }catch(MalformedJwtException ex)
        {
            System.out.println("Invalid JWT Token");
        }catch(ExpiredJwtException ex)
        {
            System.out.println("Expired JWT Token");
        }catch(UnsupportedJwtException ex)
        {
            System.out.println("Unsupported JWT Token");
        }catch(IllegalArgumentException ex)
        {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public String getUsernameFromJWT(String token)
    {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return  (String)claims.get("username");
    }

    public UsernamePasswordAuthenticationToken getUserAuthentication(String token, Authentication existingAuth, UserDetails userDetails)
    {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
