package com.anthony.devsu.backend.backend_banca.entities;

import jakarta.persistence.Entity; 
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "customer_id")
@Getter
@Setter
public class Customer extends Person {

  
    private String password; 
    
    private Boolean status;
 
}
