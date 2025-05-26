package jungsom.garden_bible.config;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import lombok.*;
        import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Getter
@Setter
@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;
    private EnvConfig envConfig;

    // secret key 저장
    public JwtTokenProvider(EnvConfig envConfig) {
        this.envConfig = envConfig;
        byte[] keyBytes = java.util.Base64.getDecoder().decode(envConfig.getJwtSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = envConfig.getAccessJwtExpirationTime();
        this.refreshTokenExpTime = envConfig.getRefreshJwtExpirationTime();
    }

    // access token 생성
    public String createAccessToken(String email, String socialType) {
        System.out.println("access 토큰 생성시 소셜 타입: " + socialType);
        return createToken(email, socialType, accessTokenExpTime);
    }

    // refresh token 생성
    public String createRefreshToken(String email, String socialType) {
        System.out.println("refresh 토큰 생성시 소셜 타입: " + socialType);
        return createToken(email, socialType, refreshTokenExpTime);
    }

    // jwt 생성
    private String createToken(String email, String socialType, long expireTime) {
        System.out.println("jwt 생성시 소셜 타입: " +socialType);
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("social_type", socialType);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // token에서 user email 추출
    public String getUserEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    // token에서 user socialtype 추출
    public String getSocialType(String token) {
        return parseClaims(token).get("social_type", String.class);
    }

    // jwt Claims 추출
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // jwt 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // cookie 설정
    public Cookie createCookie(String refreshToken) {
        String cookieName = "refreshtoken";
        String cookieValue = refreshToken;
        Cookie cookie = new Cookie(cookieName, cookieValue);

        cookie.setHttpOnly(true);
        cookie.setSecure(false); // 추후 true로 변경
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        return cookie;
    }

}
