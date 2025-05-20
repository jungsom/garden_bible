package jungsom.garden_bible.repository;

import jungsom.garden_bible.entity.BibleId;
import jungsom.garden_bible.entity.BibleKorHRV;
import jungsom.garden_bible.entity.Pray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrayRepository extends JpaRepository<Pray, Integer> {

}