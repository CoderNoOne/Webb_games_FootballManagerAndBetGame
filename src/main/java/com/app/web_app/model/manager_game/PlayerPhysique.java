package com.app.web_app.model.manager_game;

import com.app.web_app.model.manager_game.enums.StrongFoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerPhysique {

    private String height;
    private String weight;

    @Enumerated(EnumType.STRING)
    private StrongFoot strongFoot;
}
