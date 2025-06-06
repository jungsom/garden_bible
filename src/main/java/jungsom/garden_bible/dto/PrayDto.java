package jungsom.garden_bible.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrayDto {
    private String title;
    private String author;
    private String content;
}
