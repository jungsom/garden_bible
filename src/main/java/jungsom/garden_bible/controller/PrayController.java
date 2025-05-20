package jungsom.garden_bible.controller;

import jungsom.garden_bible.dto.PrayDto;
import jungsom.garden_bible.entity.Pray;
import jungsom.garden_bible.repository.PrayRepository;
import jungsom.garden_bible.service.PrayService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pray")
public class PrayController {
    private final PrayRepository prayRepository;
    private final PrayService prayService;

    public PrayController(PrayRepository prayRepository, PrayService prayService) {
        this.prayRepository = prayRepository;
        this.prayService = prayService;
    }

    @PostMapping()
    public Pray pray(
            @RequestBody PrayDto pray
    ) {
        return prayService.createPray(pray);
    }

    @DeleteMapping()
    public String deletePray(
            @RequestParam Integer id
    ) {
        return prayService.deletePray(id);
    }
}
