package jungsom.garden_bible.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:env.properties")
public class EnvConfig {
    @Value("${NAVER_CLIENT_ID}")
    private String NaverClientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String NaverClientSecret;

    @Value("${NAVER_CALLBACK_URL}")
    private String NaverCallbackUrl;

    @Value("${ACCESS_JWT_EXPIRATION_TIME}")
    private Long AccessJwtExpirationTime;

    @Value("${REFRESH_JWT_EXPIRATION_TIME}")
    private Long RefreshJwtExpirationTime;

    @Value("${JWT_SECRET_KEY}")
    private String JwtSecretKey;

    @Value("${DATABASE_URL}")
    private String DatabaseUrl;

    @Value("${DATABASE_USERNAME}")
    private String DatabaseUsername;

    @Value("${DATABASE_PASSWORD}")
    private String DatabasePassword;

    @Value("${CLIENT_URL}")
    private String ClientUrl;

    @Value("${AI_URL}")
    private String AIUrl;

    @Value("${KAKAO_CLIENT_ID}")
    private String KakaoClientId;

    @Value("${KAKAO_CALLBACK_URL}")
    private String KakaoCallbackUrl;
}
