# Auth-API
MS para gerenciamento e autenticação de usuarios

Projeto de estudo utilizando:

* Spring Boot
* Banco de dados H2 para ambiente de desenvolvimento
* Banco de dados PostgreSQL para ambiente de prod
* Flaway para versionamento de base
* Jacoco para cobertura de testes
* Swagger para documentação da API

## Em que consiste o projeto 

Uma API onde é possivel gerenciar:

* Funcionalidades
* Perfis
* Usuarios

Por padrão serão criados as funcionalidades:

* ADMIN
* LISTAR_FUNCIONALIDADE
* CRIAR_FUNCIONALIDADE
* ATUALIZAR_FUNCIONALIDADE
* EXCLUIR_FUNCIONALIDADE
* LISTAR_PERFIL
* CRIAR_PERFIL
* ATUALIZAR_PERFIL
* EXCLUIR_PERFIL
* LISTAR_USUARIO
* CRIAR_USUARIO
* ATUALIZAR_USUARIO
* EXCLUIR_USUARIO

E os perfis:

* Administrador (que tera permissão para executar todas as ações na API)
* Usuario (sem nenhuma permissão)

E um usuario padrão com login e senha **admin** e perfil **Administrador**

*Obs:* Essas configurações iniciais podem ser modificados no arquivo **data.sql** em *./src/main/resources* (arquivo de inicialização do banco H2, ambiente de desenvolvimento) ou no arquivo **V2__dados.sql** em *./src/main/resources/db/migration/postgreSQL* (arquivo de inicialização do banco de produção)

## Instalação

### Profile Dev

Clonar o projeto

```
git clone https://github.com/Eduardo32/auth.git
```

Entrar na pasta do projeto

```
cd auth
```

Subir projeto

```
./mvnw clean install && ./mvnw spring-boot:run -Pdev
```

Com a aplicação rodando é possivel acessar a [documentação do swagger](http://localhost:8080/swagger-ui.html)

### Profile Prod

#### Variaveis de Ambiente

* AUTH_DATABASE_URL= URL de conexão com banco de dados (com uma tabela auth criada)
* AUTH_DATABASE_USER= Usuario da banco de dados
* AUTH_DATABASE_PASSWORD= Senha do banco de dados 
* AUTH_SECRET= Secrec para assinatura do token

Clonar o projeto

```
git clone https://github.com/Eduardo32/auth.git
```

Entrar na pasta do projeto

```
cd auth
```

Subir projeto

```
./mvnw clean install && ./mvnw spring-boot:run -Pprod
```

### Profile Prod com Docker

```
docker run --name <NOME_CONTAINER> -p 8080:8080 -e AUTH_DATABASE_URL=URL -e AUTH_DATABASE_USER=USER -e AUTH_DATABASE_PASSWORD=PASSWORD -e AUTH_SECRET=SECRET -d pauloeduardocosta/auth-api:0.0.1
```
