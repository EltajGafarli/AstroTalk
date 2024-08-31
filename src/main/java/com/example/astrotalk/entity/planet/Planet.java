package com.example.astrotalk.entity.planet;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String image;
    private String gravitation;
    private String area;

    @Column(columnDefinition = "text")
    private String about;
}
