package org.example.admin;


import lombok.RequiredArgsConstructor;
import org.example.admin.Impl.*;
import org.example.core.entity.User;
import org.example.fm.*;
import org.example.fm.entity.*;
import org.example.fm.enums.FmMatchStatus;
import org.example.fm.enums.LeagueType;
import org.example.fm.enums.Position;
import org.example.model.admin.*;
import org.example.model.fm.TeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final TeamRepository teamRepository;
    private final TeamBaseRepository teamBaseRepository;
    private final CountryRepository countryRepository;
    private final PositionsBaseDtoRepository positionsBaseDtoRepository;
    private final PlayerAttributesBaseDtoRepository playerAttributesBaseDtoRepository;
    private final PlayerBaseDtoRepository playerBaseDtoRepository;
    private final LeagueBaseDtoRepository leagueBaseDtoRepository;
    private final GameRepository gameRepository;
    private final MatchSquadRepository matchSquadRepository;
    private final LeagueRepository leagueRepository;

    private static Map<Integer, List<Match>> generateSchedule(List<Team> allTeams, LocalDateTime startDateTime) {

        Collections.shuffle(allTeams);

        // Find out how many teamsSize we want fixtures for.
        int teamsSize = allTeams.size();

        // If odd number of teamsSize add a "ghost".
        AtomicBoolean ghost = new AtomicBoolean(false);
        if (teamsSize % 2 == 1) {
            allTeams.add(Team.builder().name("bye").build());
            teamsSize = allTeams.size();
            ghost.set(true);
        }

        int totalRounds = teamsSize - 1;
        int matchesPerRound = teamsSize / 2;

        Map<Integer, List<Match>> collect = IntStream.rangeClosed(0, totalRounds - 1)
                .boxed()
                .collect(Collectors.toMap(
                        number -> number,
                        number -> new ArrayList<Match>(Collections.nCopies(matchesPerRound, null))
                ));

        Map<Integer, List<Match>> rounds = new HashMap<>(collect);

        for (int round = 0; round < totalRounds; round++) {
            for (int match = 0; match < matchesPerRound; match++) {

                Team homeTeam = allTeams.get((round + match) % (teamsSize - 1));

                Team awayTeam = allTeams.get((teamsSize - 1 - match + round) % (teamsSize - 1));
                if (match == 0) {
                    awayTeam = allTeams.get(teamsSize - 1);
                }

                rounds.get(round).set(match, Match.builder().homeTeam(homeTeam).awayTeam(awayTeam).status(FmMatchStatus.SCHEDULED).build());

            }
        }

        Map<Integer, List<Match>> interleaved = new HashMap<>(collect);

        int evn = 0;
        int odd = (teamsSize / 2);
        for (int i = 0; i < rounds.size(); i++) {
            if (i % 2 == 0) {
                interleaved.put(i, rounds.get(evn++));
            } else {
                interleaved.put(i, rounds.get(odd++));
            }
        }

        rounds = interleaved;

        for (int round = 0; round < rounds.size(); round++) {
            if (round % 2 == 1) {
                rounds.get(round).set(0, flip(rounds.get(round).get(0)));
            }
        }

        AtomicReference<LocalDateTime> startDateAtomic = new AtomicReference<>(startDateTime);
        Map<Integer, List<Match>> rounds2 = rounds
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey() + 1,
                        entry -> {
                            List<Match> matches = entry.getValue().stream()
                                    .filter(match -> !match.getAwayTeam().getName().equals("bye") && !match.getHomeTeam().getName().equals("bye"))
                                    .peek(match -> match.setDateTime(startDateAtomic.get()))
                                    .peek(match -> match.setMatchDay(entry.getKey() + 1))
                                    .peek(match -> match.setStatus(FmMatchStatus.SCHEDULED))
                                    .collect(Collectors.toList());

                            startDateAtomic.set(startDateAtomic.get().plusDays(7));

                            return matches;
                        }
                ));

        int initialSize = rounds2.size();

        int daysOffBetween = 14;
        startDateAtomic.set(startDateAtomic.get().plusDays(daysOffBetween - 7));

        AtomicInteger roundCounterForRevengePart = new AtomicInteger(totalRounds + 1);
        for (int i = 1; i <= initialSize; i++) {

            rounds2.put(
                    i + (initialSize),
                    rounds2.get(i).stream()
                            .map(match -> Match.builder().homeTeam(match.getAwayTeam()).awayTeam(match.getHomeTeam()).build())
                            .peek(match -> match.setDateTime(startDateAtomic.get()))
                            .peek(match -> match.setMatchDay(roundCounterForRevengePart.get()))
                            .peek(match -> match.setStatus(FmMatchStatus.SCHEDULED))
                            .collect(Collectors.toList()));

            startDateAtomic.getAndSet(startDateAtomic.get().plusDays(7));
            roundCounterForRevengePart.incrementAndGet();
        }

        return rounds2;
    }

    private static Match flip(Match match) {
        return Match.builder()
                .awayTeam(match.getHomeTeam())
                .homeTeam(match.getAwayTeam())
                .build();
    }

    public List<Integer> generateLeague(LeagueType leagueType, String leagueName, LocalDateTime startDateTime, TeamDto... teams) {

        Optional<LeagueBaseDto> leagueBase = leagueBaseDtoRepository.findByType(leagueType.name());

        LeagueBaseDto leagueBaseDto = leagueBase.orElseThrow();

        Optional<Country> optionalCountry = countryRepository.findByName(leagueBaseDto.getCountryName());

        Country country = optionalCountry.orElse(countryRepository.save(Country.builder()
                .name(leagueBaseDto.getCountryName())
                .leagues(new ArrayList<>())
                .build()));

        League leagueToSave = League.builder()
                .leagueType(LeagueType.fromString(leagueBaseDto.getLeagueType()))
                .name(leagueName)
                .startDate(startDateTime.toLocalDate())
                .teams(new ArrayList<>())
                .matches(new ArrayList<>())
                .country(country)
                .build();

        Game game = Game.builder()
                .startDate(startDateTime.toLocalDate())
                .leagues(Set.of(leagueToSave))
                .users(new HashSet<>())
                .active(true)
                .build();


        leagueToSave.setGame(game);
        gameRepository.save(game);
        List<TeamBaseDto> allBaseTeams = teamBaseRepository.findAll();
        List<PositionBaseDto> allPositionsBase = positionsBaseDtoRepository.findAll();

        List<Team> teamsToAdd = Arrays.stream(teams)
                .map(team -> allBaseTeams.stream().filter(baseTeam -> team.getName().equals(baseTeam.getName())).findFirst().orElse(null))
                .filter(Objects::nonNull)
                .map(teamBaseDto ->
                        Team.builder()
                                .name(teamBaseDto.getName())
                                .country(countryRepository
                                        .findByName(teamBaseDto.getCountryName())
                                        .orElse(countryRepository.save(Country.builder()
                                                .name(teamBaseDto.getCountryName())
                                                .teams(new HashSet<>())
                                                .players(new HashSet<>())
                                                .leagues(new ArrayList<>())
                                                .build())))
                                .players(new HashSet<>())
                                .league(leagueToSave)
                                .awayMatches(new ArrayList<>())
                                .homeMatches(new ArrayList<>())
                                .squads(new ArrayList<>())
                                .backgroundUrl(teamBaseDto.getBackgroundUrl())
                                .shirtColors(teamBaseDto.getShirtColors())
                                .logoUrl(teamBaseDto.getLogoUrl())
                                .build()
                )
                .peek(team -> {
                    List<PlayerBaseDto> playersByTeam = playerBaseDtoRepository.findAllByTeamName(team.getName());

                    List<PlayerAttributesBaseDto> allPlayerAttributesBaseDto = playerAttributesBaseDtoRepository.findAll();

                    playersByTeam.forEach(playerBaseDto ->
                    {
                        @SuppressWarnings("OptionalGetWithoutIsPresent") Player player = Player.builder()
                                .firstName(playerBaseDto.getFirstName())
                                .lastName(playerBaseDto.getLastName())
                                .country(countryRepository.findByName(playerBaseDto
                                        .getCountryName())
                                        .orElse(countryRepository.save(Country
                                                .builder()
                                                .name(playerBaseDto.getCountryName())
                                                .leagues(new ArrayList<>())
                                                .players(new HashSet<>())
                                                .build())))
                                .team(team)
                                .imageUrl(playerBaseDto.getImageUrl())
                                .build();


                        Set<Position> positionsToAdd = allPositionsBase
                                .stream()
                                .filter(positionBaseDto -> positionBaseDto.getPlayerId().equals(playerBaseDto.getId()))
                                .map(positionBaseDto -> Position.fromString(positionBaseDto.getPosition()))
                                .collect(Collectors.toSet());

                        player.setPositions(positionsToAdd);


                        team.getPlayers().add(player);
                        player.getCountry().getPlayers().add(player);

                        @SuppressWarnings("OptionalGetWithoutIsPresent")
                        PlayerAttributesBaseDto playerAttributesBaseDto1 = allPlayerAttributesBaseDto.stream()
                                .filter(playerAttributesBaseDto -> playerAttributesBaseDto.getPlayerId().equals(playerBaseDto.getId()))
                                .findFirst().orElse(null);

                        PlayerAttributes playerAttr = PlayerAttributes.builder()
                                .player(player)
                                .attacking(playerAttributesBaseDto1.getAttacking())
                                .defending(playerAttributesBaseDto1.getDefending())
                                .heading(playerAttributesBaseDto1.getHeading())
                                .dribbling(playerAttributesBaseDto1.getDribbling())
                                .passing(playerAttributesBaseDto1.getPassing())
                                .technique(playerAttributesBaseDto1.getTechnique())
                                .teamWork(playerAttributesBaseDto1.getTeamWork())
                                .speed(playerAttributesBaseDto1.getSpeed())
                                .aggression(playerAttributesBaseDto1.getAggression())
                                .oneOnOnes(playerAttributesBaseDto1.getOneOnOnes())
                                .penaltySaving(playerAttributesBaseDto1.getPenaltySaving())
                                .penaltyScoring(playerAttributesBaseDto1.getPenaltyScoring())
                                .reflexes(playerAttributesBaseDto1.getReflexes())
                                .build();

                        player.setPlayerAttributes(playerAttr);


                    });

                })
                .collect(Collectors.toList());

        Map<Integer, List<Match>> matchesPerRound = generateSchedule(new ArrayList<>(teamsToAdd), startDateTime);

        List<Match> allMatchesForLeague = matchesPerRound.values().stream().flatMap(Collection::stream)
                .peek(match -> match.setLeague(leagueToSave))
                .collect(Collectors.toList());

        leagueToSave.setMatches(allMatchesForLeague);

        teamsToAdd = teamsToAdd.stream()
                .peek(team -> {
                    team.setAwayMatches(getAwayMatchesForTeam(team, allMatchesForLeague));
                    team.setHomeMatches(getHomeMatchesForTeam(team, allMatchesForLeague));
                })
                .collect(Collectors.toList());

        leagueToSave.setTeams(teamsToAdd);
        teamRepository.saveAll(teamsToAdd);

        return allMatchesForLeague
                .stream()
                .map(Match::getId)
                .collect(Collectors.toList());
    }

    private List<Match> getHomeMatchesForTeam(Team homeTeam, List<Match> allMatchesForLeague) {
        return allMatchesForLeague
                .stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam))
                .collect(Collectors.toList());

    }

    private List<Match> getAwayMatchesForTeam(Team awayTeam, List<Match> allMatchesForLeague) {

        return allMatchesForLeague
                .stream()
                .filter(match -> match.getAwayTeam().equals(awayTeam))
                .collect(Collectors.toList());
    }

    public Page<League> getPaginatedGames(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<League> all = leagueRepository.findAll();

        List<League> games = all.size() < startItem ? Collections.emptyList() : all.subList(startItem, Math.min(startItem + pageSize, all.size()));

        return new PageImpl(games, PageRequest.of(currentPage, pageSize), all.size());
    }

    public Set<User> getUsersForGameById(Integer id) {
        return gameRepository.getUsersForGame(id);
    }


    public void deleteGameById(Integer id) {

        gameRepository.findById(id)
                .ifPresent(game -> {
                            List<Integer> matchIds = game.getLeagues()
                                    .stream()
                                    .flatMap(league -> league.getMatches()
                                            .stream()
                                            .map(Match::getId)
                                    )
                                    .collect(Collectors.toList());

                            matchSquadRepository.deleteAllByMatchIdIn(matchIds);
                            gameRepository.delete(game);
                        }
                );

    }
}
