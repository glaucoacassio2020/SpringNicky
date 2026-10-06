# Contrato da API

Escopo: vagas, empresas, pessoas e busca, até o conteúdo informado da aula 05.
POST e PUT usam os mesmos campos. O PUT substitui os dados; campos opcionais
omitidos ficam nulos. Nenhuma entrada recebe id. Os ids são números gerados
pelo H2. Campos desconhecidos não fazem parte do contrato e não são persistidos.

## Vaga

| Campo | Obrigatório | Regra |
| --- | --- | --- |
| titulo | Sim | Texto não vazio, até 120 caracteres |
| descricao | Não | Até 2000 caracteres |
| area | Sim | Front-end, Back-end, Dados ou Mobile |
| senioridade | Sim | Estágio, Júnior, Pleno ou Sênior |
| local | Não | Até 120 caracteres |
| aceitaIniciante | Sim | true ou false; usa Boolean na entrada para detectar ausência |
| empresaSlug | Sim | Até 100 caracteres; letras minúsculas, números e hífen |

A resposta contém id e todos os campos acima, exceto descricao.
A descrição continua no banco. empresaSlug ainda é texto: o relacionamento
entre entidades e a verificação de empresa existente ficam para a aula 06.

## Empresa

| Campo | Obrigatório | Regra |
| --- | --- | --- |
| nome | Sim | Texto não vazio, até 120 caracteres |
| slug | Sim | Até 100 caracteres; letras minúsculas, números e hífen; único |
| site | Não | URL válida, até 255 caracteres; pode ser omitido |
| descricao | Não | Até 2000 caracteres |

A resposta acrescenta id. As rotas usam slug.
Slug repetido no POST retorna 409 porque conflita com uma empresa existente.
No PUT, o slug do corpo deve coincidir com o da URL; divergência retorna 400.

## Pessoa

| Campo | Obrigatório | Regra |
| --- | --- | --- |
| nome | Sim | Texto não vazio, até 120 caracteres |
| email | Sim | Formato de e-mail válido, até 254 caracteres |
| area | Sim | Front-end, Back-end, Dados ou Mobile |
| senioridade | Sim | Estágio, Júnior, Pleno ou Sênior |

A resposta acrescenta id. Não há campo de senha nem autenticação.
@NotBlank exige presença; @Email verifica formato e sozinho permite null.
Os exemplos maria@, @exemplo.com e maria exemplo.com devem retornar 400.
Um endereço bem formado não prova que a caixa de e-mail existe.

Nesta etapa, e-mail repetido é permitido: dois POSTs criam duas pessoas.
Quando o e-mail passar a identificar uma conta, a decisão será torná-lo único
no banco e responder 409 nos conflitos.

## Busca e erros

GET /vagas/busca aceita area (até 40 caracteres), senioridade (até 20)
e aceitaIniciante (true/false). Todos os filtros são opcionais e combináveis.
Os filtros de texto ignoram maiúsculas/minúsculas. Termos desconhecidos
retornam lista vazia com 200. Um parâmetro longo demais retorna 400.

Erros tratados usam application/problem+json com status, detail e campos.
campos relaciona os campos inválidos às mensagens; fica vazio quando o erro
não corresponde a um campo específico, por exemplo um recurso inexistente.

## Decisões desta etapa

Mantemos área e senioridade como texto para preservar os valores dos exemplos.
O conjunto aceito é limitado por validação; SenioridadeValida demonstra uma
anotação customizada. Não usamos Lombok, mapeadores automáticos ou classes base
para o CRUD. Cada recurso tem suas próprias classes.
