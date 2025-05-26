package jungsom.garden_bible.service;

import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.Pray;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.enums.FriendshipStatus;
import jungsom.garden_bible.repository.FriendshipRepository;
import jungsom.garden_bible.repository.PrayRepository;
import jungsom.garden_bible.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
