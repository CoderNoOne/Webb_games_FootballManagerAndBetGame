package com.app.web_app.model.manager_game;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "health_cards")
public class HealthCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @OneToOne(mappedBy = "healthCard")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Player player;

    @OneToMany(mappedBy = "healthCard", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Injury> injuries;

}
