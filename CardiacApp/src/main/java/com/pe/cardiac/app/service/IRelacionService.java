package com.pe.cardiac.app.service;

import com.pe.cardiac.app.model.Relacion;
import com.pe.cardiac.app.model.Usuario;

public interface IRelacionService {
	
	public Relacion findByID(int id);
	
	public void save(Relacion relacion);
	
	public Iterable<Relacion> findAll();
	
	public Iterable<Relacion> findByDoctor(int id);

}
