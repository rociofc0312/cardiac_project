package com.example.easynotes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.easynotes.model.Relacion;

public interface IRelacionDao extends CrudRepository<Relacion, Integer>{
	List<Relacion> findByUsuarioDoctorId(int id);
	List<Relacion> findByUsuarioPacienteId(int id);
}
