package jungsom.garden_bible.service;

import jakarta.transaction.Transactional;
import jungsom.garden_bible.dto.BookmarkDto;
import jungsom.garden_bible.entity.Bookmark;
import jungsom.garden_bible.entity.Friendship;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.enums.FriendshipStatus;
import jungsom.garden_bible.repository.BookmarkRepository;
import jungsom.garden_bible.repository.FriendshipRepository;
import jungsom.garden_bible.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;

    @Transactional
    public String toggleBookmark(BookmarkDto dto) {
        User user = userDetailService.getAuthenticatedUserId();

        boolean exists = bookmarkRepository.existsByUserAndBookAndChapterAndVerse(
                user, dto.getBook(), dto.getChapter(), dto.getVerse());

        if (exists) {
            bookmarkRepository.deleteByUserAndBookAndChapterAndVerse(
                    user, dto.getBook(), dto.getChapter(), dto.getVerse());

            return "북마크가 삭제되었습니다.";
        } else {
            Bookmark bookmark = Bookmark.builder()
                    .user(user)
                    .book(dto.getBook())
                    .chapter(dto.getChapter())
                    .verse(dto.getVerse())
                    .build();

            bookmarkRepository.save(bookmark);

            return "북마크가 등록되었습니다.";
        }
    }

    public List<Bookmark> selectBookmarks() {
        User user = userDetailService.getAuthenticatedUserId();

        return bookmarkRepository.findAllByUser(user);
    }
}
