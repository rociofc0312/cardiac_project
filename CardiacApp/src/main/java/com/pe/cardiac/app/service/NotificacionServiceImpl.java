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
	
	@Override
	public Notificacion findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Notificacion notificacion) {
		notificacionDao.save(notificacion);
	}

	@Override
	public Iterable<Notificacion> findAll() {
		return notificacionDao.findAll();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Notificacion> findByDoctor(Usuario usuario) {
		return notificacionDao.findByUsuarioDoctor(usuario);
	}

	@Override
	public List<Notificacion> findByPaciente(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
