package jungsom.garden_bible.entity;

import jakarta.persistence.*;
import jungsom.garden_bible.enums.FriendshipStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Enumerated()
    private FriendshipStatus status;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDate createdAt;

    @CreationTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "deletedAt")
    private LocalDate deletedAt = null;

}
