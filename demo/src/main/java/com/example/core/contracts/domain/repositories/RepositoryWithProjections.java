package com.example.core.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.core.annotation.RestResource;

public interface RepositoryWithProjections {
	@RestResource(exported = false)
	<T> List<T> findAllBy(Class<T> tipo);
	@RestResource(exported = false)
	<T> Iterable<T> findAllBy(Sort orden, Class<T> tipo);
	@RestResource(exported = false)
	<T> Page<T> findAllBy(Pageable page, Class<T> tipo);
}
