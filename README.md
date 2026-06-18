# DRIPSTOCK

API REST de e-commerce de streetwear, construída em Java com Spring Boot. Projeto pessoal de estudo, desenvolvido com foco em arquitetura limpa, boas práticas de backend e preparação para o mercado de trabalho.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat-square)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-lightgrey?style=flat-square)

---

## Sobre o projeto

A DRIPSTOCK simula o backend de uma loja de streetwear: catálogo de produtos, carrinho de compras, pedidos e autenticação de usuários. O objetivo foi praticar arquitetura em camadas, persistência de dados relacional e segurança com JWT, seguindo padrões usados em aplicações Java reais de mercado.

## Funcionalidades

- Cadastro e login de usuários com autenticação via JWT
- Catálogo de produtos com categorias, estoque e paginação
- Carrinho de compras (adicionar, atualizar quantidade, remover item)
- Criação de pedidos a partir do carrinho, com baixa de estoque transacional
- Tratamento centralizado de erros e validação de entrada
- Testes unitários e de integração
- Ambiente containerizado com Docker

## Stack técnica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3 (Web, Security, Data JPA, Validation) |
| Banco de dados | PostgreSQL |
| Migrations | Flyway |
| Autenticação | JWT (JSON Web Token) |
| Testes | JUnit 5, Mockito, Testcontainers |
| Documentação da API | Swagger / OpenAPI |
| Containerização | Docker, Docker Compose |
| Build | Maven |

## Arquitetura

O projeto segue separação em camadas inspirada em Clean Architecture:

```
controller  →  service  →  repository  →  banco de dados
```

- **controller**: recebe requisições HTTP e retorna respostas. Não contém lógica de negócio.
- **service**: concentra as regras de negócio (cálculo de carrinho, validação de pedido, etc).
- **repository**: interfaces Spring Data JPA responsáveis pelo acesso ao banco.
- **model**: entidades JPA mapeadas para as tabelas do banco.
- **dto**: objetos de transferência de dados entre camadas e cliente.
- **security**: configuração de autenticação e filtros JWT.

## Modelo de dados

Principais entidades e relacionamentos:

- `User` → possui muitos `Order` e um `CartItem` por produto no carrinho
- `Product` → pertence a uma `Category`, aparece em vários `CartItem` e `OrderItem`
- `Order` → possui muitos `OrderItem`, pertence a um `User`
- `CartItem` → liga `User` e `Product` com quantidade

## Endpoints principais

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| POST | `/auth/register` | Cadastra um novo usuário | Não |
| POST | `/auth/login` | Autentica e retorna token JWT | Não |
| GET | `/products` | Lista produtos (paginado, filtrável por categoria) | Não |
| GET | `/products/{id}` | Detalha um produto | Não |
| POST | `/cart` | Adiciona item ao carrinho | Sim |
| PUT | `/cart/{itemId}` | Atualiza quantidade de um item | Sim |
| DELETE | `/cart/{itemId}` | Remove item do carrinho | Sim |
| POST | `/orders` | Cria pedido a partir do carrinho atual | Sim |
| GET | `/orders` | Lista pedidos do usuário autenticado | Sim |

Documentação interativa completa disponível via Swagger em `/swagger-ui.html` após rodar o projeto.

## Como rodar localmente

### Pré-requisitos

- Java 21
- Maven
- Docker e Docker Compose

### Passo a passo

```bash
# clonar o repositório
git clone https://github.com/seu-usuario/dripstock.git
cd dripstock

# subir banco de dados via Docker
docker-compose up -d

# rodar as migrations e iniciar a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Rodando com Docker (aplicação completa)

```bash
docker-compose -f docker-compose.full.yml up --build
```

### Variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```
DB_URL=jdbc:postgresql://localhost:5432/dripstock
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=sua_chave_secreta_aqui
JWT_EXPIRATION=86400000
```

## Rodando os testes

```bash
./mvnw test
```

## Estrutura do projeto

```
dripstock/
├── src/main/java/com/dripstock/
│   ├── config/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── dto/
│   └── security/
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/
├── src/test/
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Próximos passos

- [ ] Implementar avaliações de produtos
- [ ] Adicionar integração com gateway de pagamento (simulado)
- [ ] Implementar cache com Redis para catálogo de produtos
- [ ] Pipeline de CI/CD com GitHub Actions

## Autor

Desenvolvido por **Kalled Abdala** como projeto de estudo no caminho para se tornar desenvolvedor backend Java.

[LinkedIn](https://www.linkedin.com/in/kalledabdala/
) · [GitHub](https://github.com/KalledAbdala)

## Licença

Este projeto está sob a licença MIT.
