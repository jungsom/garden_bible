package jungsom.garden_bible.dto;


import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.enums.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {
    private Integer id;
    private String username;
}
