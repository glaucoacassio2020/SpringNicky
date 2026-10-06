# Leque de Vagas

API de estudo para cadastrar vagas de tecnologia, empresas e pessoas.
Projeto em Java 21, Spring Boot 4.1.1 e Maven.

## O que está implementado

| Aula | Aplicação no projeto |
| --- | --- |
| 01 | Spring Boot, Maven, configuração e endpoints HTTP |
| 02 | Controller, service e repository; injeção pelo construtor em campos final |
| 03 | CRUD, 200/201/204/404, Location, filtros e requisições .http |
| 04 | DTOs com record, @Valid, validação de campos, validador customizado e Problem Details |
| 05 | @Entity, @Id, @GeneratedValue, @Column, JpaRepository, H2 e ddl-auto |

Os materiais das aulas 03 e 04 foram usados como referência. Os links da aula 05
retornaram 404 na revisão de 06/10/2026; essa etapa segue a ementa fornecida.
Isso não confirma requisitos adicionais de um desafio da aula 05 indisponível.

## Como executar

Instale o JDK 21 e confira:

```bash
java -version
./mvnw -v
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.
Se o terminal recusar a execução do wrapper no Linux/macOS, use
`chmod +x mvnw`. No IntelliJ, configure o SDK do projeto e o JDK do Maven
como 21, recarregue o pom.xml e execute TechApplication.

O Maven Wrapper baixa o Maven e as dependências na primeira execução.
A API usa **http://localhost:8081**. Para encerrar, pressione Ctrl+C.
Se a porta estiver ocupada:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
```

Nesse caso, ajuste também a porta das requisições.

## Organização

O controller recebe a requisição e define o status HTTP.
O service monta os objetos, aplica as regras e converte os DTOs.
O repository usa o Spring Data JPA para consultar e gravar no banco.

- Vaga, Empresa e Pessoa: entidades do banco.
- Entrada: campos recebidos e regras de validação.
- Resposta: campos devolvidos pela API.
- TratadorDeErros: tratamento centralizado das exceções.
- data.sql: dados de exemplo carregados na inicialização.
- CONTRATO.md: campos obrigatórios e decisões de validação.

Produto e ProdutoController são o exemplo antigo da primeira aula, mantido
separado do Leque de Vagas. A rota /produtos ainda usa lista em memória e
não faz parte da migração das quatro frentes para DTO/JPA.

## Rotas

| Método | Rota | Resultado |
| --- | --- | --- |
| GET | /boas-vindas | 200 com texto de apresentação |
| GET | /destaque | 200 com a primeira vaga ou 404 se o catálogo estiver vazio |
| GET | /vagas | 200 com lista |
| GET | /vagas/{id} | 200 ou 404 |
| POST | /vagas | 201 com Location ou 400 |
| PUT | /vagas/{id} | 200, 400 ou 404 |
| DELETE | /vagas/{id} | 204 ou 404 |
| GET | /empresas | 200 com lista |
| GET | /empresas/{slug} | 200 ou 404 |
| POST | /empresas | 201, 400 ou 409 por slug repetido |
| PUT | /empresas/{slug} | 200, 400 ou 404 |
| DELETE | /empresas/{slug} | 204 ou 404 |
| GET | /pessoas | 200 com lista |
| GET | /pessoas/{id} | 200 ou 404 |
| POST | /pessoas | 201 ou 400 |
| PUT | /pessoas/{id} | 200, 400 ou 404 |
| DELETE | /pessoas/{id} | 204 ou 404 |
| GET | /vagas/busca?area=Dados&senioridade=Júnior | 200 ou 400 se parâmetro inválido |
| GET | /estatisticas | 200 com contagens |

Os ids de vagas e pessoas são números gerados pelo banco. Não envie id no
POST ou PUT. O slug da empresa é escolhido pelo cliente, por isso o Location
de empresa usa o slug. No PUT, o recurso é identificado pela URL.

O Spring cria os controllers durante a inicialização ao encontrar
@RestController. Ele também cria os services e injeta as dependências
pelos construtores. A conversão dos objetos para JSON é feita pelo
conversor HTTP com Jackson, configurado pelo Spring Boot.

