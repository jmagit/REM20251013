package com.example.domain.entities.models;

import java.sql.Timestamp;

import org.springframework.data.rest.core.config.Projection;

import com.example.domain.entities.Actor;

import lombok.Data;

@Projection(name = "actor-dto", types = { Actor.class })
public interface ActorEdit {
	int getId();
	String getFirstName();
	String getLastName();
	Timestamp getLastUpdate();
}
