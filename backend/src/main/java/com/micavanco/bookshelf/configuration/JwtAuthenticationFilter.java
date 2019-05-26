package com.micavanco.bookshelf.configuration;

import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.service.implementations.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static com.micavanco.bookshelf.configuration.SecurityConstants.HEADER_STRING;
import static com.micavanco.bookshelf.configuration.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(httpServletRequest);
            String username = null;

            if(StringUtils.hasText(jwt))
            {
                try {
                    username = jwtTokenProvider.getUsernameFromToken(jwt);
                } catch (IllegalArgumentException e) {
                    logger.error("an error occured during getting username from token", e);
                } catch (ExpiredJwtException e) {
                    logger.warn("the token is expired and not valid anymore", e);
                } catch(SignatureException e) {
                    logger.error("Authentication Failed. Username or Password not valid.");
                }

            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (jwtTokenProvider.validateToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getUserAuthentication(jwt, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }

        }catch(Exception ex)
        {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJWTFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader(HEADER_STRING);

        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith(TOKEN_PREFIX))
            return bearerToken.substring(7);

        return null;
    }
}
