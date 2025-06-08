package jungsom.garden_bible.controller;

import jungsom.garden_bible.dto.FriendDto;
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

import java.util.List;

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
    public ResponseEntity<?> respond() {
        List<Friendship> requestedFriends = friendshipService.selectRequestedFriends();

        if (requestedFriends.isEmpty()) {
            ResponseEntity.ok("요청 온 친구가 없습니다.");
        }
        return ResponseEntity.ok(requestedFriends);
    }

    /** 팔로우된 친구 목록 */
    @GetMapping("/follow")
    public ResponseEntity<?> follow() {
        List<FriendDto> acceptedFriends = friendshipService.selectAcceptedFriends();

        if (acceptedFriends.isEmpty()) {
            ResponseEntity.ok("팔로우된 친구가 없습니다.");
        }
        return ResponseEntity.ok(acceptedFriends);
    }

    /** 신청 수락 */
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> accept(@PathVariable Integer id) {
        Friendship acceptedFriend = friendshipService.acceptFriend(id);

        return ResponseEntity.ok(acceptedFriend);

    }

    /* 신청 거절 */
    @PostMapping("/refuse/{id}")
    public ResponseEntity<?> refuse(@PathVariable Integer id) {
        friendshipService.refuseFriend(id);

        return ResponseEntity.ok("친구 신청을 거절하였습니다.");

    }
}
