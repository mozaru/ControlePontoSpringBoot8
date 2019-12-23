package br.com.tech.ControlePonto;
import br.com.tech.ControlePonto.utils.Banco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import br.com.tech.ControlePonto.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
class ControlePontoApplicationTests {
    
	private static Banco bd=null;
	
	@Autowired
	private Environment env;

	//BeforeAll
	/*public static void setUp() {
		bd = new Banco();
		bd.criarBanco();
	}*/

	private synchronized void criarBD()
	{
		if (bd==null)
		{
			bd = new Banco();
			bd.criarBanco();
		}
	}

			
	@Test
	void criarBanco() {
		criarBD();
		Assert.isTrue(bd.executarCount("select count(*) from teste")>0, "Banco nao criado!");
	}

	@Test
	void select() {
		criarBD();
		List<Map<String,Object>> resp = bd.executarConsulta("select name from teste where name='banana'");
		Assert.isTrue(resp.size()>0 && resp.get(0).get("name").equals("banana"),"Banana nao encontrada!");
	}


	@Test
	void inserir() {
		criarBD();
		int count = bd.executarCount("select count(*) from teste");
		bd.executarUpdate("insert into teste (name) values ('laranja')");
		Assert.isTrue(count+1 == bd.executarCount("select count(*) from teste"), "Laranja nao inserida!");
	}

	@Test
	void remover() {
		criarBD();
		bd.executarUpdate("delete from teste where name='banana'");
		Assert.isTrue(0 == bd.executarCount("select count(*) from teste where name='banana'"), "Banana nao removida!");
	}
}
