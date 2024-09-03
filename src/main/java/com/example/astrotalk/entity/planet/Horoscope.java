package com.example.astrotalk.entity.planet;

import com.example.astrotalk.dto.HoroscopeDetailsDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Horoscope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String horoscopeName;
    private String image;

    @OneToMany(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }, fetch = FetchType.LAZY,
            mappedBy = "horoscope"
    )
    private List<HoroscopeDetails> horoscopeDetails;


    public void addDetails(HoroscopeDetails details) {
        if(horoscopeDetails == null) {
            horoscopeDetails = new ArrayList<>();
        }

        horoscopeDetails.add(details);
    }
}
