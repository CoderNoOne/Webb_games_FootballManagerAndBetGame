package com.app.web_app.model.admin;

import com.app.web_app.model.manager_game.LeagueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaguesWrapper {

    private List<LeagueType> leagues;
}
