#language:pt
Funcionalidade: Consultar cadastro de pessoas

  @Teste
  Cenario: Deve ser possível procurar pessoa por telefone completo (DDD e numero)
    Dado que preciso cadastrar uma nova pessoa
    E inserir dados corretos para cadastro de pessoa
    E finalizar envio de informacoes
    E o cadastro deve ser efetuado com sucesso
    Quando inicio de pesquisa
    E pesquisar por telefone completo cadastrado
    Então retornar pesquisa com dados corretos

  @Teste
  Cenario: Deve retornar erro quando buscar pessoa com TELEFONE.DDD não preenchido
    Dado inicio de pesquisa
    Quando pesquisar por telefone com DDD não preenchido
    Então  retornar pesquisa com erro para consulta invalida

  @Teste
  Cenario: Deve retornar erro quando buscar pessoa com TELEFONE.NUMERO não preenchido
    Dado inicio de pesquisa
    Quando pesquisar por telefone com NUMERO não preenchido
    Então  retornar pesquisa com erro para consulta invalida

  @Teste
  Cenario: Deve retornar erro quando buscar pessoa não cadastrada
    Dado inicio de pesquisa
    Quando pesquisar por pessoa não cadastrada
    Então  retornar pesquisa para pessoa não encontrada