package org.example.bet;

import org.example.bet.entity.BetPoints;
import org.example.bet.entity.BetPointsIdentityKey;
import org.example.model.bet.enums.BetLeague;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetPointsRepository extends JpaRepository <BetPoints, BetPointsIdentityKey> {

    List<BetPoints> findAllByLeague(BetLeague betLeague);
}
