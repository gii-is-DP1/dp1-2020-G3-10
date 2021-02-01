package org.springframework.samples.petclinic.repository;

import javax.validation.Valid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Reproductor;

public interface ReproductorRepository extends CrudRepository<Reproductor,Integer>{


}
