package com.anthony.devsu.backend.backend_banca.entities;
 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
