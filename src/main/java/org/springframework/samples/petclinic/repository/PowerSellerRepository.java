package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.PowerSeller;


public interface PowerSellerRepository extends  CrudRepository<PowerSeller, Integer>{
	
}
