package com.api.rest.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class VotoResultDTO {

    private int totalVotos;
    private Collection<OptionCountDTO> results;

}
