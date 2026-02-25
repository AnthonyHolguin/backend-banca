package com.anthony.devsu.backend.backend_banca.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;
import com.anthony.devsu.backend.backend_banca.dtos.TransactionsReportDTO;
import com.anthony.devsu.backend.backend_banca.dtos.request.TransactionsRequest;
import com.anthony.devsu.backend.backend_banca.dtos.response.AccountResponse;
import com.anthony.devsu.backend.backend_banca.dtos.response.TransactionsResponse;
import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.entities.Transactions;
import com.anthony.devsu.backend.backend_banca.exceptions.InsufficientBalanceException;
import com.anthony.devsu.backend.backend_banca.repositories.AccountRepository;
import com.anthony.devsu.backend.backend_banca.repositories.TransactionsRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository movimientoRepo;
    @Autowired
    private AccountRepository accountRepo;

    @Transactional
    public TransactionsResponse registrarMovimiento(TransactionsRequest request) {
        Account account = accountRepo.findByNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Customer customer = account.getCustomer();
        if (customer == null) {
            throw new RuntimeException("Error: La cuenta no tiene un cliente asociado");
        }

        if (!customer.getStatus()) {
            throw new RuntimeException("Usuario inactivo: No puede realizar transacciones");
        }

        if (account.getStatus() != null && !account.getStatus()) {
            throw new RuntimeException("Cuenta inactiva: No se permiten movimientos");
        }

        Transactions trans = this.setTransactionEntity(request, account);
        double nuevoSaldo = 0;
        if (trans.getType().equals(TransaccionType.DEPOSITO)) {
            nuevoSaldo = account.getBalance() + trans.getValue();
        } else {
            nuevoSaldo = account.getBalance() - trans.getValue();
        }

        if (nuevoSaldo < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        account.setBalance(nuevoSaldo);
        accountRepo.save(account);

        trans.setBalance(nuevoSaldo);
        trans.setDate(LocalDateTime.now());
        return this.getResponse(movimientoRepo.save(trans));
    }

    private Transactions setTransactionEntity(TransactionsRequest request, Account account) {
        Transactions trans = new Transactions();
        trans.setAccount(account);
        trans.setDescription(request.getDescription());
        trans.setValue(request.getValue());
        trans.setType(request.getType());
        return trans;
    }

    @Transactional
    public Transactions recordInitialTransaction(Transactions trans) {
        return movimientoRepo.save(trans);
    }

    public List<TransactionsReportDTO> generateReport(Long clienteId, LocalDateTime inicio, LocalDateTime fin) {
        List<Transactions> transacciones = movimientoRepo.findByClienteAndFecha(clienteId, inicio, fin);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss");

        return transacciones.stream().map(t -> {
            TransactionsReportDTO dto = new TransactionsReportDTO();
            dto.setFecha(t.getDate().format(formatter));
            dto.setCliente(t.getAccount().getCustomer().getName());
            dto.setNumeroCuenta(t.getAccount().getNumber());
            dto.setTipo(t.getAccount().getType());
            dto.setEstado(t.getAccount().getStatus());
            dto.setMovimiento(t.getValue());
            dto.setSaldoDisponible(t.getBalance());
            dto.setSaldoInicial(t.getBalance() + t.getValue());
            dto.setTipoTransaccion(t.getType());
            dto.setDescription(t.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    public TransactionsResponse getResponse(Transactions transaction) {
        TransactionsResponse response = new TransactionsResponse();
 
        response.setId(transaction.getId());
        response.setDate(transaction.getDate());
        response.setType(transaction.getType());
        response.setDescription(transaction.getDescription());
        response.setValue(transaction.getValue());
        response.setBalance(transaction.getBalance());
 
        if (transaction.getAccount() != null) {
            AccountResponse accountRes = new AccountResponse();
            accountRes.setId(transaction.getAccount().getId());
            accountRes.setNumber(transaction.getAccount().getNumber());
            accountRes.setType(transaction.getAccount().getType());
            accountRes.setStatus(transaction.getAccount().getStatus());
            if(transaction.getAccount().getCustomer() != null ){ 
                accountRes.setCustomerIdentification(transaction.getAccount().getCustomer().getIdentify());
                accountRes.setCustomerName(transaction.getAccount().getCustomer().getName());
            }
            response.setAccount(accountRes);
        }

        return response;
    }
}
