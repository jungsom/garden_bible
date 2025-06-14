package jungsom.garden_bible.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "pray")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pray {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public String title;

    @ManyToOne
    public User author;

    public String content;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDate createdAt;

    @CreationTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "deletedAt")
    private LocalDate deletedAt = null;
}
