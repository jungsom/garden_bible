package jungsom.garden_bible.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jungsom.garden_bible.config.EnvConfig;
import jungsom.garden_bible.config.JwtTokenProvider;
import jungsom.garden_bible.dto.LoginDto;
import jungsom.garden_bible.dto.UserDto;
import jungsom.garden_bible.repository.UserRepository;
import jungsom.garden_bible.service.UserDetailService;
import jungsom.garden_bible.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;
    private final EnvConfig envConfig;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) throws IOException {
        userService.login(loginDto);

        String accessToken = jwtTokenProvider.createAccessToken(loginDto.getEmail(), "local");
        String refreshToken = jwtTokenProvider.createRefreshToken(loginDto.getEmail(), "local");

        response.setHeader("Authorization", "Bearer " + accessToken);
        Cookie cookie = jwtTokenProvider.createCookie(refreshToken);
        response.addCookie(cookie);

        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto, HttpServletResponse response) {
        userService.signup(userDto);

        // 회원가입 후 자동 로그인 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(userDto.getEmail(), "local");
        String refreshToken = jwtTokenProvider.createRefreshToken(userDto.getEmail(), "local");

        response.setHeader("Authorization", "Bearer " + accessToken);

        Cookie cookie = jwtTokenProvider.createCookie(refreshToken);
        response.addCookie(cookie);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}