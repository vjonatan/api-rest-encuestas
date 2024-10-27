package com.api.rest.controller.v2;

import com.api.rest.dto.OptionCountDTO;
import com.api.rest.dto.VotoResultDTO;
import com.api.rest.model.Voto;
import com.api.rest.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("ComputeRestControllerV2")
@RequestMapping("/v2")
public class ComputeRestController {

    @Autowired
    VotoRepository votoRepository;

    @GetMapping("/calcularResultado")
    ResponseEntity<?> calcularVotos(@RequestParam Long encuestaId){

        VotoResultDTO votoResult = new VotoResultDTO();

        Iterable<Voto> votos = votoRepository.findByEncuesta(encuestaId);

        //Algoritmo para contar votos
        int totalVotos = 0;
        Map<Long, OptionCountDTO> tempMap = new HashMap<Long, OptionCountDTO>();

        for (Voto v : votos){
            totalVotos++;

            OptionCountDTO optionCount = tempMap.get(v.getOpcion().getId());

            if (optionCount == null) {
                optionCount = new OptionCountDTO();
                optionCount.setOpcionId(v.getOpcion().getId());
                tempMap.put(v.getOpcion().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount() + 1);
        }

        votoResult.setTotalVotos(totalVotos);
        votoResult.setResults(tempMap.values());

        return new ResponseEntity<>(votoResult, HttpStatus.OK);
    }

}
