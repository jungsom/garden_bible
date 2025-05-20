package jungsom.garden_bible.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bible_korhrv")
@Getter
@Setter
@NoArgsConstructor
public class BibleKorHRV {

    @EmbeddedId
    private BibleId id;

    @Column(columnDefinition = "text")
    private String content;


}

