package jungsom.garden_bible.service;

import jungsom.garden_bible.config.CustomException;
import jungsom.garden_bible.dto.FriendDto;
import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.Pray;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.enums.FriendshipStatus;
import jungsom.garden_bible.repository.FriendshipRepository;
import jungsom.garden_bible.repository.PrayRepository;
import jungsom.garden_bible.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    public User selectFriendByCode(String code) {
        User fromUser = userDetailService.getAuthenticatedUserId();
        User toUser = userRepository.findByInviteCode(code);

        return toUser;
    }

    public Friendship createFriendship(Integer id) {
        User fromUser = userDetailService.getAuthenticatedUserId();
        User toUser = userRepository.findById(id).orElseThrow();

        Friendship friendship = Friendship.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .status(FriendshipStatus.REQUESTED)
                .build();

        return friendshipRepository.save(friendship);
    }

    public List<Friendship> selectRequestedFriends() {
        User fromUser = userDetailService.getAuthenticatedUserId();
        List<Friendship> requestedFriends = friendshipRepository.findByToUserAndStatus(fromUser, FriendshipStatus.REQUESTED);

        return requestedFriends;
    }

    public List<FriendDto> selectAcceptedFriends() {
        User me = userDetailService.getAuthenticatedUserId();
        List<Friendship> friends = friendshipRepository.findByStatus(FriendshipStatus.ACCEPTED);

        return friends.stream()
                .filter(f -> f.getFromUser().getId().equals(me.getId()) || f.getToUser().getId().equals(me.getId()))
                .map(f -> {
                    User other = f.getFromUser().getId().equals(me.getId()) ? f.getToUser() : f.getFromUser();
                    return new FriendDto(other.getId(), other.getUsername());
                })
                .collect(Collectors.toList());
    }

    public Friendship acceptFriend(Integer id) {
        User fromUser = userDetailService.getAuthenticatedUserId();

        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 친구 요청을 찾을 수 없습니다."));

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipRepository.save(friendship);
    }

    public void refuseFriend(Integer id) {
        User fromUser = userDetailService.getAuthenticatedUserId();

        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 친구 요청을 찾을 수 없습니다."));

        friendshipRepository.delete(friendship);
    }


}
