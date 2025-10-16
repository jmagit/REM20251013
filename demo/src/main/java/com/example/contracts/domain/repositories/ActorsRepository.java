package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.core.contracts.domain.exceptions.DuplicateKeyException;
import com.example.core.contracts.domain.exceptions.NotFoundException;
import com.example.core.contracts.domain.repositories.RepositoryWithProjections;
import com.example.domain.entities.Actor;
import com.example.domain.entities.models.ActorDTO;
import com.example.domain.entities.models.ActorShort;
//ProjectionsAndSpecificationJpaRepository<Actor, Integer> 
@RepositoryRestResource(path="actores", itemResourceRel="actor", collectionResourceRel="actores", excerptProjection = ActorShort.class)
public interface ActorsRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {
	@RestResource(path = "por-nombre")
	List<Actor> findTop5ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWithIgnoreCase(String prefijo, Sort orderBy);

	List<Actor> findByIdGreaterThanEqual(int idMin);
	@Query(value = "FROM Actor a WHERE a.id >= ?1")
	List<Actor> findNovedadesJPQL(int idMin);
	@Query(value = "SELECT * FROM actor a WHERE a.actor_id >= :idMin", nativeQuery = true)
	List<Actor> findNovedadesSQL(int idMin);

	List<ActorDTO> queryByIdGreaterThanEqual(int idMin);
	List<ActorShort> getByIdGreaterThanEqual(int idMin);
	@RestResource(exported = false)
	<T> List<T> searchByIdGreaterThanEqual(int idMin, Class<T> type);
	
	default Actor insert(Actor actor) {
		if(existsById(actor.getId())) {
			throw new DuplicateKeyException("El actor con id " + actor.getId() + " ya existe");
		}
		return save(actor);
	}
	
	default Actor update(Actor actor) throws NotFoundException {
		if(!existsById(actor.getId())) {
			throw new NotFoundException("El actor con id " + actor.getId() + " ya existe");
		}
		return save(actor);
	}

}