## Como testar manualmente

Abra `requisicoes.http` no HTTP Client do IntelliJ ou com a extensão REST Client
do VS Code. Execute uma requisição de cada vez. Copie o id retornado pelo POST
para a variável indicada antes de testar o PUT e o DELETE.
Os comentários informam os status esperados.

Com a aplicação recém-iniciada, as listas têm 12 vagas, 5 empresas e 6 pessoas.
GET /estatisticas deve indicar 12 vagas e 7 que aceitam iniciantes.

Exemplo no terminal:

```bash
curl -i http://localhost:8081/pessoas

curl -i -X POST http://localhost:8081/pessoas \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Maria","email":"maria@exemplo.com","area":"Dados","senioridade":"Júnior"}'
```

Confira o 201, o id do corpo e o cabeçalho Location. Use o id devolvido:

```bash
curl -i http://localhost:8081/pessoas/7
curl -i -X DELETE http://localhost:8081/pessoas/7
curl -i http://localhost:8081/pessoas/7
```

O 7 é apenas exemplo: em uma execução com outros cadastros o id pode mudar.
O GET após excluir retorna 404.

Para testar uma entrada inválida:

```bash
curl -i -X POST http://localhost:8081/pessoas \
  -H 'Content-Type: application/json' -d '{}'
```

Deve retornar 400, Content-Type application/problem+json e campos com as
mensagens. Teste também e-mail inválido, senioridade desconhecida, slug
repetido e busca sem resultados usando o arquivo .http.

O arquivo `idempotencia.http` demonstra que repetir PUT mantém os mesmos
dados e repetir DELETE mantém o recurso ausente, mesmo que o segundo DELETE
retorne 404. Repetir POST de pessoa cria outro registro.

## Banco H2

Com a aplicação em execução, abra http://localhost:8081/h2-console e preencha:

- Driver: org.h2.Driver
- JDBC URL: jdbc:h2:mem:tech
- Usuário: sa
- Senha: deixe em branco

Execute:

```sql
SELECT * FROM vagas;
SELECT * FROM empresas;
SELECT * FROM pessoas;
```

Crie uma pessoa pela API e repita o SELECT para conferir a gravação.
O H2 está em memória: ao reiniciar, os cadastros novos desaparecem e data.sql
recarrega os exemplos. O console deve usar a mesma URL da configuração.

`ddl-auto=create-drop` cria as tabelas ao iniciar e as remove ao encerrar.
É adequado a este exercício. `update` tenta ajustar o esquema, `validate`
somente verifica e `none` não gerencia o esquema. Em produção, prefira
migrações controladas com validate/none e desative o console H2.

## Testes automatizados

```bash
./mvnw test
```

Os testes verificam a inicialização, leitura dos dados, CRUD de pessoa,
integração da criação de vaga com busca e estatísticas, validações e erros.
Não é necessário manter a aplicação aberta para executar esses testes.

## Equipe e decisões

Preencha os nomes reais antes da entrega:

| Frente | Responsável | Principal assunto |
| --- | --- | --- |
| 1 — Vaga | A preencher | CRUD, geração de id e DTO de resposta sem descrição |
| 2 — Empresa | A preencher | Slug escolhido pelo cliente e conflito 409 |
| 3 — Pessoa | A preencher | E-mail, DTO sem senha e idempotência |
| 4 — Busca e números | A preencher | Filtros opcionais e consultas ao mesmo banco |

As regras estão no [CONTRATO.md](CONTRATO.md). As rotas de pessoas permanecem
públicas e não existe senha no modelo. Relacionamentos, candidaturas,
paginação, login/JWT e Swagger pertencem às próximas aulas.

Referências:

- [Briefing](https://cdn.aulas.umlequedetecnologia.com.br/public/introducao-spring-boot/projeto/brief.html)
- [Desafio 03](https://cdn.aulas.umlequedetecnologia.com.br/public/introducao-spring-boot/aula-03-api-rest/desafio.html)
- [Desafio 04](https://cdn.aulas.umlequedetecnologia.com.br/public/introducao-spring-boot/aula-04-dtos-validacao-erros/desafio.html)
