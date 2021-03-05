package brasilprev.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        glue = "brasilprev.steps.cadastrarPessoa",
        plugin = "html:target/reports/brasilprev-cadastrarPessoa.html",
        features ="classpath:features/cadastrarPessoa.feature",
        tags = "@Teste" ,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class CadastrarPessoaTests { }