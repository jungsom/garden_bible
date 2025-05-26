package jungsom.garden_bible.controller;

import jungsom.garden_bible.dto.FriendRequestDto;
import jungsom.garden_bible.dto.UserDto;
import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.repository.FriendshipRepository;
import jungsom.garden_bible.repository.UserRepository;
import jungsom.garden_bible.service.FriendshipService;
import jungsom.garden_bible.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    /** 초대 코드로 친구 유저 조회 */
    @GetMapping("/invite/{code}")
    public ResponseEntity<User> selectFriendByCode(
            @PathVariable String code
    ) {
        User user = friendshipService.selectFriendByCode(code);
        return ResponseEntity.ok(user);
    }

    /** 친구에게 요청 보내기 */
    @PostMapping("/request/{id}")
    public ResponseEntity<String> requestToFriend(
            @PathVariable Integer id
    ) {
        Friendship friendship = friendshipService.createFriendship(id);
        return ResponseEntity.ok("친구 신청을 완료했습니다.");
    }

    /** 받은 친구 요청 목록 */
    @GetMapping("/respond")
    public ResponseEntity<String> respond(@RequestParam String code) {
        User toUser = userDetailService.getAuthenticatedUserId();
        return ResponseEntity.ok("test");
    }

    /** 신청 수락 or 거절 */
    @PostMapping("/accept")
    public ResponseEntity<String> accept(@RequestBody UserDto dto) {
        User toUser = userDetailService.getAuthenticatedUserId();
        return ResponseEntity.ok("test");
    }
}
