# Spring E-Commerce

Sistem E-Commerce sederhana menggunakan Spring Boot 3 dan Java 21 dengan dukungan JWT Authentication, RBAC, dan integrasi Payment Gateway Midtrans (dummy).
Dokumentasi API sudah tersedia via Swagger.

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Security (JWT, RBAC)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Swagger-UI
- Midtrans Payment Gateway (dummy)

## Fitur

- User Authentication (Register, Login, JWT Token)
- Role-based Access Control (Admin, Seller, User)
- Manajemen Produk (CRUD Produk)
- Payment Gateway Dummy (Midtrans)
- Wallet & Riwayat Transaksi
- Swagger API Docs

## Setup Project

1. Clone repository

```bash
git clone https://github.com/fauzinashrullah/spring-ecommerce.git
cd spring-ecommerce
```

2. File .env

```env
DB_USER=postgres
DB_PASS=postgres
DB_URL=jdbc:postgresql://localhost:5432/ecommercedb

JWT_SECRET=your-secret-key

MIDTRANS_SERVER_KEY=midtrans-server-key
```

3. Run app

```bash
./mvnw spring-boot:run
```

##  Dummy Accounts

You can use the following dummy accounts for testing:

### Seller
- **Email**: seller@example.com  
- **Password**: password123


## API Documentation

Full API documentation is available via Swagger UI:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)