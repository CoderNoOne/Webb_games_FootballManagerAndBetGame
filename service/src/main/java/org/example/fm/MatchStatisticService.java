package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.model.fm.MatchStatisticDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchStatisticService {

    private final MatchStatisticRepository matchStatisticRepository;

    public Optional<MatchStatisticDto> getMatchStatisticByMatchId(Integer matchId) {

        return matchStatisticRepository.findByMatchId(matchId)
                .map(ManagerMapper::mapMatchStatisticToDto);
    }

}
