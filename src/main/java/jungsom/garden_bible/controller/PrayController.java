package jungsom.garden_bible.controller;

import jungsom.garden_bible.config.CustomException;
import jungsom.garden_bible.dto.ErrorDto;
import jungsom.garden_bible.dto.PrayDto;
import jungsom.garden_bible.entity.Pray;
import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.repository.PrayRepository;
import jungsom.garden_bible.service.PrayService;
import jungsom.garden_bible.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pray")
@RequiredArgsConstructor
public class PrayController {
    private final PrayRepository prayRepository;
    private final PrayService prayService;
    private final UserDetailService userDetailService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyPrayers() {
        try {
            List<Pray> prays = prayService.getMyPrays();
            return ResponseEntity.ok(prays);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorDto(e.getStatus().value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrayer(@PathVariable Integer id) {
        try {
            Pray pray = prayService.getPrayById(id);
            return ResponseEntity.ok(pray);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(new ErrorDto(e.getStatus().value(), e.getMessage()));
        }
    }
    @PostMapping()
    public ResponseEntity<?> createPray(
            @RequestBody PrayDto pray
    ) {
        try {
            Pray result = prayService.createPray(pray);
            return ResponseEntity.ok(result);
        } catch (CustomException e) {
            ErrorDto errorResponse = new ErrorDto(e.getStatus().value(), e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDto errorResponse = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에 문제가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deletePray(
            @RequestParam Integer id
    ) {
        try {
            String result = prayService.deletePray(id);
            return ResponseEntity.ok(result);
        } catch (CustomException e) {
            ErrorDto errorResponse = new ErrorDto(e.getStatus().value(), e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDto errorResponse = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에 문제가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
