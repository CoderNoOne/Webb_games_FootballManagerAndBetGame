package com.app.web_app.model.bet_game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "points")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BetPoints {

    @EmbeddedId
    private BetPointsIdentityKey betPointsIdentityKey;

    private Integer pointsNumber;

    private LocalDateTime lastUpdateTime;
}
