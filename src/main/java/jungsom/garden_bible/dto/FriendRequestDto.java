package jungsom.garden_bible.dto;

import jungsom.garden_bible.entity.User;
import jungsom.garden_bible.enums.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {
    private User fromUser;
    private User toUser;
    private FriendshipStatus status;
}
