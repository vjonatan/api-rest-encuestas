package com.api.rest.repository;

import com.api.rest.model.Voto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long> {

    @Query(value = "SELECT v.* FROM opcion o, voto v WHERE o.encuesta_id = ?1 AND v.opcion_id = o.opcion_id", nativeQuery = true)
    Iterable<Voto> findByEncuesta(Long encuestaId);

}
