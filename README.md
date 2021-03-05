# Desafio QA - BRASILPREV // Gabriela Gomes
https://www.linkedin.com/in/gabriela-gomess/

## Funcionalidades

- cadastrarPessoa: baseado em respostas de solicitações enviadas ao endpoint localhost:8080/pessoas.
- consultarPessoa: baseado em respostas de solicitações enviadas ao endpoint localhost:8080/pessoas/{ddd}/{numero}

## Plugins

| Plugin | Mais informações |
| ------ | ------ |
| Cucumber | [https://plugins.jetbrains.com/plugin/7212-cucumber-for-java]|
| Gherkin | [https://plugins.jetbrains.com/plugin/9164-gherkin] |
| Java SDK | 1.8 version 1.8.0_271 |

## Dependências
| Plugin | Mais informações |
| ------ | ------ |
| Rest-Assured | [https://mvnrepository.com/artifact/io.rest-assured/rest-assured]|
| Cucumber | [https://mvnrepository.com/artifact/io.cucumber] |
| JUnit | [https://mvnrepository.com/artifact/io.cucumber/cucumber-junit] |
| JavaFaker | [https://mvnrepository.com/artifact/com.github.javafaker/javafaker] |
| OKHTTP | [https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp] |

## Problemas encontrados
| Cenário | Problema abordado |
| ------ | ------ |
| Deve ser possível salvar pessoa com campo CPF vazio |  Verificando a estrutura desta API, o campo CPF pode ser cadastrado com string vazia. Por se tratar de um campo com características únicas (não se repete em outras pessoas), este campo necessitaria de validação de legitimidade e bloqueio para cadastros irregulares. Atualmente permite passar cadastros com campo vazio (não nulo), assim gerando erro 400 - "erro": "Já existe pessoa cadastrada com o CPF" |
| Cadastro de pessoa com mais endereços | Analizando API, verificou-se que campo endererecos é tratado como tipo < Lista >, desta forma, podendo o cadastro haver mais de um endereço, com codigo.endereco diferentes. Mas está ocorrendo erro de sintaxe. Talvez problema na criação de query para este cenário. |
|Cadastro de pessoa com mais telefones| Analizando API, verificou-se que campo telefones é tratado como tipo < Lista >, desta forma, podendo o cadastro haver mais de um telefone, com codigo.telefone diferentes. Mas está ocorrendo erro de sintaxe. Talvez problema na criação de query para este cenário. |


## Melhorias a serem abordadas
* OBS: Vazio = tamanho 0, que é diferente de nulo = null;

| Cenário: | Melhoria |
| ------ | ------ |
| Deve ser possível salvar pessoa com campo NOME vazio | Necessário validação do campo NOME para bloqueio de inserções com tamanho 0 (zero). Atualmente campo permite strings vazias, desde que seu valor não seja nulo (null). Em alguma futura implementação de buscar por nome completo, esta brecha afetaria seu desenvolvimento.|
| Deve ser possível salvar pessoa com campo CPF vazio | Necessário validação do campo CPF para bloqueio de inserções de valores com quantidade de caracteres menores que 11. Talvez também a validação de legitimidade de CPF. Levando em consideração o tipo deste campo (VARCHAR 11 NOT NULL) o mesmo permite cadastros com campo vazio (tamanho = 0), retornando status 400 ("erro": "Já existe pessoa cadastrada com o CPF ") em caso de duplicidade. Em alguma futura implementação de buscar por CPF, esta brecha afetaria seu desenvolvimento. |
| Não deve ser possivel salvar pessoa com campo TELEFONES.DDD vazio | Verificando a estrutura desta API, o campo telefones.DDD pode ser cadastrado como string vazia. Por se tratar de campo utilizado como parâmetro para consulta em /pessoas/{ddd}/{numero}, ao se eleger o campo para uso vazio, afetara o resultado para a busca. Necessário validação para obrigatoriedade de inserção de 2 caracteres.
| Deve ser possivel salvar pessoa com campo TELEFONES.NUMERO vazio|Verificando a estrutura desta API, o campo telefones.numero pode ser cadastrado como string vazia. Por se tratar de campo utilizado como parâmetro para consulta em /pessoas/{ddd}/{numero}, ao se eleger o campo para uso vazio, afetara o resultado para a busca. Necessário validação para obrigatoriedade de inserção de caracteres necessários.|
