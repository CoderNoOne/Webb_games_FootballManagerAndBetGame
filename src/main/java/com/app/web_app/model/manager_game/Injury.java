package com.app.web_app.model.manager_game;

import com.app.web_app.model.manager_game.enums.InjuryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "injuries")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class    Injury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @Enumerated(EnumType.STRING)
    private InjuryType type;

    @ManyToOne
    @JoinColumn(name = "healthCard_id")
    private HealthCard healthCard;
}
