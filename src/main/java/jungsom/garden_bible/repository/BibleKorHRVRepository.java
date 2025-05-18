package jungsom.garden_bible.repository;

import jungsom.garden_bible.entity.BibleKorHRV;
import jungsom.garden_bible.entity.BibleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibleKorHRVRepository extends JpaRepository<BibleKorHRV, BibleId> {
}