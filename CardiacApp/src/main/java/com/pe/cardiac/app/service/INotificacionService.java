package com.pe.cardiac.app.service;

import java.util.List;

import com.pe.cardiac.app.model.Notificacion;
import com.pe.cardiac.app.model.Usuario;

public interface INotificacionService {

	public Notificacion findByID(int id);
	
	public void save(Notificacion notificacion);
	
	public Iterable<Notificacion> findAll();
	
	public void delete(int id);
	
	public List<Notificacion> findByDoctor(Usuario usuario);
	
	public List<Notificacion> findByPaciente(int id);
}
