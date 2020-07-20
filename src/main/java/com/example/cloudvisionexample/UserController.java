package com.example.cloudvisionexample;

import com.example.cloudvisionexample.request.Image;
import com.example.cloudvisionexample.request.ImageData;
import com.example.cloudvisionexample.request.ImageRequest;
import com.example.cloudvisionexample.request.Payload;
import com.example.cloudvisionexample.response.ErrorResponse;
import com.example.cloudvisionexample.response.ImageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class UserController {

    private static String UPLOADED_FOLDER = "src/main/resources";

    @PostMapping("/upload")
    public ResponseEntity getUploadResponse(@RequestBody ImageData imageData, @RequestHeader("Authorization") String auth) {

        try {
            String url = "https://automl.googleapis.com/v1beta1/projects/179572677998/locations/us-central1/models/IOD4582513775919235072:predict";

            String imageBytes = imageData.getImage().replace("data:image/jpeg;base64,", "");

            com.example.cloudvisionexample.request.Image image = Image.builder().imageBytes(imageBytes).build();
            Payload payload = Payload.builder().image(image).build();
            ImageRequest imageRequest = ImageRequest.builder().payload(payload).build();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", auth);

            HttpEntity<ImageRequest> request = new HttpEntity<>(imageRequest, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity responseEntity = restTemplate.postForEntity(url, request, ImageResponse.class);

            return ResponseEntity.ok(responseEntity.getBody());
        }
        catch (RestClientResponseException e) {
            HttpStatus httpStatus = HttpStatus.valueOf(e.getRawStatusCode());
            ErrorResponse errorResponse = null;
            log.error(e.getStatusText());
            log.error(e.getResponseBodyAsString());
            if(httpStatus.is4xxClientError()){
                errorResponse = ErrorResponse.builder().error(e.getResponseBodyAsString()).build();
            }
            if(httpStatus.is5xxServerError()){
                if(!StringUtils.isEmpty(e.getResponseBodyAsString())){
                    errorResponse = ErrorResponse.builder().error(e.getResponseBodyAsString()).build();
                }
                else{
                    errorResponse = ErrorResponse.builder().error("Error occured while processing request").build();
                }
            }
            return ResponseEntity.status(httpStatus).body(errorResponse);
        }
    }
}
