package com.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailDTO {

    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String message;

}
