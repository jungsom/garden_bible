package jungsom.garden_bible.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pray")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pray {
    @Id @GeneratedValue
    private Integer id;

    public String title;

    @ManyToOne
    public User author;

    public String content;
}
