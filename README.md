[![Test and build Kotlin app with Gradle](https://github.com/LucasTrevizanbr/trace-finance-payment-api/actions/workflows/gradle.yml/badge.svg)](https://github.com/LucasTrevizanbr/trace-finance-payment-api/actions/workflows/gradle.yml)


<h1 align="center">🎯 Sobre a API</h1>

A API tem como propósito controlar pagamentos para uma carteira, aplicando regras de limite com base em periodos dos dias.

<h1 align="center">📦 Desenvolvimento</h1>

A api foi construída com kotlin em conjunto com varios projetos do framework Spring, 

<h1 align="center"> 💻 Requisitos de inicialização do projeto</h1>

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento.

Consulte *Implantação* para saber como implantar o projeto.

### 📋 Pré-requisitos

Para rodar a aplicação é altamente recomendado usar o docker. Portanto você precisa de:

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

<h1 align="center">📌 Documentação </h1>
Além do Swagger, eu tomei a liberdade de criar algumas documentações a respeito do projeto. Decisões sobre arquitetura, implementações, ideia da API, um pouco sobre docker e alguns quesitos técnicos do desenvolviemnto. Basta acessar a pasta "documentação", lá contém os seguintes tópicos abordados:

</div>


