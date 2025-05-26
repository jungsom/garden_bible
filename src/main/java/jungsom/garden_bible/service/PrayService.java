package jungsom.garden_bible.service;

import jungsom.garden_bible.config.CustomException;
import jungsom.garden_bible.dto.PrayDto;
import jungsom.garden_bible.entity.Pray;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.repository.PrayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrayService {
    private final PrayRepository prayRepository;
    private final UserDetailService userDetailService;

    public List<Pray> getMyPrays() {
        User user = userDetailService.getAuthenticatedUserId();

        List<Pray> pray = prayRepository.findAllByAuthorId(user.getId());

        return pray;
    }

    public Pray getPrayById(Integer id) {
        User user = userDetailService.getAuthenticatedUserId();

        Pray pray = prayRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Pray not found"));

        return pray;
    }

    public Pray createPray(PrayDto prayDto) {
        User user = userDetailService.getAuthenticatedUserId();

        Pray pray = Pray.builder()
                .title(prayDto.getTitle())
                .author(user)
                .content(prayDto.getContent())
                .build();

        return prayRepository.save(pray);
    }

    public String deletePray(Integer prayId) {
        User user = userDetailService.getAuthenticatedUserId();

        Pray pray = prayRepository.findById(prayId)
                        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Pray not found"));

        // 권한 확인
        if (!pray.getAuthor().getId().equals(user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "본인이 작성한 기도만 삭제할 수 있습니다.");
        }

        prayRepository.delete(pray);
        return "기도가 삭제되었습니다.";
    }
}
