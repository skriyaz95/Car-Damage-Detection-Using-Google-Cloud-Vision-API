package com.example.cloudvisionexample.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ImageResponse {
    private List<Payload> payload;
}
