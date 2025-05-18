package jungsom.garden_bible.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BibleKorhrv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String book;

    private String chapter;
    private String verse;
    private Text content;
}