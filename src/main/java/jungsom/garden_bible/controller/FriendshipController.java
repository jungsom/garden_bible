package jungsom.garden_bible.controller;

import jungsom.garden_bible.dto.FriendRequestDto;
import jungsom.garden_bible.dto.UserDto;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.repository.FriendshipRepository;
import jungsom.garden_bible.repository.UserRepository;
import jungsom.garden_bible.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    /** 초대 코드로 친구 유저 조회 */
    @GetMapping("/invite")
    public ResponseEntity<User> invite(@RequestParam String code) {
        User toUser = userDetailService.getAuthenticatedUserId();
        User fromUser = userRepository.findByInviteCode(code);
        return ResponseEntity.ok(fromUser);
    }

    /** 친구 요청 보내기 */
    @PostMapping("/request")
    public ResponseEntity<String> request(@RequestBody FriendRequestDto dto) {
        return ResponseEntity.ok("test");
    }

    /** 받은 친구 요청 목록 */
    @GetMapping("/respond")
    public ResponseEntity<String> respond(@RequestParam String code) {
        return ResponseEntity.ok("test");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> accept(@RequestBody UserDto dto) {
        return ResponseEntity.ok("test");
    }

    /** 친구 목록 조회 */
    @GetMapping()
    public ResponseEntity<String> getFriends(@RequestParam String code) {
        return ResponseEntity.ok("test");
    }
}
