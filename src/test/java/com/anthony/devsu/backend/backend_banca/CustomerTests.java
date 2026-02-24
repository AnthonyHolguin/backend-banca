package com.anthony.devsu.backend.backend_banca;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.anthony.devsu.backend.backend_banca.entities.Customer;

public class CustomerTests {
    @Test
    @DisplayName("Debería crear un cliente y validar la herencia de Persona")
    void shouldCreateCustomerWithInheritedFields() {
        Customer customer = new Customer();
        
        // Campos heredados de Person
        customer.setName("Anthony");
        customer.setIdentify("1234567890");
        customer.setAge(25);
        customer.setAddress("Calle Principal 123");
        
        customer.setPassword("secure123");
        customer.setStatus(true);

        assertAll("Validación de campos del Cliente",
            () -> assertEquals("Anthony", customer.getName(), "El nombre heredado no coincide"),
            () -> assertEquals("1234567890", customer.getIdentify(), "La identificación no coincide"),
            () -> assertEquals("secure123", customer.getPassword(), "La contraseña no coincide"),
            () -> assertEquals(true, customer.getStatus(), "El estado no coincide")
        );
    }

    @Test
    @DisplayName("Debería validar que el cliente puede heredar el ID de Persona")
    void shouldHandleIdentityInheritance() {
        Customer customer = new Customer();
        Long expectedId = 5L;
        
        customer.setId(expectedId);
        
        assertEquals(expectedId, customer.getId(), "El ID heredado debería ser accesible y correcto");
    }
}
