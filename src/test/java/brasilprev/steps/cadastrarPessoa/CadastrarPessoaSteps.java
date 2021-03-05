package brasilprev.steps.cadastrarPessoa;
import brasilprev.utils.GeradorDeDados;
import com.github.javafaker.Faker;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.*;

public class CadastrarPessoaSteps {
    Response response;
    RequestSpecification request;
    GeradorDeDados geradorDeDados;
    Faker faker = new Faker();
    public static String nomeCompleto = null;
    public static String cpf = null;
    public static String logradouroEnderecos = null;
    public static int numeroEnderecos = 0;
    public static String complementoEnderecos = null;
    public static String bairroEnderecos = null;
    public static String cidadeEnderecos = null;
    public static String estadoEnderecos = null;
    public static String dddTelefones = null;
    public static String numeroTelefones = null;

    @Dado("^que preciso cadastrar uma nova pessoa$")
    public void quePrecisoCadastrarUmaNovaPessoa() {
        request = RestAssured.given()
                .log()
                .all()
                .accept("application/json")
                .contentType(ContentType.JSON);
    }

    @Quando("^inserir dados corretos para cadastro de pessoa$")
    public void inserirDadosCorretosParaCadastroDePessoa() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("^finalizar envio de informacoes$")
    public void finalizarEnvioDeInformacoes() {
        response = request.when().log().all()
                .post("/pessoas");
    }

    @Entao("^o cadastro deve ser efetuado com sucesso$")
    public void oCadastroDeveSerEfetuadoComSucesso() {
        response.then().log().all()
                .body("codigo", notNullValue())
                .body("nome", is(nomeCompleto))
                .body("cpf", is(cpf))
                .body("enderecos[0].codigo", notNullValue())
                .body("enderecos[0].logradouro", is(logradouroEnderecos))
                .body("enderecos[0].numero", is(response.path("enderecos[0].numero")))
                .body("enderecos[0].complemento", is(complementoEnderecos))
                .body("enderecos[0].bairro", is(bairroEnderecos))
                .body("enderecos[0].cidade", is(cidadeEnderecos))
                .body("enderecos[0].estado", is(estadoEnderecos))
                .body("telefones[0].codigo", notNullValue())
                .body("telefones[0].ddd", is(dddTelefones))
                .body("telefones[0].numero", is(numeroTelefones))
                .statusCode(HttpStatus.SC_CREATED); // 201);
    }

