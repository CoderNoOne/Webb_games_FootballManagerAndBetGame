package org.example;

import org.example.bet.BetService;
import org.example.fm.SquadStrengthCalculatorService;
import org.example.repository.bet.BetPointsRepository;
import org.example.repository.bet.ScoreEntityRepository;
import org.example.repository.fm.GoalDetailRepository;
import org.example.repository.fm.MatchRepository;
import org.example.repository.fm.MatchSquadRepository;
import org.example.repository.fm.MatchStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("test")
public class ScheduleService extends org.example.core.ScheduleService {

    public ScheduleService(TaskScheduler executor, MatchRepository matchRepository, MatchStatisticRepository matchStatisticRepository, SquadStrengthCalculatorService squadStrengthCalculatorService, MatchSquadRepository matchSquadRepository, BetService betService, ScoreEntityRepository scoreEntityRepository, BetPointsRepository betPointsRepository, GoalDetailRepository goalDetailRepository) {
        super(executor, matchRepository, matchStatisticRepository, squadStrengthCalculatorService, matchSquadRepository, betService, scoreEntityRepository, betPointsRepository, goalDetailRepository);
    }

    @Override
    @PostConstruct
    public void scheduleTasks() {
        System.out.println("DO NOTHING");
    }
}
