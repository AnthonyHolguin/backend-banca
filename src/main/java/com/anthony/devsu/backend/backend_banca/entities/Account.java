package com.anthony.devsu.backend.backend_banca.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax; 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern; 
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de cuenta solo debe contener números")
    @Column(unique = true, nullable = false)
    private String number;

    @NotBlank(message = "El tipo de cuenta es obligatorio") 
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El tipo de cuenta solo debe contener letras") 
    private String type;

    @Column(columnDefinition = "DECIMAL(10,2)")
    @DecimalMax(value = "999999.99", message = "El saldo ha superado el límite")
    private double balance; 

    private Boolean status;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false) 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;
 
}
