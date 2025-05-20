package jungsom.garden_bible.service;

import jungsom.garden_bible.dto.PrayDto;
import jungsom.garden_bible.entity.Pray;
import jungsom.garden_bible.repository.PrayRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrayService {
    private final PrayRepository prayRepository;

    public PrayService(PrayRepository prayRepository) {
        this.prayRepository = prayRepository;
    }

    public Pray createPray(PrayDto prayDto) {
        Pray pray = Pray.builder()
                .title(prayDto.getTitle())
                .content(prayDto.getContent())
                .build();

        return prayRepository.save(pray);
    }

    public String deletePray(Integer prayId) {
        Pray pray = prayRepository.findById(prayId).get();
        prayRepository.delete(pray);
        return "기도가 삭제되었습니다.";
    }
}
