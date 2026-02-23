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

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String password; 
    
    private Boolean status;
 
}
