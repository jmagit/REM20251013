package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.domains.entities.Contacto;

public interface ContactoRepository extends MongoRepository<Contacto, String> {
	List<Contacto> findByConflictivoTrue();
	<T> Page<T> searchByNombreContainsIgnoreCaseOrderByNombreAsc(String fragmento, Pageable pageable, Class<T> tipo);
	<T> List<T> searchTop20ByNombreContainsIgnoreCaseOrderByNombreAsc(String fragmento, Class<T> tipo);
	<T> List<T> findAllBy(Class<T> tipo);
}
