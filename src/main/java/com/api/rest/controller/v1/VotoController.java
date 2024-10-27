package com.api.rest.controller.v1;

import com.api.rest.model.Voto;
import com.api.rest.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("VotoControllerV1")
@RequestMapping("/v1")
public class VotoController {

    @Autowired
    VotoRepository votoRepository;

    @PostMapping("/encuestas/{encuestaId}/voto")
    ResponseEntity<?> crearVoto(@PathVariable Long encuestaId, @RequestBody Voto voto) {
        Voto votoSaved = votoRepository.save(voto);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(votoSaved.getId()).toUri();

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/encuestas/{encuestaId}/votos")
    ResponseEntity<Iterable<Voto>> listarTodosLosVotos(@PathVariable Long encuestaId){
        Iterable<Voto> votos = votoRepository.findByEncuesta(encuestaId);
        return new ResponseEntity<>(votos, HttpStatus.OK);
    }

}
