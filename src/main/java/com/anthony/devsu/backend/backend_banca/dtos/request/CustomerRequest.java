package com.anthony.devsu.backend.backend_banca.dtos.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest { 
    
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras") 
    private String name;

    @Size(max = 1, message = "El género debe ser de máximo 1 caracter")
    private String genre;
 
    private int age;
    @NotBlank(message = "El nombre es obligatorio")
    private String identify;
    private String address;

    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    private String phone;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String password; 
    
    private Boolean status;
}
