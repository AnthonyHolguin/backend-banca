package com.anthony.devsu.backend.backend_banca.dtos;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransactionsReportDTO {
@JsonProperty("Fecha")
    private String fecha;
    
    @JsonProperty("Cliente")
    private String cliente;
    
    @JsonProperty("Numero Cuenta")
    private String numeroCuenta;
    
    @JsonProperty("Tipo")
    private String tipo;

    @JsonProperty("description")
    private String description;
    
    
    @JsonProperty("Saldo Inicial")
    private double saldoInicial;
    
    @JsonProperty("Estado")
    private boolean estado;
    
    @JsonProperty("Movimiento")
    private double movimiento;
    
    @JsonProperty("Saldo Disponible")
    private double saldoDisponible;

    @JsonProperty("Tipo transaccion")
    private TransaccionType tipoTransaccion;
}

   
