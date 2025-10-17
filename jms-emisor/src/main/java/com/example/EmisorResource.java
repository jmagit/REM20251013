package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmisorResource {
	@Value("${spring.application.name}:${server.port}")
	private String origen;

	@Autowired
	JmsTemplate jms;

	@GetMapping(path = "/saludo/{nombre}")
	public String saluda(@PathVariable String nombre) {
		String msg = "Hola " + nombre;
		jms.setPubSubDomain(false);
		jms.convertAndSend("saludos", new MessageDTO(msg, origen));
		return "SEND COLA: " + msg;
	}
	@GetMapping(path = "/despedida/{nombre}")
	public String despedida(@PathVariable String nombre) {
		String msg = "Adios " + nombre;
		jms.setPubSubDomain(true);
		jms.convertAndSend("despedidas", new MessageDTO(msg, origen));
		return "SEND TEMA: " + msg;
	}
	
}
