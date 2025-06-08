package jungsom.garden_bible.repository;

import jungsom.garden_bible.entity.BibleId;
import jungsom.garden_bible.entity.BibleKorHRV;
import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.enums.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    List<Friendship> findByToUserAndStatus(User toUser, FriendshipStatus status);
    List<Friendship> findByStatusAndFromUserOrStatusAndToUser(FriendshipStatus status1, User fromUser, FriendshipStatus status2, User toUser);
    List<Friendship> findByStatus(FriendshipStatus status);
}