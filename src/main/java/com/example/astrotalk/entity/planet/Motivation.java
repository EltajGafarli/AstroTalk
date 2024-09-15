package com.example.astrotalk.entity.planet;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motivations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Motivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    private String motivationLetter;
    private String motivationImage;
}
