# Financial Organizar

O projeto consiste em um aplicativo web para registro de entradas e saídas de um negócio, possibilitando visualizar, cadastrar, marcar como efetivado, pendente e cancelado em todas as despesas e rendas cadastradas.

## Pré-requisitos

Certifique-se de ter as seguintes tecnologias instaladas antes de começar:

- [Node.js](https://nodejs.org/): O ambiente de execução JavaScript.
- [Java 21](https://www.oracle.com/br/java/technologies/downloads/): Linguagem de programação para desenvolvimento de aplicações.
- [MySql](https://www.mysql.com/):  Banco de dados SQL utilizado pelo projeto.
- [React](https://react.dev/): Biblioteca para desenvolvimento de aplicações web.
- [Maven](https://maven.apache.org/): Biblioteca para desenvolvimento de aplicações web.

## Configuração do Ambiente de Desenvolvimento

1. Clone o repositório:

```bash
git clone link-do-repositório
```

2. Para executar o ambiente você precisa ter o Java Jdk configurado na sua máquina. Os códigos se encontra na pasta Server.
O gerenciador de dependências utilizado foi o Maven, ao abrir o projeto na IDE certifique-se de que as demências foram carregadas corretamente.

3. Instale as dependências no Front-end (pasta /client):
`npm install`

4. Configure as variáveis de conexão com o banco de dados (se necessário):
Nos arquivo .yml você vai encontrar o perfil de "dev" e "test" você pode configurar o servidor, porta de execução, token Jwt, banco de dados e outras configurações que achar necessária para execução do projeto.

As configurações do application.yml base são: 
```
server:
  port: PORTA DE EXECUÇÃO DA APLICAÇÃO
cors:
  originPatterns: LINKS QUE SÃO PERMITIDOS NO CORS
security:
  jwt:
    token:
      secret-key: CHAVE SECRETA PARA GERAÇÃO DE TOKEN
      expire-length: TEMPO DE EXPIRAÇÃO
spring:
  application:
    name:  NOME DA APLICAÇÃO

  profiles:
    active: PERFIL QUE ESTÁ ATIVO DURANTE DESENVOLVIMENTO

  jpa:
    open-in-view: CONFIGURAÇÃO DA CONEXÃO AO BANCO DE DADOS COM J
```

4. Inicie o servidor de desenvolvimento do Front-end:
- `npm start`

## Scripts Disponíveis

No diretório do projeto Front-End, você pode executar os seguintes scripts:

- `npm start`:  Inicia o servidor da aplicação web utilizando o angular.
- `npm build`:  compila o projeto da aplicação web.

Certifique-se de executar `npm install` antes de usar esses scripts para garantir que todas as dependências sejam instaladas corretamente.

Lembre-se de ajustar os scripts conforme necessário para atender às suas necessidades específicas de desenvolvimento e produção.

## Outras informações

No Bakc-end é possível ver a documentação através da Swagger [OpenAPI](https://www.openapis.org/) acessando o path /swagger-ui/index.html#/;
Nesse path você consegue ver todas as requisições possíveis e mapeamento dos dados necessários para realizá-las.

## Tecnologias Utilizadas

No Back-End projeto faz uso das seguintes tecnologias e ferramentas:

- [Spring boot](https://spring.io/): Estrutura Java de código aberto usada para programar aplicativos.
- [Java 21](https://www.oracle.com/br/java/technologies/downloads/): Linguagem de programação para desenvolvimento de aplicações.
- [MySql](https://www.mysql.com/):  Banco de dados SQL utilizado pelo projeto.
- [Maven](https://maven.apache.org/): Biblioteca para desenvolvimento de aplicações web.
- [Jsonwebtoken](https://www.npmjs.com/package/jsonwebtoken): Implementação de JSON Web Tokens (JWT) para autenticação.
- [Flyway](https://flywaydb.org/): ferramenta de migração de banco de dados de código aberto.
- [JUnit 5](https://junit.org/): Framework open-source, que se assemelha ao raio de testes software java.
- [Mokito](https://site.mockito.org/): Framework de teste de código aberto para Java.
- [Rest-assured](https://rest-assured.io/):  Framework de teste de código para Java.

No Front-End projeto faz uso das seguintes tecnologias e ferramentas:

- [React](https://react.dev/): Biblioteca para desenvolvimento de aplicações web.
- [Axios](https://axios-http.com/): Cliente HTTP baseado em Promises para fazer requisições HTTP.
- [BootsWatch](https://bootswatch.com/):  Framework CSS para design responsivo.
- [BootsWatch Icons](https://bootswatch.com/):  Conjunto de ícones SVG customizáveis do Bootstrap.
- [Toastr](https://github.com/CodeSeven/toastr):  Biblioteca Javascript para notificações
- [Primereact](https://primereact.org/): UI Suite para React.
- [Primeicons](https://primeng.org/icons): Bibliiteca de icones.
- [Jsonwebtoken](https://jwt.io/): JSON Web Tokens (JWT).

## Autores

- [Willian Costa](https://github.com/costaowillian) - Desenvolvedor.
