package com.example.cloudvisionexample.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Payload {
    private String annotationSpecId;
    private ImageObjectDetection imageObjectDetection;
    private String displayName;
}
