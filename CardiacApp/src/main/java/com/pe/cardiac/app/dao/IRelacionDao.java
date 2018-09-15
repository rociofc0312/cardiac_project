package com.pe.cardiac.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.pe.cardiac.app.model.Relacion;

public interface IRelacionDao extends CrudRepository<Relacion, Integer>{
	Iterable<Relacion> findByUsuarioDoctorId(int id);
}
