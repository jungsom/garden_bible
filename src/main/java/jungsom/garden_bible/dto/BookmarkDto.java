package jungsom.garden_bible.dto;


import jungsom.garden_bible.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {
    private User user;
    private int book;
    private int chapter;
    private int verse;
}
