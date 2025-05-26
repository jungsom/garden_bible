package jungsom.garden_bible.repository;

import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByInviteCode(String inviteCode);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndSocialType(@Param("email") String email, @Param("socialType") String socialType);
}
