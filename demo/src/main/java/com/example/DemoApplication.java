package com.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.ioc.AppConfig;
import com.example.ioc.Dummy;
import com.example.ioc.GenericoEvent;
import com.example.ioc.NotificationService;
import com.example.ioc.Rango;
import com.example.ioc.anotaciones.Remoto;
import com.example.ioc.contratos.Configuracion;
import com.example.ioc.contratos.Servicio;
import com.example.ioc.contratos.ServicioCadenas;
import com.example.ioc.implementaciones.ConfiguracionImpl;
import com.example.ioc.notificaciones.Sender;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
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
	
//	@Bean
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
	
//	@Bean
	CommandLineRunner cualificados(List<Sender> senders, Map<String, Sender> mapa, List<Servicio> servicios) {
		return arg -> {
			senders.forEach(s -> s.send(s.getClass().getCanonicalName()));
			mapa.forEach((k,v) -> System.out.println("%s -> %s".formatted(k, v.getClass().getCanonicalName())));
			servicios.forEach(s -> System.out.println(s.getClass().getCanonicalName()));
		};
	}
	
//	@Bean
	CommandLineRunner inyectaValores(@Value("${mi.valor:Sin configurar}") String valor, Rango rango) {
		return arg -> {
			System.err.println("Valor inyectado: %s".formatted(valor));
			System.err.println("Rango inyectado: %s".formatted(rango));
		};
	}

//	@Bean
	CommandLineRunner asincrono(Dummy dummy) {
		return arg -> {
			var obj = new Dummy();
			System.err.println(obj.getClass().getCanonicalName());
			obj.ejecutarTareaSimple(1);
			obj.ejecutarTareaSimple(2);
			obj.calcularResultado(10,20,30,40,50).thenAccept (result -> notify.add(result));
			obj.calcularResultado(1,2,3).thenAccept (result -> notify.add(result));
			obj.calcularResultado().thenAccept (result -> notify.add(result));
		};
	}

//	@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
	void progamado() {
		if(! notify.hasMessages()) {
			System.out.println("Han pasado 5 segundos sin mensajes.");
			return;
		}
		System.err.println(" Mensajes pendientes ---------------------------------->");
		notify.getListado().forEach(System.out::println);
		notify.clear();
		System.err.println("<-------------------------------------------------------");

	}

//	@EventListener
	void receptor(String ev) {
		System.out.println("----> Evento inteceptado en DemoApplication -> " + ev);
	}
	
//	@EventListener
	void receptor(GenericoEvent ev) {
		System.err.println("Evento -> Origen: %s Carga: %s".formatted(ev.origen(), ev.carga()));
	}

}
