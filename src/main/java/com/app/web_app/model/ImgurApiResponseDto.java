package com.app.web_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImgurApiResponseDto {

    private String link;

    @JsonProperty(value = "data")
    private void setLink(Map<String, Object> data){
        this.link = (String) data.get("link");
    }
}
