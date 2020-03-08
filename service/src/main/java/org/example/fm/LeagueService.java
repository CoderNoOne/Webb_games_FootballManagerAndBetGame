package org.example.fm;

import lombok.RequiredArgsConstructor;
import org.example.model.fm.LeagueDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public Set<LeagueDto> getAllLeaguesByActiveGameWithFetchedTeams() {
        return leagueRepository.findAllFetchTeams(true)
                .stream()
                .map(ManagerMapper::mapLeagueToDto)
                .collect(Collectors.toSet());
    }

    public Optional<LeagueDto> getActiveLeagueByUsername(String username) {
        return leagueRepository
                .findByGameActiveAndGameUsersUsername(true, username)
                .map(ManagerMapper::mapLeagueToDto);
    }
}
