package brasilprev.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        glue = {"brasilprev.steps.cadastrarPessoa","brasilprev.steps.consultarPessoa"},
        plugin = "html:target/reports/brasilprev-consultarPessoa.html",
        features ="classpath:features/consultarPessoa.feature",
        tags = "@Teste" ,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ConsultarPessoaTests {
}
