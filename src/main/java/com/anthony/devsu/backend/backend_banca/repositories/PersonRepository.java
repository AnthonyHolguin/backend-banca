package com.anthony.devsu.backend.backend_banca.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.anthony.devsu.backend.backend_banca.entities.Person;

@RepositoryRestResource(path = "person")
public interface PersonRepository extends CrudRepository<Person, Long>{

    
}  
