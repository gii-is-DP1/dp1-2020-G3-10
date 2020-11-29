package org.springframework.samples.petclinic.repository;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Plataforma;

public interface PlataformaRepository extends CrudRepository<Plataforma,Integer>{


}
