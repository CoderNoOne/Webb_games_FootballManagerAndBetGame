package com.app.web_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBet {


    private String name;

    @JsonProperty(value = "matches")
    private List<BetMatch> matches;

//    private Long min;

    @JsonSetter(value = "competition")
    public void setName(Map<String, Object> competitionProperties) {
        this.name = (String) competitionProperties.get("name");
    }

//    @JsonSetter()
//    public void setName(Map<String, Object> properties) {
//        this.min = ChronoUnit.MINUTES.between(utcDate, lastUpdated
//                /*(LocalDateTime)properties.get("utcDate"), (LocalDateTime)properties.get("lastUpdated")*/);
//    }


}
