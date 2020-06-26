package org.example.entity.bet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.bet.enums.BetLeague;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BetPointsIdentityKey implements Serializable {

    private String username;

    @Enumerated(EnumType.STRING)
    private BetLeague league;
}
