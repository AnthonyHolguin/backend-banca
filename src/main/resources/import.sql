-- Personas
INSERT INTO person (id, name, genre, age, identify, address, phone) VALUES (21,'Jose Lemá','M',30,'1722547852','Otavalo sn y principal','098254785');
INSERT INTO person (id, name, genre, age, identify, address, phone) VALUES (22,'Marianela Montalvo','F',33,'1722547851','Amazonas y NNUU','097548965');
INSERT INTO person (id, name, genre, age, identify, address, phone) VALUES (23,'Juan Osorio','M',32,'1722547853','13 junio y Equinoccial','098874587');

-- Clientes
INSERT INTO customer (customer_id, password, status) VALUES (21,'password1234',1);
INSERT INTO customer (customer_id, password, status) VALUES (22,'password1233',1);
INSERT INTO customer (customer_id, password, status) VALUES (23,'password1236',1);

-- Cuentas
INSERT INTO account (id, number, type, balance, status, customer_id) VALUES (30,'478758','Ahorros',1425.00,1,21);
INSERT INTO account (id, number, type, balance, status, customer_id) VALUES (31,'225487','Corriente',700.00,1,22);
INSERT INTO account (id, number, type, balance, status, customer_id) VALUES (32,'495878','Ahorros',150.00,1,23);
INSERT INTO account (id, number, type, balance, status, customer_id) VALUES (33,'496825','Ahorros',0.00,1,22);
INSERT INTO account (id, number, type, balance, status, customer_id) VALUES (34,'585545','Corriente',1000.00,1,21);

-- Transacciones
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (1,'2026-02-23 16:52:05','DEPOSITO',2000,2000,30,'Depósito inicial');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (2,'2026-02-23 16:52:58','DEPOSITO',100,100,31,'Depósito inicial');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (3,'2026-02-23 16:54:20','DEPOSITO',540,540,33,'Depósito inicial');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (4,'2026-02-23 16:56:03','DEPOSITO',1000,1000,34,'Depósito inicial');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (5,'2026-02-23 17:17:22','RETIRO',575,1425,30,'Retiro de 575');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (6,'2026-02-23 17:20:11','DEPOSITO',600,700,31,'Deposito de 600');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (7,'2026-02-23 17:21:01','DEPOSITO',150,150,32,'Deposito de 150');
INSERT INTO transactions (id, date, type, value, balance, account_id, description) VALUES (8,'2026-02-23 17:21:49','RETIRO',540,0,33,'Retiro de 540');