    @Entao("o cadastro não deve ser efetuado por erro em sintaxe SQL")
    public void oCadastroNaoDeveSerEfetuadoPorErroEmSintaxe() {
        response.then().log().all()
                .body("error", is("Internal Server Error"))
                .body("message", containsString("could not execute statement"))
                .body("path", containsString("/pessoas"))
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR); // 500
    }

    @Entao("o cadastro não deve ser efetuado")
    public void oCadastroNaoDeveSerEfetuado() {
        response.then().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Quando("inserir dados para campo CODIGO em cadastro")
    public void inserirDadosParaCampoCODIGOEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();
        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"codigo\": " + geradorDeDados.getCodigo() + ",\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado nulo para campo CODIGO em cadastro")
    public void inserirDadosParaCampoCODIGONuloEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();
        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"codigo\": " + null + ",\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado vazio para campo NOME em cadastro")
    public void inserirDadoVazioParaCampoNOMEEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                "",
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
        }

    @Quando("inserir dado nulo para campo NOME em cadastro")
    public void inserirDadoNuloParaCampoNOMEEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        request.body(
                "{\n" +
                "  \"nome\": " + null + ",\n" +
                "  \"cpf\": \"" + geradorDeDados.getCpf() + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + geradorDeDados.getLogradouro() + "\",\n" +
                "      \"numero\": " + geradorDeDados.getNumero() + ",\n" +
                "      \"complemento\": \"" + geradorDeDados.getComplemento() + "\",\n" +
                "      \"bairro\": \"" + geradorDeDados.getBairro() + "\",\n" +
                "      \"cidade\": \"" + geradorDeDados.getCidade() + "\",\n" +
                "      \"estado\": \"" + geradorDeDados.getEstado() + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + geradorDeDados.getDDD() + "\",\n" +
                "      \"numero\": \"" + geradorDeDados.getTelefone() + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado vazio para campo CPF em cadastro")
    public void inserirDadoVazioParaCampoCPFEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                "",
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado maior que {int} caracterses para campo CPF em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoCPF(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                faker.lorem().characters(aux + 1),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado maior que {int} caracteres para campo LOGRADOURO em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoLogradouro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                faker.lorem().characters(aux + 1),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado vazio para campo LOGRADOURO em cadastro")
    public void naoInserirDadosParaCampoLOGRADOUROEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                "",
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado vazio para campo COMPLEMENTO em cadastro")
    public void naoInserirDadosParaCampoCOMPLEMENTOEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                "",
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado vazio para campo BAIRRO em cadastro")
    public void naoInserirDadosParaCampoBAIRROEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                "",
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado vazio para campo CIDADE em cadastro")
    public void naoInserirDadosParaCampoCIDADEEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                "",
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());

    }

    @Quando("inserir dado vazio para campo ESTADO em cadastro")
    public void naoInserirDadosParaCampoESTADOEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                "",
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado vazio para campo DDD em cadastro")
    public void naoInserirDadosParaCampoDDDEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                "",
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado vazio para campo TELEFONE em cadastro")
    public void naoInserirDadosParaCampoTELEFONEEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                "");
    }

    @E("inserir dados corretos para cadastro de pessoa com mesmo CPF")
    public void inserirDadosCorretosParaCadastroDePessoaComMesmoCPF() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                cpf,
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Entao("o cadastro não deve ser efetuado para CPF já cadastrado")
    public void oCadastroNaoDeveSerEfetuadoParaCPFJaCadastrado() {
        response.then().log().all()
                .body("erro", containsString("Já existe pessoa cadastrada com o CPF " + cpf))
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @E("^inserir dados corretos para cadastro de pessoa com mesmo Telefone completo$")
    public void inserirDadosCorretosParaCadastroDePessoaComMesmoTelefoneCompletoDDDNumero() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                dddTelefones,
                numeroTelefones);
    }

    @E("^inserir dados corretos para cadastro de pessoa com mesmo DDD e telefone diferente$")
    public void inserirDadosCorretosParaCadastroDePessoaComMesmoDDDeTelefoneDiferente() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                dddTelefones,
                geradorDeDados.getTelefone());
    }

    @E("^inserir dados corretos para cadastro de pessoa com mesmo telefone e ddd diferente$")
    public void inserirDadosCorretosParaCadastroDePessoaComMesmoTelefoneEDDDDiferente() throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                numeroTelefones);
    }

    @Entao("o cadastro não deve ser efetuado para Telefone completo já cadastrado")
    public void oCadastroNaoDeveSerEfetuadoParaTelefoneCompletoJaCadastrado() {
        response.then().log().all()
                .body("erro", containsString("Já existe pessoa cadastrada com o Telefone (" + dddTelefones + ")" + numeroTelefones))
                .statusCode(HttpStatus.SC_BAD_REQUEST);

    }

    @Quando("inserir dados corretos para cadastro de pessoa com mais enderecos")
    public void inserirDadosCorretosParaCadastroDePessoaComMaisEnderecos() throws Exception {
        geradorDeDados = new GeradorDeDados();
        String logradouroEndereco1 = geradorDeDados.getLogradouro();
        int numeroEndereco1 = geradorDeDados.getNumero();
        String complementoEndereco1 = geradorDeDados.getComplemento();
        String bairroEndereco1 = geradorDeDados.getBairro();
        String cidadeEndereco1 = geradorDeDados.getCidade();
        String estadoEndereco1 = geradorDeDados.getEstado();

        geradorDeDados = new GeradorDeDados();
        String logradouroEndereco2 = geradorDeDados.getLogradouro();
        int numeroEndereco2 = geradorDeDados.getNumero();
        String complementoEndereco2 = geradorDeDados.getComplemento();
        String bairroEndereco2 = geradorDeDados.getBairro();
        String cidadeEndereco2 = geradorDeDados.getCidade();
        String estadoEndereco2 = geradorDeDados.getEstado();

        request.body(
                "{\n" +
                "  \"nome\": \"" + geradorDeDados.getNomeCompleto() + "\",\n" +
                "  \"cpf\": \"" + geradorDeDados.getCpf() + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEndereco1 + "\",\n" +
                "      \"numero\": " + numeroEndereco1 + ",\n" +
                "      \"complemento\": \"" + complementoEndereco1 + "\",\n" +
                "      \"bairro\": \"" + bairroEndereco1 + "\",\n" +
                "      \"cidade\": \"" + cidadeEndereco1 + "\",\n" +
                "      \"estado\": \"" + estadoEndereco1 + "\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEndereco2 + "\",\n" +
                "      \"numero\": " + numeroEndereco2 + ",\n" +
                "      \"complemento\": \"" + complementoEndereco2 + "\",\n" +
                "      \"bairro\": \"" + bairroEndereco2 + "\",\n" +
                "      \"cidade\": \"" + cidadeEndereco2 + "\",\n" +
                "      \"estado\": \"" + estadoEndereco2 + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + geradorDeDados.getDDD() + "\",\n" +
                "      \"numero\": \"" + geradorDeDados.getTelefone() + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("^inserir dados corretos para cadastro de pessoa com mais telefones$")
    public void inserirDadosCorretosParaCadastroDePessoaComMaisTelefones() throws Exception {
        geradorDeDados = new GeradorDeDados();
        String dddTelefones1 = geradorDeDados.getDDD();
        String numeroTelefones1 = geradorDeDados.getTelefone();

        geradorDeDados = new GeradorDeDados();
        String dddTelefones2 = geradorDeDados.getDDD();
        String numeroTelefones2 = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + geradorDeDados.getNomeCompleto() + "\",\n" +
                "  \"cpf\": \"" + geradorDeDados.getCpf() + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + geradorDeDados.getLogradouro() + "\",\n" +
                "      \"numero\": " + geradorDeDados.getNumero() + ",\n" +
                "      \"complemento\": \"" + geradorDeDados.getComplemento() + "\",\n" +
                "      \"bairro\": \"" + geradorDeDados.getBairro() + "\",\n" +
                "      \"cidade\": \"" + geradorDeDados.getCidade() + "\",\n" +
                "      \"estado\": \"" + geradorDeDados.getEstado() + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones1 + "\",\n" +
                "      \"numero\": \"" + numeroTelefones1 + "\"\n" +
                "    },\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones2 + "\",\n" +
                "      \"numero\": \"" + numeroTelefones2 + "\"\n" +
                "    }\n" +
                 "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado maior que {int} caracteres para campo NOME em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoNOMEEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                faker.lorem().characters(aux + 1),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado nulo para campo CPF em cadastro")
    public void inserirDadoNuloParaCampoCPFEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        request.body(
                "{\n" +
                "  \"nome\": \"" + geradorDeDados.getNomeCompleto() + "\",\n" +
                "  \"cpf\": " + null + ",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + geradorDeDados.getLogradouro() + "\",\n" +
                "      \"numero\": " + geradorDeDados.getNumero() + ",\n" +
                "      \"complemento\": \"" + geradorDeDados.getComplemento() + "\",\n" +
                "      \"bairro\": \"" + geradorDeDados.getBairro() + "\",\n" +
                "      \"cidade\": \"" + geradorDeDados.getCidade() + "\",\n" +
                "      \"estado\": \"" + geradorDeDados.getEstado() + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + geradorDeDados.getDDD() + "\",\n" +
                "      \"numero\": \"" + geradorDeDados.getTelefone() + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado nulo para campo LOGRADOURO em cadastro")
    public void inserirDadoNuloParaCampoLogradouroEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = null;
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": " + cpf + ",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": " + logradouroEnderecos + ",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado nulo para campo NUMERO em cadastro")
    public void inserirDadoNuloParaCampoNUMEROEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": " + cpf + ",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + null + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dados corretos sem uso de tag endereco.numero")
    public void inserirDadosCorretosSemUsoDeTagEnderecoNumero() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado nulo para campo COMPLEMENTO em cadastro")
    public void inserirDadoNuloParaCampoCOMPLEMENTOEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = null;
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": " + cpf + ",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": " + complementoEnderecos + ",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado maior que {int} caracteres para campo COMPLEMENTO em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoCOMPLEMENTOEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                faker.lorem().characters(aux + 1),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado nulo para campo BAIRRO em cadastro")
    public void inserirDadoNuloParaCampoBAIRROEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = null;
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": " + bairroEnderecos + ",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado maior que {int} caracteres para campo BAIRRO em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoBAIRROEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                faker.lorem().characters(aux + 1),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado nulo para campo CIDADE em cadastro")
    public void inserirDadoNuloParaCampoCIDADEEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = null;
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": " + cidadeEnderecos + ",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado maior que {int} caracteres para campo CIDADE em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoCidadeEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                faker.lorem().characters(aux + 1),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado nulo para campo ESTADO em cadastro")
    public void inserirDadoNuloParaCampoEstadoEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = null;
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": " + estadoEnderecos + "\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }


    @Quando("inserir dado nulo para campo DDD em cadastro")
    public void inserirDadoNuloParaCampoDDDEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = null;
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": " + dddTelefones + ",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado nulo para campo TELEFONE em cadastro")
    public void inserirDadoNuloParaCampoTelefoneEmCadastro() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = null;

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": " + null + "\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dado maior que {int} caracteres para campo ESTADO em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoESTADOEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                faker.lorem().characters(aux + 1).toUpperCase(),
                geradorDeDados.getDDD(),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado maior que {int} caracteres para campo DDD em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoDDDEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                faker.number().digits(aux+1),
                geradorDeDados.getTelefone());
    }

    @Quando("inserir dado maior que {int} caracteres para campo TELEFONE em cadastro")
    public void inserirDadoMaiorQueTotalCaracteresParaCampoTELEFONEEmCadastro(int aux) throws Exception {
        geradorDeDados = new GeradorDeDados();

        preencherCadastroPessoa(
                geradorDeDados.getNomeCompleto(),
                geradorDeDados.getCpf(),
                geradorDeDados.getLogradouro(),
                geradorDeDados.getNumero(),
                geradorDeDados.getComplemento(),
                geradorDeDados.getBairro(),
                geradorDeDados.getCidade(),
                geradorDeDados.getEstado(),
                geradorDeDados.getDDD(),
                faker.number().digits(aux+1));
    }

    public void preencherCadastroPessoa(String nomeParameter,
                                        String cpfParameter,
                                        String logradouroEnderecoParameter,
                                        int numeroEnderecoParameter,
                                        String complementoEnderecoParameter,
                                        String bairroEnderecoParameter,
                                        String cidadeEnderecoParameter,
                                        String estadoEnderecoParameter,
                                        String dddTelefonesParameter,
                                        String numeroTelefonesParameter ) {

        nomeCompleto = nomeParameter;
        cpf = cpfParameter;
        logradouroEnderecos = logradouroEnderecoParameter;
        numeroEnderecos = numeroEnderecoParameter;
        complementoEnderecos = complementoEnderecoParameter;
        bairroEnderecos = bairroEnderecoParameter;
        cidadeEnderecos = cidadeEnderecoParameter;
        estadoEnderecos = estadoEnderecoParameter;
        dddTelefones = dddTelefonesParameter;
        numeroTelefones = numeroTelefonesParameter;

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dados sem NOME em corpo de requisicao")
    public void inserirDadosSemNOMEEmCorpoDeRequisicao() throws Exception {
        geradorDeDados = new GeradorDeDados();

        cpf = geradorDeDados.getCpf();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"cpf\": \"" + cpf + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }

    @Quando("inserir dados sem CPF em corpo de requisicao")
    public void inserirDadosSemCPFEmCorpoDeRequisicao() throws Exception {
        geradorDeDados = new GeradorDeDados();

        nomeCompleto = geradorDeDados.getNomeCompleto();
        logradouroEnderecos = geradorDeDados.getLogradouro();
        numeroEnderecos = geradorDeDados.getNumero();
        complementoEnderecos = geradorDeDados.getComplemento();
        bairroEnderecos = geradorDeDados.getBairro();
        cidadeEnderecos = geradorDeDados.getCidade();
        estadoEnderecos = geradorDeDados.getEstado();
        dddTelefones = geradorDeDados.getDDD();
        numeroTelefones = geradorDeDados.getTelefone();

        request.body(
                "{\n" +
                "  \"nome\": \"" + nomeCompleto + "\",\n" +
                "  \"enderecos\": [\n" +
                "    {\n" +
                "      \"logradouro\": \"" + logradouroEnderecos + "\",\n" +
                "      \"numero\": " + numeroEnderecos + ",\n" +
                "      \"complemento\": \"" + complementoEnderecos + "\",\n" +
                "      \"bairro\": \"" + bairroEnderecos + "\",\n" +
                "      \"cidade\": \"" + cidadeEnderecos + "\",\n" +
                "      \"estado\": \"" + estadoEnderecos + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"telefones\": [\n" +
                "    {\n" +
                "       \"ddd\": \"" + dddTelefones + "\",\n" +
                "      \"numero\": \"" + numeroTelefones + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").log().all();
    }
}

