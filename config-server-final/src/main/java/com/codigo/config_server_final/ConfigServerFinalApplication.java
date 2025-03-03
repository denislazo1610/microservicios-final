package com.codigo.config_server_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerFinalApplication.class, args);
	}

}
