  <h1 align="center">★ Alura ★</h1>

---

## Descrição

Este é um projeto back-end para um aplicativo de gestão de usuários e cursos

## Objetivo

Esse projeto foi criado como um desafio de cadastros de alunos, cursos, matrículas e avaliações de cursos.
Com ele conseguimos usar as funcionalidades básicas de um site de cursos, como o Alura.

## Tecnologias

- ``Java 21``
- ``Maven``
- ``Spring``
- ``MySQL``
- ``JPA``
- ``Flyway``
- ``Lombok``
- ``Swagger``
- ``Docker``

## Como usar

As imagens deste projeto estão disponívels no [Docker Hub](https://hub.docker.com/u/arielgpaz) e podem ser usadas
através do `docker-compose.yaml`, basta seguir os passos abaixo:

1) Com o Docker instalado e estando na pasta raiz do projeto, executar o comando: `docker compose up -d`
2) Abrir o swagger: [Swagger](http://localhost:7001/swagger-ui/index.html)

## Próximos passos:

1. Implementar regras de negócio mais detalhadas e que não estavam previstas nos requisitos, como:
    1. Permitir usuário avaliar apenas o curso que ele estiver matriculado;
    2. Não permitir matrícula do instrutor em seus próprios cursos...
2. Implementar novas funcionalidades:
    1. Criar tratamentos para exceptions que permitem retornos mais explicativos dos erros (ExceptionHandler);
    2. Completar o CRUD para usuários, cursos, matrículas e avaliações;
    3. Criptografar senhas no banco de dados;
    4. Implementar o Spring Security com os acessos baseados em roles;
    5. Gerar o relatório de NPS através de um arquivo `.csv` e/ou `.pdf`...
