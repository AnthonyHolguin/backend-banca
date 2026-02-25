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


    @Column(unique = true, nullable = false)
    private String number;


    private String type;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private double balance; 

    private Boolean status;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false) 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;
 
}
