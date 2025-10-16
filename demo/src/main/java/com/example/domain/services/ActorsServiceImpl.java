package com.example.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.contracts.domain.repositories.ActorsRepository;
import com.example.contracts.domain.services.ActorsService;
import com.example.core.contracts.domain.exceptions.DuplicateKeyException;
import com.example.core.contracts.domain.exceptions.InvalidDataException;
import com.example.core.contracts.domain.exceptions.NotFoundException;
import com.example.domain.entities.Actor;

@Service
public class ActorsServiceImpl implements ActorsService {
	private ActorsRepository dao;
	
	public ActorsServiceImpl(ActorsRepository dao) {
		super();
		this.dao = dao;
	}

	@Override
	public Optional<Actor> getOne(Specification<Actor> spec) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Actor> getAll(Specification<Actor> spec) {
		return dao.findAll(spec);
	}

	@Override
	public Page<Actor> getAll(Specification<Actor> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> getAll(Specification<Actor> spec, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Actor> getAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actor> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("No se ha proporcionado un actor");
		}
		if(item.isInvalid()) {
			throw new InvalidDataException("El actor proporcionado no es válido", item.getErrorsFields());
		}
		if(dao.existsById(item.getId())) {
			throw new DuplicateKeyException("Ya existe un actor con id: " + item.getId());
		}
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("No se ha proporcionado un actor");
		}
		if(item.isInvalid()) {
			throw new InvalidDataException("El actor proporcionado no es válido", item.getErrorsFields());
		}
		if(!dao.existsById(item.getId())) {
			throw new NotFoundException("El actor ya no existe.");
		}
		return dao.save(item);
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void repartoDePremios(String palmares, int año) {
		// TODO Auto-generated method stub

	}

}
