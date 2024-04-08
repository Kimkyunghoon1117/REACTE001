package com.edu.boardback.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.edu.boardback.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    // 사용자의 요청에 대해서 한번만 실행하는 Filter
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String token = parseBearerToken(request);

            if(token == null){
                filterChain.doFilter(request, response);
                return;
            }

            String email = jwtProvider.validate(token);

            if(email == null){
                filterChain.doFilter(request, response);
                return;
            }

            AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);
                // id , pwd, 권한(NO_AUTHORITIES=권한없음)
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // WebAuthenticationDetailsSource 인증 요청에 대한 세부정보 설정
            SecurityContext securitycontext = SecurityContextHolder.createEmptyContext();
            securitycontext.setAuthentication(authenticationToken);//SecurityContext:인증객체를저장
            
            SecurityContextHolder.setContext(securitycontext);//SecurityContextHolder:SecurityContext를저장
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest req){
        String authorization = req.getHeader("Authorization");
        boolean hasAuthorization = StringUtils.hasText(authorization);//sp
        if(!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer) return null;

        String token = authorization.substring(7);

        return token;
    }
}
