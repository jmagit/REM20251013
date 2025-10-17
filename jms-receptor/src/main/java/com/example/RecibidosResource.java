package com.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecibidosResource {
	
	private static final Logger log = LoggerFactory.getLogger(RecibidosResource.class);

	// -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*
	@JmsListener(destination = "saludos"/*, containerFactory = "myFactory"*/)
	public void listenQueue(MessageDTO in) {
		Store.addQueue(new Message(in));
		log.warn("MENSAJE RECIBIDO: " + in);
	}

	@GetMapping("/saludos")
	public List<Message> getQueue() {
		return Store.getQueue();
	}

	@JmsListener(destination = "despedidas", containerFactory = "myPubSubFactory")
	public void listen(MessageDTO in) {
		Store.addTopic(new Message(in));
		log.warn("TEMA RECIBIDO: " + in);
	}
	
	@GetMapping("/despedidas")
	public List<Message> getTopic() {
		return Store.getTopic();
	}

}
