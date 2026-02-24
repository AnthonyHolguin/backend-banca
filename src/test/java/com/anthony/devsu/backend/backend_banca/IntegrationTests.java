package com.anthony.devsu.backend.backend_banca;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;
import com.anthony.devsu.backend.backend_banca.dtos.request.TransactionsRequest;
import com.anthony.devsu.backend.backend_banca.dtos.response.AccountResponse;
import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.entities.Transactions;
import com.anthony.devsu.backend.backend_banca.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFlujoCompletoBanco() throws Exception {
        // 1. Crear Cliente
        Customer jose = new Customer();
        jose.setName("Jose Lema");
        jose.setGenre("M");
        jose.setAge(25);
        jose.setIdentify("1234567883");
        jose.setAddress("Otavalo sn y principal");
        jose.setPhone("098254785");
        jose.setPassword("12ssqs34");
        jose.setStatus(true);

        String clienteJson = mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jose)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        Customer joseCreado = objectMapper.readValue(clienteJson, Customer.class);

        // 2. Crear Cuenta para Jose (Saldo inicial 100)
        Account cuenta = new Account();
        cuenta.setNumber("1234562222222");
        cuenta.setType("Ahorros");
        cuenta.setBalance(100.0);
        cuenta.setStatus(true);
        cuenta.setCustomer(joseCreado);

        AccountService service = new AccountService();
        

        String cuentaJson = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service.mapToResponse(cuenta))))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        AccountResponse cuentaCreada = objectMapper.readValue(cuentaJson, AccountResponse.class);

        // 3. Intento de Retiro de 200 (F3: Validar "Saldo no disponible")
        TransactionsRequest retiroFallido = new TransactionsRequest();
        retiroFallido.setAccountNumber(cuentaCreada.getNumber());
        retiroFallido.setType(TransaccionType.RETIRO);
        retiroFallido.setValue(200.0);

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(retiroFallido)))
                .andExpect(status().isBadRequest()) // 400 Bad Request
                .andExpect(content().string("Saldo no disponible")); // Mensaje requerido en F3


        // 3. Intento de Retiro de 200 (F3: Validar "Saldo no disponible")
        TransactionsRequest deposito = new TransactionsRequest();
        deposito.setAccountNumber(cuentaCreada.getNumber());
        deposito.setType(TransaccionType.DEPOSITO);
        deposito.setValue(200.0);

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deposito)))
                .andExpect(status().isCreated()) // 201 ok
                .andReturn().getResponse().getContentAsString();



    }

      @Test
    public void testFlujoUsuarioInactivo() throws Exception {
        // 1. Crear Cliente 
        Customer jose = new Customer();
        jose.setName("Marianela Montalvo");
        jose.setGenre("F");
        jose.setAge(25);
        jose.setIdentify("1234567801");
        jose.setAddress("Amazonas y NNUU");
        jose.setPhone("097548965");
        jose.setPassword("123453223");
        jose.setStatus(false);

        String clienteJson = mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jose)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        Customer created = objectMapper.readValue(clienteJson, Customer.class);

        // 2. Crear Cuenta para Jose (Saldo inicial 100)
        Account cuenta = new Account();
        cuenta.setNumber("1234567");
        cuenta.setType("Ahorros");
        cuenta.setBalance(100.0);
        cuenta.setStatus(true);
        cuenta.setCustomer(created);
        AccountService service = new AccountService();
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service.mapToResponse(cuenta))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede crear la cuenta: El usuario se encuentra inactivo.")); // Mensaje requerido en F3




    }
}
