package com.app.web_app.service;

import com.app.web_app.exceptions.AppException;
import com.app.web_app.model.ImgurApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImgurService {

    private final RestTemplate restTemplate;

    public String upload(MultipartFile file) {
        String photoString;

        try {
            photoString = Base64.getEncoder().encodeToString(file.getBytes());
            if (photoString.equals("")) {
                return null;
            }
        } catch (IOException e) {

            throw new AppException("File conversion error");
        }

        RequestEntity<String> requestEntity = RequestEntity
                .post(URI.create("https://api.imgur.com/3/image"))
                .header("Authorization", "Client-ID 153f7b66666a79c")
                .body(photoString);

        ResponseEntity<ImgurApiResponseDto> response = restTemplate.exchange(requestEntity, ImgurApiResponseDto.class);

        return Objects.requireNonNull(response.getBody()).getLink();
    }

}

