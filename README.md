# Auth
MS para autenticação de usuarios

Projeto de estudo utilizando:

* Spring Boot
* Banco de dados H2 para ambiente de desenvolvimento
* Jacoco para cobertura de testes
* Swagger para documentação da API
* Flaway para versionamento de base

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

E um usuario padrão com login e senha **admin**

*Obs:* Essas configurações iniciais podem ser modificados no arquivo **data.sql** em *./src/main/resources* (arquivo de inicialização do banco H2, ambiente de desenvolvimento) ou no arquivo **V2__dados.sql** em *./src/main/resources/db/migration/postgreSQL* (arquivo de inicialização do banco de produção)

## Instalação

### Dev

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
./mvnw spring-boot:run -Pdev
```

Com a aplicação rodando é possivel acessar a [documentação do swagger](http://localhost:8080/swagger-ui.html)

### Prod


