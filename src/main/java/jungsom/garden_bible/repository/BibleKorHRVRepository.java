package jungsom.garden_bible.repository;

import jungsom.garden_bible.entity.BibleKorHRV;
import jungsom.garden_bible.entity.BibleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibleKorHRVRepository extends JpaRepository<BibleKorHRV, BibleId> {
    List<BibleKorHRV> findByIdBookAndIdChapter(Long book, Long chapter);
}