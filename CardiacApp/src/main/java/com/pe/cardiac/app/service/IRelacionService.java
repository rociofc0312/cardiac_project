package com.pe.cardiac.app.service;

import java.util.List;

import com.pe.cardiac.app.model.Relacion;
import com.pe.cardiac.app.model.Usuario;

public interface IRelacionService {
	
	public Relacion findByID(int id);
	
	public void save(Relacion relacion);
	
	public Iterable<Relacion> findAll();
	
	public void delete(int id);
	
	public List<Relacion> findByDoctor(int id);
	
	public List<Relacion> findByPaciente(int id);

}
