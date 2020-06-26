package org.example.repository.bet;


import org.example.entity.bet.entity.BetPoints;
import org.example.entity.bet.entity.BetPointsIdentityKey;
import org.example.entity.bet.enums.BetLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetPointsRepository extends JpaRepository <BetPoints, BetPointsIdentityKey> {

    List<BetPoints> findAllByLeague(BetLeague betLeague);
}
