package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.ioc.AppConfig;
import com.example.ioc.NotificationService;
import com.example.ioc.Rango;
import com.example.ioc.anotaciones.Remoto;
import com.example.ioc.contratos.Configuracion;
import com.example.ioc.contratos.ServicioCadenas;
import com.example.ioc.implementaciones.ConfiguracionImpl;
import com.example.ioc.notificaciones.Sender;

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
	
//	@Bean
	CommandLineRunner contexto() {
		return arg -> {
			try (var contexto = new AnnotationConfigApplicationContext(AppConfig.class)) {
				var c1 = contexto.getBean(Configuracion.class);
				var c2 = contexto.getBean(Configuracion.class);
				System.out.println("c1 = %d".formatted(c1.getNext()));
				System.out.println("c2 = %d".formatted(c2.getNext()));
				System.out.println("c1 = %d".formatted(c1.getNext()));
				System.out.println("c2 = %d".formatted(c2.getNext()));
				System.out.println("c1 = %d".formatted(c1.getNext()));
				contexto.getBean(NotificationService.class).getListado().forEach(System.out::println);
			}
		};
	}
	
//	@Bean
	CommandLineRunner porNombre(Sender correo, Sender fichero, Sender twittea) {
		return arg -> {
			correo.send("Hola mundo");
			fichero.send("Hola mundo");
			twittea.send("Hola mundo");
		};
	}

//	@Bean
	CommandLineRunner cualificados(@Qualifier("local") Sender local, @Remoto Sender remoto, Sender primario) {
		return arg -> {
			primario.send("Hola por defecto");
			local.send("Hola local");
			remoto.send("Hola remoto");
		};
	}
	
	@Bean
	CommandLineRunner inyectaValores(@Value("${mi.valor:Sin configurar}") String valor, Rango rango) {
		return arg -> {
			System.err.println("Valor inyectado: %s".formatted(valor));
			System.err.println("Rango inyectado: %s".formatted(rango));
		};
	}

}
