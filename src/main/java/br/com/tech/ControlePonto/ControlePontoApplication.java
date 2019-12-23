package br.com.tech.ControlePonto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import br.com.tech.ControlePonto.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ControlePontoApplication {

	@Autowired
	private Environment env;

	private static Logger logger = LoggerFactory.getLogger(ControlePontoApplication.class);

	public static void main(String[] args) {
		System.out.println("oi, controle de ponto");
		SpringApplication.run(ControlePontoApplication.class, args);
	}

    /*@Value("${bd.driver}")
    public void setDriver(String valor) {
        config._DRIVER_ = valor;
    }
    @Value("${bd.stringconexao}")
    public void setStringConexao(String valor) {
        config._STRING_CONEXAO_ = valor;
    }
    @Value("${bd.user}")
    public void setUser(String valor) {
        config._USER_ = valor;
    }
    @Value("${bd.password}")
    public void setPassword(String valor) {
        config._PASSWORD_ = valor;
    }*/

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			try{
			Config._DRIVER_ = env.getProperty("bd.driver");
			Config._STRING_CONEXAO_ = env.getProperty("bd.stringconexao");
			Config._USER_ = env.getProperty("bd.user");
			Config._PASSWORD_ = env.getProperty("bd.password");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			logger.info("111Profile Ativo="+env.getProperty("spring.profiles.active"));
			logger.info("Profile Banco="+env.getProperty("bd.profile"));
			logger.info("_DRIVER_="+Config._DRIVER_);
			logger.info("_STRING_CONEXAO_="+Config._STRING_CONEXAO_);
			logger.info("_USER_="+Config._USER_);
			logger.info("_PASSWORD_="+Config._PASSWORD_);

		};
	}


}
