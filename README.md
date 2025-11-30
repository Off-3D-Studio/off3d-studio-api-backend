# ![Off3D Studio](docs/favicon.ico) Off3D Studio – API Backend
API backend para o gerenciamento de operações de uma empresa de impressão 3D, desenvolvida com Spring Boot 4, Java 21, integração com PostgreSQL, H2, Flyway, JPA/Hibernate, Thymeleaf e validação.

---

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![Tests](https://img.shields.io/badge/Tests-Passing-success)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 📌 Visão Geral

Este repositório contém a API backend do Off3D Studio, estruturada com uma arquitetura em camadas, migrações de banco com Flyway, persistência via JPA/Hibernate e testes completos (unitários e de integração).
O projeto foi construído pensando em escalabilidade, manutenção e boas práticas de desenvolvimento em Java.
---

## 🛠️ Tecnologias e Dependências Principais

- **Java 21**
- **Spring Boot 4.0.0**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Validation
  - Thymeleaf
  - Flyway Migration
- **Banco de Dados**
  - PostgreSQL
  - H2 (para testes e ambiente local)
- **Lombok**
- **JUnit + Spring Boot Starter Test**

---

## 🏗️ Arquitetura do Projeto

A API segue uma arquitetura em camadas:
```
src/
└── main/
├── java/com/off3dstudio/off3dbackend
│   ├── controller/        # Camada de entrada (REST Controllers)
│   ├── service/           # Regras de negócio
│   ├── repository/        # Acesso ao banco (JPA)
│   ├── dto/               # Objetos de transferência de dados
│   ├── exception/         # Regras de exceção
│   └── model/             # Models gerais
│
└── resources/
    ├── application.properties   # Configurações da aplicação
    ├── db/migration/            # Scripts Flyway
    ├── static/                  # Arquivos estáticos
    └── templates/               # Views Thymeleaf (se aplicável)
```

### 🔄 Fluxo de Requisições

**Controller → Service → Repository → Banco de Dados → Persistência**  
**↑**  
**Models / DTO**


- **Controllers** recebem e retornam dados via JSON.
- **Services** centralizam regras de negócio e mapeamentos.
- **Repositories** fazem consultas SQL via JPA/Hibernate.
- **Flyway** gerencia evolução do esquema do banco.

---

## 🚀 Como Executar o Projeto
### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-repositorio/off3d-studio-api-backend.git
cd off3d-studio-api-backend
```

### 2. Rodar a aplicação
```bash
mvn spring-boot:run
```

A API estará disponível em:
```
http://localhost:8080
```


## 🧪 Testes

O projeto possui: 

- ✔ Testes unitários 
- ✔ Testes de integração (Repositories via H2)
- ✔ Testes para validação de mapeamento DTO/JPQL
- ✔ Prevenção de falhas no startup do contexto

### ▶️ Rodar todos os testes

```bash
./mvnw.cmd -B clean test
```

## 🗄️ Configuração de Banco de Dados
### ▶ Ambiente Local (H2)

Console do H2:
```
http://localhost:8080/h2-console
```
