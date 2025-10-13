package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.ioc.NotificationService;

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
	
	@Bean
	CommandLineRunner ioc() {
		return args -> {
			notify.add("Mensaje 1");
			notify.add("Mensaje 2");
			notify.add("Mensaje 3");
			notify.getListado().forEach(System.out::println);
			System.out.println(notify.getClass().getCanonicalName());
		};
	}
}
