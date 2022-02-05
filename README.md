# Sistema de Análise de Crédito

## Introdução  
Este projeto tem como objetivo o desenvolvimento de um Sistema de Análise de Crédito disponibilizado através de uma API REST.
O Sistema fornece aos clientes as seguintes funcionalidades:  
1. Cadastro de clientes  
O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.  
2. Login  
A autenticação é realizada por e-mail e senha.  
3. Solicitação de empréstimo  
Para solicitar um empréstimo, é preciso informar: valor do empréstimo, data da primeira parcela e quantidade de parcelas.    
O máximo de parcelas é 60 e a data da primeira parcela é no máximo 3 meses após o dia atual. O cliente também não pode solicitar dois empréstimos com as mesmas características, ou seja, com o mesmo valor, o mesmo número de parcelas e a mesma data de pagamento da primeira parcela.  
4. Acompanhamento das solicitações de empréstimo  
O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.    
Na listagem são retornados: código do empréstimo, valor, quantidade de parcelas e o status do empréstimo.  
No detalhe do empréstimo são retornados: o código do empréstimo, o valor, a quantidade de parcelas, a data da primeira parcela, o e-mail do cliente e a renda do cliente.  

## Tecnologias utilizadas  
As seguintes ferramentas foram utilizadas para o desenvolvimento do projeto:
- Java 17 
- Maven 3.8.4.
- IntelliJ IDEA Ultimate.
- Spring Boot Initializr para o setup inicial do projeto
- SDKMan! para o gerenciamento das versões de Java e Maven.
- Banco de dados H2 em ambiente de desenvolvimento.
- Banco de dados MySQL em ambiente de produção.
- Dependências: Spring Web, Spring Data JPA, Spring Security, Spring Boot DevTools, Spring Actuator, JSON Web Token, JUnit, Lombok, MapStruct, Springfox, MySQL connector, Flyway. 
- Swagger 2 para a documentação de todos os endpoints desenvolvidos.
- Git para o controle de versão e simulação de um fluxo de trabalho via criação de branches, pull requests e code reviews.
- GitHub para o armazenamento do projeto na nuvem.
- Heroku para o deploy do projeto na nuvem.
- Postman para a execução de testes de integração e de ponta a ponta da API.
- VS Code para a criação do arquivo de configuração do banco de dados.
- Docker para o gerenciamento do container do MySQL.

## Funcionalidades implementadas

- Criação de modelos de dados Customer (Cliente) e Loan (Empréstimo) para o mapeamento de entidades em bancos de dados.
  Para cada modelo, são criados um controller para receber as requisições HTTP, um repositório que extende a interface JpaRepository, e um serviço para o processamento das requisições e a implementação das regras do negócio.
  Com isso as responsabilidades de cada classe são bem separadas, o que resulta por exemplo em controllers com implementações mais limpas.

- Desenvolvimento de operações de gerenciamento de clientes e empréstimos de acordo com o padrão arquitetural REST.
  Neste projeto são implementadas as seguintes operações HTTP: POST e PUT para o cadastramento, a autenticação e a atualização dos dados de um cliente;
  POST, GET e DELETE para a solicitação, a listagem, o detalhamento e a exclusão de empréstimos pelo cliente.

- Utilização de DTOs (Data Transfer Objects) para o recebimento dos dados e as respostas das requisações.
  A conversão entre os modelos e os DTOs é feita com o gerador de código MapStruct.
  E cada modelo herda uma classe Auditable contendo campos sobre datas de criação e de última modificação dos cadastros no sistema.

- Desenvolvimento de autenticação e autorizaçao de usuários do Sistema através do Spring Security, e com suporte a JWT.
  São seis classes implementadas para esta funcionalidade sendo:
  - 4 classes de configuração (verificação da presença dos tokens nos headers, filtro das requisições e autorização de usuários, criptografia das senhas e liberação ou não de diferentes endpoints para diferentes ROLES), e 
  - 2 serviços (serviço de geração de tokens e serviço de autenticação dos usuários) 
   
- O Sistema está focado nas operações realizadas pelo cliente conforme os requisitos do projeto, e já está preparado para o cadastro de administradores ou funcionários através de ROLES implementados.
  As operações de atualização dos dados do cliente e  de exclusão dos empréstimos foram acrescentadas pois foram julgadas pertinentes para a natureza do Sistema desenvolvido.
  Numa evolução do sistema, o administrador poderá por exemplo estar cadastrado diretamente no banco de dados, e realizar operações de listar e deletar clientes, aprovar ou rejeitar solicitações de empréstimos.  
  Atualmente todas as solicitações de empréstimos recebem automaticamente, conforme esperado, o status "Submetido", e numa evolução do sistema, os clientes podem deletar somente empréstimos com esse status.

- Desenvolvimento de testes unitários para validação e evolução segura de novas funcionalidades.
  Tratamento de exceções. O Sitema desenvolvido conta com uma ampla cobertura de exceções geradas no cadastramento e na autenticação de clientes, e na solicitação de empréstimos.
  Documentação das operações HTTP com o Swagger e implantação do Sistema na nuvem através do Heroku.

## Execução da aplicação
Para executar o projeto em ambiente de desenvolvimento, comentar as dependencias do MySQL connector e do Flyway no pom.xml, 
e alterar o valor do active profile para "dev" no arquivo application.properties.
A execução do projeto no terminal pode ser feita via comando:  
```
mvn spring-boot:run
```
O endpoint de entrada do projeto é:  
```
http:localhost:8080/api/v1/customers
```
A doumentação pode ser acessada em:  
```
http:localhost:8080/swagger-ui.html
```

## Link
[Screenshots sobre o Sistema desenvolvido](https://github.com/endisl/tqi_evolution_backend_2021/blob/master/project_screenshots.pptx)  
