package jungsom.garden_bible.repository;

import jungsom.garden_bible.entity.Bookmark;
import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    boolean existsByUserAndBookAndChapterAndVerse(User user, int book, int chapter, int verse);
    void deleteByUserAndBookAndChapterAndVerse(User user, int book, int chapter, int verse);
    List<Bookmark> findAllByUser(User user);
}
