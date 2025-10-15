package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.entities.Actor;

public interface ActorsRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findTop5ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc(String prefijo);
	List<Actor> findTop5ByFirstNameStartingWithIgnoreCase(String prefijo, Sort orderBy);
	
	List<Actor> findByIdGreaterThanEqual(int idMin);
	@Query(value = "FROM Actor a WHERE a.id >= ?1")
	List<Actor> findNovedadesJPQL(int idMin);
	@Query(value = "SELECT * FROM actor a WHERE a.actor_id >= :idMin", nativeQuery = true)
	List<Actor> findNovedadesSQL(int idMin);
	
}
