package jungsom.garden_bible.controller;

import jungsom.garden_bible.dto.BookmarkDto;
import jungsom.garden_bible.entity.Bookmark;
import jungsom.garden_bible.repository.UserRepository;
import jungsom.garden_bible.service.BookmarkService;
import jungsom.garden_bible.service.FriendshipService;
import jungsom.garden_bible.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping()
    public ResponseEntity<?> toggleBookmark(@RequestBody BookmarkDto bookmarkDto) {
        String bookmark = bookmarkService.toggleBookmark(bookmarkDto);

        return ResponseEntity.ok(bookmark);
    }

    @GetMapping("/list")
    public ResponseEntity<?> selectBookmarks() {
        List<Bookmark> bookmarks = bookmarkService.selectBookmarks();

        return ResponseEntity.ok(bookmarks);
    }
}
