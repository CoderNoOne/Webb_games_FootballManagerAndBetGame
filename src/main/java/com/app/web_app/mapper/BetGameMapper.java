package com.app.web_app.mapper;

import com.app.web_app.model.bet_game.BetPoints;
import com.app.web_app.model.dto.BetPointsDto;
import org.springframework.stereotype.Component;

@Component
public class BetGameMapper {

    public BetPointsDto mapBetPointsToDto(BetPoints betPoints) {

        return betPoints != null ?

                BetPointsDto.builder()
                        .lastUpdateTime(betPoints.getLastUpdateTime())
                        .league(betPoints.getBetPointsIdentityKey().getLeague())
                        .username(betPoints.getBetPointsIdentityKey().getUsername())
                        .pointsNumber(betPoints.getPointsNumber())
                        .build()
                : null;

    }
}
