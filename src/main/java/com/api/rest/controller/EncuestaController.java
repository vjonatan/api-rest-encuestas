package com.api.rest.controller;

import com.api.rest.model.Encuesta;
import com.api.rest.repository.EncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class EncuestaController {

    @Autowired
    EncuestaRepository encuestaRepository;

    @GetMapping("/encuestas")
    ResponseEntity<Iterable<Encuesta>> obtenerEncuestas(){
        return new ResponseEntity<>(encuestaRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/encuestas")
    ResponseEntity<?> crearEncuesta(@RequestBody Encuesta encuesta) {
        encuesta = encuestaRepository.save(encuesta);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(encuesta.getId()).toUri();
        httpHeaders.setLocation(newEncuestaUri);

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

    }

    @GetMapping("/encuestas/{encuestaId}")
    ResponseEntity<?> obtenerEncuesta(@PathVariable Long encuestaId){
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuestaId);

        if (encuesta.isPresent()){
            return new ResponseEntity<>(encuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/encuestas/{encuestaId}")
    ResponseEntity<?> eliminarEncuesta(@PathVariable Long encuestaId){

        encuestaRepository.deleteById(encuestaId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/encuestas/{encuestaId}")
    ResponseEntity<?> actualizarEncuesta(@RequestBody Encuesta encuesta, @PathVariable Long encuestaId){
        encuesta.setId(encuestaId);
        Encuesta udpated = encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
