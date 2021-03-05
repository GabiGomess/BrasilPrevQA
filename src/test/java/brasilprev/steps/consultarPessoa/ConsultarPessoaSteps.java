package brasilprev.steps.consultarPessoa;
import brasilprev.steps.cadastrarPessoa.CadastrarPessoaSteps;
import brasilprev.utils.GeradorDeDados;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ConsultarPessoaSteps {
    Response response;
    RequestSpecification request;
    GeradorDeDados geradorDeDados = new GeradorDeDados();
    String ddd = null;
    String numero = null;

    public ConsultarPessoaSteps() throws Exception { }

    @Dado("inicio de pesquisa")
    public void inicioDePesquisa() {
        request = RestAssured.given().log().all();
    }

    @Quando("pesquisar por telefone completo cadastrado")
    public void pesquisarPorTelefoneCompletoCadastrado() {
        response = request.when().log().all()
                .get("/pessoas/" + CadastrarPessoaSteps.dddTelefones + "/" + CadastrarPessoaSteps.numeroTelefones);
    }

    @Entao("retornar pesquisa com dados corretos")
    public void retornarPesquisaComDadosCorretos() {
        response.then().log().all()
                .body("codigo", notNullValue())
                .body("nome", is(CadastrarPessoaSteps.nomeCompleto))
                .body("cpf", is(CadastrarPessoaSteps.cpf))
                .body("enderecos[0].codigo", notNullValue())
                .body("enderecos[0].logradouro", is(CadastrarPessoaSteps.logradouroEnderecos))
                .body("enderecos[0].numero", is(CadastrarPessoaSteps.numeroEnderecos))
                .body("enderecos[0].complemento", is(CadastrarPessoaSteps.complementoEnderecos))
                .body("enderecos[0].bairro", is(CadastrarPessoaSteps.bairroEnderecos))
                .body("enderecos[0].cidade", is(CadastrarPessoaSteps.cidadeEnderecos))
                .body("enderecos[0].estado", is(CadastrarPessoaSteps.estadoEnderecos))
                .body("telefones[0].codigo", notNullValue())
                .body("telefones[0].ddd", is(CadastrarPessoaSteps.dddTelefones))
                .body("telefones[0].numero", is(CadastrarPessoaSteps.numeroTelefones))
                .statusCode(HttpStatus.SC_OK); // 200
    }

    @E("pesquisar por telefone com DDD não preenchido")
    public void pesquisarPorTelefoneComDDDnaoPreenchido() {
        response = request.when().log().all()
                .get("/pessoas/" + geradorDeDados.getTelefone());
    }

    @Entao("retornar pesquisa com erro para consulta invalida")
    public void retornarPesquisaComErroParaConsultaInvalida() {
        response.then().log().all()
                .body("error", is("Not Found"))
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @E("pesquisar por telefone com NUMERO não preenchido")
    public void pesquisarPorTelefoneComNumeroNaoPreenchido() {
        response = request.when().log().all()
                .get("/pessoas/" + geradorDeDados.getDDD() + "/" + "");
    }

    @Entao("retornar pesquisa para pessoa não encontrada")
    public void retornarPesquisaParaPessoaNaoEncontrada() {
        response.then().log().all()
                .body("erro", is("Não existe pessoa com o telefone (" + ddd + ")" + numero))
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Quando("pesquisar por pessoa não cadastrada")
    public void pesquisarPorPessoaNaoCadastrada() {
        ddd = geradorDeDados.getDDD();
        numero = geradorDeDados.getTelefone();

        response = request.when().log().all()
                .get("/pessoas/" + ddd + "/" + numero );
    }
}
