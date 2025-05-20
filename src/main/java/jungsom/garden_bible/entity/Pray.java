package jungsom.garden_bible.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;

    public String title;

    public String author;

    public String content;
}
