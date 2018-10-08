package com.pe.cardiac.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pe.cardiac.app.model.Notificacion;
import com.pe.cardiac.app.model.Usuario;

public interface INotificacionDao extends CrudRepository<Notificacion, Integer>{
	List<Notificacion> findByUsuarioDoctor(Usuario usuario);
}
