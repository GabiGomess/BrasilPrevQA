package brasilprev.utils;
import com.github.javafaker.Faker;
import java.util.Random;

public class GeradorDeDados {
    Random gerador = new Random();
    private final Faker faker = new Faker();

    /* Geração de dados para validação de cadastro inicial */
    private int codigo = faker.number().numberBetween(1, 20);
    private String nome = faker.name().firstName().replace("'", " ");
    private String sobrenome = faker.name().lastName().replace("'", " ");
    private String nomeCompleto = getNome() + " " + getSobrenome();
    private String CPF = geraCPF();
    private String logradouro = "Rua " + faker.address().streetName();
    private int numero = faker.number().numberBetween(1, 3000);
    private final String[] complemento = {"casa", "predio", "galpão"};
    private String bairro = faker.address().streetName();
    private String cidade = faker.address().city();
    private final String[] estado = {"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB",
            "PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
    private final String[] DDD = {"11","12","13","14","15","16","17","18","19","21","22","24","27","28","31","32","33","34","35","37","38",
    		"41","42","43","44","45","46","47","48","49","51","53","54","55","61","62","63","64","65","66","67","68",
			"69","71","73","74","75","77","79","81","82","83","84","85","86","87","88","89","91","92","93","94","95",
			"96","97","98","99"};
    private String telefone = faker.number().digits(9);

    /* ------------------------------------------------------------------------------------------- */
    /* Geração de CPF */
    private static String calcDigVerif(String num) {
        Integer primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++) {
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }

        if (soma % 11 == 0 | soma % 11 == 1) {
            primDig = new Integer(0);
        } else {
            primDig = new Integer(11 - (soma % 11));
        }

        soma = 0;
        peso = 11;

        for (int i = 0; i < num.length(); i++) {
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }   soma += primDig.intValue() * 2;

        if (soma % 11 == 0 | soma % 11 == 1) {
            segDig = new Integer(0);
        } else {
            segDig = new Integer(11 - (soma % 11));
        }

        return primDig.toString() + segDig.toString();
    }

    public static String geraCPF() {
        String iniciais = "";
        Integer numero;
        for (int i = 0; i < 9; i++) {
            numero = new Integer((int) (Math.random() * 10));
            iniciais += numero.toString();
        }   return iniciais + calcDigVerif(iniciais);
    }

    public GeradorDeDados() throws Exception { }

    /* ------------------------------------------------------------------------------------------- */
    /* Construtores */
    public int getCodigo() { return codigo; }

    public String getCpf() { return CPF; }

    public String getNomeCompleto() { return nomeCompleto;	}

    public String getNome() { return nome; }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getTelefone() {
         return telefone;
    }

    public String getLogradouro() {
        return logradouro ;
    }

    public String getComplemento() {
        return complemento[gerador.nextInt(3)];
    }

    public int getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() { return cidade; }

    public String getEstado() { return estado[gerador.nextInt(27)];
    }
    public String getDDD() { return DDD[gerador.nextInt(67)];}
}
