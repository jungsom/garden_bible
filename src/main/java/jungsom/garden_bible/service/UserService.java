package jungsom.garden_bible.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jungsom.garden_bible.config.CustomException;
import jungsom.garden_bible.config.EnvConfig;
import jungsom.garden_bible.dto.LoginDto;
import jungsom.garden_bible.dto.UserDto;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;

@Configuration
@PropertySource("classpath:env.properties")
@RequiredArgsConstructor
@Service
public class UserService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final EnvConfig envConfig;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .username(dto.getUsername())
                .inviteCode(generateCode())
                .socialType("local")
                .build();

        userRepository.save(user);
    }

    public void login(LoginDto dto) throws IOException {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
    }

    // 액세스 토큰 저장
    public void saveSocialAccessToken(String email, String type, String accessToken) {
        Optional<User> user = userRepository.findByEmailAndSocialType(email, type);
        user.get().setAccessToken(accessToken);
        user.get().setSocialType(type);
        userRepository.save(user.get());
    }

    // 액세스 토큰 조회
    public String getSocialAccessToken(String email, String type) {
        Optional<User> user = userRepository.findByEmailAndSocialType(email, type);
        return user.get().getAccessToken();
    }

    // 그룹 코드 생성
    public String generateCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Integer codeLength = 8;
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

}
