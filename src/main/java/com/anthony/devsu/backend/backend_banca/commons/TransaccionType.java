package com.anthony.devsu.backend.backend_banca.commons;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransaccionType {
   @JsonProperty("Deposito") DEPOSITO,
    @JsonProperty("Retiro") RETIRO
}
