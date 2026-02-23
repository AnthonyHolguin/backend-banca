package com.anthony.devsu.backend.backend_banca.entities;

import java.time.LocalDateTime;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @NotNull(message = "El tipo de transacción es obligatorio")
    @Enumerated(EnumType.STRING)    
    private TransaccionType type;
    
    private String description;

    @Column(columnDefinition = "DECIMAL(10,2)")
    @DecimalMax(value = "15000.00", message = "El valor ha superado el límite 15000.00.")
    
    private double value;

    @Column(columnDefinition = "DECIMAL(10,2)")
    @DecimalMax(value = "999999.99", message = "El saldo ha superado el límite 999999.99.")
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Account account;

    

  

}
