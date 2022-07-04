[![Test and build Kotlin app with Gradle](https://github.com/LucasTrevizanbr/trace-finance-payment-api/actions/workflows/gradle.yml/badge.svg)](https://github.com/LucasTrevizanbr/trace-finance-payment-api/actions/workflows/gradle.yml)


<h1 align="center">🎯 Sobre a API</h1>

A API tem como propósito controlar pagamentos para uma carteira, aplicando regras de limite com base em periodos dos dias.

<h1 align="center">📦 Desenvolvimento</h1>

A api foi construída com kotlin em conjunto com varios projetos do framework Spring, 

<h1 align="center">📌 Documentação </h1>

# Importante:
Devido a natureza da aplicação precisar lidar com horários diferentes é necessário testa-la de uma certa maneira. Para que fique claro os passos eu
coloquei uma documentação detalhada de testes na pasta documentação/teste, por favor siga ela para conseguir testar todos os cenários funcionalmente.

Além da documentação de testes, também adicionei documentações importantes sobre o projeto como um todo, são elas:
- Payments-containers : Aqui eu dou uma visão sobre como roda o ambiente da aplicação de maneira conteinerizada e explico como funciona os arquivos docker da aplicação
- Payments-Visão arquitetural: Aqui eu comento sobre as decisões de arquitetura da aplicação e estrutura de pacotes, aplicando um pouco de DDD
- Payments-Uma visão mais precisa: Aqui eu falo sobre o código core das funcionalidades, lógicas implementadas, validações, padrão utilizado etc..

</div>
<h1 align="center"> 💻 Requisitos de inicialização do projeto</h1>

Com esses comandos você consegue utilizar o projeto em ambiente conteinerizado.

### 📋 Pré-requisitos

- [JDK 17 ou superior] (https://www.oracle.com/java/technologies/downloads/#java17)
- [Docker](https://docs.docker.com/desktop/windows/install/)
- [docker-compose](https://docs.docker.com/compose/install/)


 <h1 align="center">📍 Rodando a aplicação</h1>

```
bash

Clone este repositório
$ git clone https://github.com/LucasTrevizanbr/trace-finance-payment-api.git

Acesse a pasta do projeto no terminal/cmd
$ cd ./payment

Faça o build do projeto com o seguinte comando , não precisa ter o gradle instalado:(se estiver no prompt do windowns não use "./")
$ ./gradlew clean bootjar

Volte para o diretório principal que contém o docker-compose.yml:
$ cd ..

Agora basta subir os containers que contem a aplicação e o banco de dados
$ docker-compose up 
```

O servidor iniciará na porta:8080 - acesse http://localhost:8080/swagger-ui/#/ para ter acesso direto a documentação

*caso tenha ocorrido algum erro, por favor verifique a documentação sobre docker da aplicação*

<h1 align="center">✔️ Verificando se o projeto esta on</h1>

Se tudo deu certo no processo de construção dos containers, você irá ver uma imagem assim:
![docker-compose](https://user-images.githubusercontent.com/72326473/177079786-e71f8d0b-8167-4346-b684-fb02da10a0ba.png)

se tiver usando o windowns e possuir o docker desktop, então você precisa ver algo assim:
![image](https://user-images.githubusercontent.com/72326473/177079892-87259099-1ad6-443c-ad1c-1cb1f14d1d82.png)

