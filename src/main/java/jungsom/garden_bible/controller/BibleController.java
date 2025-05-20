package jungsom.garden_bible.controller;

import jungsom.garden_bible.entity.BibleId;
import jungsom.garden_bible.entity.BibleKorHRV;
import jungsom.garden_bible.repository.BibleKorHRVRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bible")
public class BibleController {
    private final BibleKorHRVRepository repository;

    public BibleController(BibleKorHRVRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public List<BibleKorHRV> getVersesByBookAndChapter(
            @RequestParam Long book,
            @RequestParam Long chapter
    ) {
        return repository.findByIdBookAndIdChapter(book, chapter);
    }
}
