package com.example.cloudvisionexample.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Image {
    private String imageBytes;
}
