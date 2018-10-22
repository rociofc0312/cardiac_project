package com.pe.cardiac.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.INotificacionDao;
import com.pe.cardiac.app.model.Notificacion;
import com.pe.cardiac.app.model.Usuario;

@Service
public class NotificacionServiceImpl implements INotificacionService{

	@Autowired
	private INotificacionDao notificacionDao;
	
	public Notificacion findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Notificacion notificacion) {
		notificacionDao.save(notificacion);
	}
	public Iterable<Notificacion> findAll() {
		return notificacionDao.findAll();
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	public List<Notificacion> findByDoctor(Usuario usuario) {
		return notificacionDao.findByUsuarioDoctor(usuario);
	}

	public List<Notificacion> findByPaciente(int id) {
		return null;
	}

}
