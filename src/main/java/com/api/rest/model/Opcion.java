package com.api.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opcion {

    @Id
    @Column(name = "OPCION_ID")
    private Long id;

    @Column(name = "VALUE")
    private String value;
}
