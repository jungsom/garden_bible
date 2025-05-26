package jungsom.garden_bible.config;

import java.io.IOException;
import jungsom.garden_bible.dto.UserDto;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.service.UserDetailService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessTokenFromHeader(request);

        if (isValidToken(accessToken)) {
            authenticateUser(accessToken);
        } else {
            String refreshToken = getRefreshTokenFromCookies(request.getCookies());

            if (isValidToken(refreshToken)) {
                // Access Token 재발급 및 인증
                issueNewAccessToken(refreshToken, response);
            } else if (refreshToken != null) {
                // Refresh Token 만료 시 새로 발급
                issueNewTokens(refreshToken, response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void issueNewAccessToken(String refreshToken, HttpServletResponse response) {
        String userEmail = jwtTokenProvider.getUserEmail(refreshToken);
        String socialType = jwtTokenProvider.getSocialType(refreshToken);

        if (userEmail != null && socialType != null) {
            String newAccessToken = jwtTokenProvider.createAccessToken(userEmail, socialType);
            response.setHeader("Authorization", "Bearer " + newAccessToken);
            authenticateUser(newAccessToken);
        } else {
            System.out.println("Failed to issue new access token from refresh token.");
        }
    }

    private void issueNewTokens(String refreshToken, HttpServletResponse response) {
        String userEmail = jwtTokenProvider.getUserEmail(refreshToken);
        String socialType = jwtTokenProvider.getSocialType(refreshToken);

        if (userEmail != null && socialType != null) {
            String newAccessToken = jwtTokenProvider.createAccessToken(userEmail, socialType);
            String newRefreshToken = jwtTokenProvider.createRefreshToken(userEmail, socialType);

            response.setHeader("Authorization", "Bearer " + newAccessToken);

            // 리프레시 토큰을 쿠키에 설정
            Cookie refreshCookie = jwtTokenProvider.createCookie(newRefreshToken);
            response.addCookie(refreshCookie);

            authenticateUser(newAccessToken);
        } else {
            System.out.println("Failed to issue new tokens from expired refresh token.");
        }
    }

    private boolean isValidToken(String token) {
        return token != null && jwtTokenProvider.validateToken(token);
    }

    // 쿠키에서 리프레시 토큰 추출
    private String getRefreshTokenFromCookies(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if ("refreshtoken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // Authorization 헤더에서 토큰 추출
    private String getAccessTokenFromHeader(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            return headerValue.substring("Bearer ".length());
        }
        return null;
    }

    // 접근 권한 설정
    private void authenticateUser(String token) {
        String userEmail = jwtTokenProvider.getUserEmail(token);
        String socialType = jwtTokenProvider.getSocialType(token);
        User user = ((UserDetailService) userDetailsService)
                .loadUserByEmailAndSocialType(userEmail, socialType);

        System.out.println("useremail: " + userEmail);
        System.out.println("socialtype: " + socialType);
        System.out.println("user: " + user);

        if (user != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}


