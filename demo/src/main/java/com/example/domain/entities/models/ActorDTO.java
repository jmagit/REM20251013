package com.example.domain.entities.models;

import org.springframework.data.rest.core.config.Projection;

import com.example.domain.entities.Actor;

import lombok.Data;

@Data
public class ActorDTO {
	private int id;
	private String nombre;
	private String apellidos;
	
	public ActorDTO(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.nombre = firstName;
		this.apellidos = lastName;
	}
		
	public static ActorDTO from(Actor source) {
		return new ActorDTO(source.getId(), source.getFirstName(), source.getLastName());
	}
	public static Actor from(ActorDTO source) {
		return new Actor(source.getId(), source.getNombre(), source.getApellidos());
	}
}
