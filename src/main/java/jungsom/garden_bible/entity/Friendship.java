package jungsom.garden_bible.entity;

import jakarta.persistence.*;
import jungsom.garden_bible.enums.FriendshipStatus;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friendship")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Enumerated()
    private FriendshipStatus status;

    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;
}
