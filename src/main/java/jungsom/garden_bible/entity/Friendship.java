package jungsom.garden_bible.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

enum FriendshipStatus {
    REQUESTED,
    ACCEPTED,
    REJECTED
}

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
