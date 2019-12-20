package br.com.tech.ControlePonto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ControlePontoApplication {

	public static void main(String[] args) {
		System.out.println("oi, controle de ponto");
		SpringApplication.run(ControlePontoApplication.class, args);
	}

}
