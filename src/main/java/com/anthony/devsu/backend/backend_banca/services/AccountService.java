package com.anthony.devsu.backend.backend_banca.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;
import com.anthony.devsu.backend.backend_banca.dtos.request.AccountRequest;
import com.anthony.devsu.backend.backend_banca.dtos.response.AccountResponse;
import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.entities.Transactions;
import com.anthony.devsu.backend.backend_banca.repositories.AccountRepository;
import com.anthony.devsu.backend.backend_banca.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private TransactionsService transactionsService;

    public List<AccountResponse> findAll() {
        return this.mapListToResponse(accountRepo.findAll());
    }

    public Optional<AccountResponse> findById(Long id) {
        return accountRepo.findById(id).map(this::mapToResponse);
    }

    @Transactional
    public AccountResponse update(Long id, AccountRequest details) {
        return accountRepo.findById(id)
                .map(account -> {
                    // Actualización de campos básicos
                    account.setNumber(details.getNumber());
                    account.setType(details.getType());
                    account.setStatus(details.getStatus());

                    // Si en el update se envía un nuevo cliente, validamos su estado
                    if (account.getCustomer() != null && account.getCustomer().getId() != null) {
                        Customer customer = customerRepo.findById(account.getCustomer().getId())
                                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

                        if (!customer.getStatus()) {
                            throw new RuntimeException("No se puede asignar la cuenta a un usuario inactivo");
                        }
                        account.setCustomer(customer);
                    }

                    return this.mapToResponse(accountRepo.save(account));
                }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Transactional
    public AccountResponse save(AccountRequest request) {
        // 1. Verificar si el cliente existe en el JSON/Request

        Customer customer = customerRepo.findByIdentify(request.getCustomerIdentification());

        if (customer == null || customer.getId() == null) {
            throw new RuntimeException("La cuenta debe estar asociada a un cliente.");
        }

        // 3. VALIDACIÓN: Verificar si el usuario está inactivo
        // Según tu tabla de casos de uso, el estado es un String "True" o "False"
        if (!customer.getStatus()) {
            throw new RuntimeException("No se puede crear la cuenta: El usuario se encuentra inactivo.");
        }

        Boolean foundAccount = accountRepo.existsByNumber(request.getNumber());
        if (foundAccount) {
            throw new RuntimeException("No se puede crear la cuenta: Esta cuenta ya existe para otro usuario.");
        }

        // 4. Si está activo, asociamos el objeto completo y guardamos
        Account newAccount = this.setAccountEntity(request, customer);
        Account savedAccount = accountRepo.save(newAccount);
        if (savedAccount.getBalance() > 0) {
            Transactions initialMove = new Transactions();
            initialMove.setAccount(savedAccount);
            initialMove.setType(TransaccionType.DEPOSITO);
            initialMove.setDescription("Depósito inicial");
            initialMove.setValue(savedAccount.getBalance());
            // El balance de la transacción será el mismo saldo inicial
            initialMove.setDate(LocalDateTime.now());
            initialMove.setBalance(savedAccount.getBalance());

            // Llamamos al servicio de transacciones para persistirlo
            transactionsService.recordInitialTransaction(initialMove);
        }

        return this.mapToResponse(savedAccount);
    }

    public Account setAccountEntity(AccountRequest request, Customer customer) {
        Account entity = new Account();

        // Mapeo manual de campos
        entity.setNumber(request.getNumber());
        entity.setCustomer(customer);
        entity.setBalance(request.getBalance());
        entity.setType(request.getType());

        entity.setStatus(request.getStatus());

        return entity;
    }

    public AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();
 
        response.setId(account.getId());
        response.setNumber(account.getNumber());
        response.setType(account.getType());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus());
 
        if (account.getCustomer() != null) {
            response.setCustomerIdentification( account.getCustomer().getIdentify()); 
            response.setCustomerName(account.getCustomer().getName());
        }

        return response;
    }

    public List<AccountResponse> mapListToResponse(List<Account> accounts) {
    return accounts.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
}
