package com.pe.cardiac.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pe.cardiac.app.model.Relacion;

public interface IRelacionDao extends CrudRepository<Relacion, Integer>{
	List<Relacion> findByUsuarioDoctorId(int id);
	List<Relacion> findByUsuarioPacienteId(int id);
	List<Relacion> findByUsuarioTutorId(int id);
}
