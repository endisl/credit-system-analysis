[add SonarCloud badge  
do runner Test in Postman]
# Sistema de Análise de Crédito

## Introdução  

Processo Seletivo TQI  

Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades:  
i. Cadastro de clientes  
O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.  
ii. Login  
A autenticação será realizada por e-mail e senha.  
iii. Solicitação de empréstimo  
Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.  
O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.  
iv. Acompanhamento das solicitações de empréstimo  
O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.  
Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.  
No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.  

## Tecnologias utilizadas  

Spring Boot
Spring Data JPA 
Spring Security 
Spring Boot DevTools
Spring Actuator 
JUnit 
MapStruct 
Lombok
Flyway
MySQL Driver
SonarCloud 
Heroku
Docker 

## Entidades e DTO  

## Controllers  

## Serviços  

## Repositórios  

## Autenticação e Autorização  

## Regras de Negócio

O Projeto pede explicitamente a implementaçao de duas regras de negócio: max=60 and date<=date+3months

Outra regra de negócio acrescentado no projeto é sobre o Cliente não poder fazer dois empréstimos com as mesmas características, ou seja,
com o mesmo valor, o mesmo número de parcelas e a mesma data do pagamento da primeira parcela

## Testes  

## FrontEnd

## Links  
