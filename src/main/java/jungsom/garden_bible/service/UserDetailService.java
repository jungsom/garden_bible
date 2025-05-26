package jungsom.garden_bible.service;

import jungsom.garden_bible.config.CustomException;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User loadUserByEmailAndSocialType(String email, String socialType) {
        return userRepository.findByEmailAndSocialType(email, socialType).orElse(null);
    }

    public User getAuthenticatedUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.FORBIDDEN, "사용자 인증에 실패하였습니다.");
        }
    }
}

