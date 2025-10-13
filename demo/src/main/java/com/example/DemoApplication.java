package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.ioc.NotificationService;
import com.example.ioc.contratos.Configuracion;
import com.example.ioc.contratos.ServicioCadenas;
import com.example.ioc.implementaciones.ConfiguracionImpl;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Apicacion arrancada....");
	}

	@Autowired
	NotificationService notify;
	
//	@Bean
	CommandLineRunner ioc(Configuracion config) {
		return args -> {
//			System.out.println(notify.getClass().getCanonicalName());
			notify.add("Mensaje 1");
			notify.add("Mensaje 2");
			notify.add("Mensaje 3");
			config.config();
			Configuracion c = new ConfiguracionImpl(notify);
			c.config();
			notify.getListado().forEach(System.out::println);
			System.out.println(notify.getClass().getCanonicalName());
		};
	}
	
	@Bean
	CommandLineRunner cadenaDeDependencia(ServicioCadenas srv) {
		return args -> {
			srv.get().forEach(notify::add);
			srv.add("Hola mundo");
			notify.add(srv.get(1));
			srv.modify("modificado");
			System.out.println("===================>");
			notify.getListado().forEach(System.out::println);
			notify.clear();
			System.out.println("<===================");
			
		};
	}
}
