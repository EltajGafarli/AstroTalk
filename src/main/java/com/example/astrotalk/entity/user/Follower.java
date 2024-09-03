package com.example.astrotalk.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "followers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private User follower;
}
