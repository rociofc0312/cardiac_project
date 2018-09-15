package com.pe.cardiac.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pe.cardiac.app.dao.IRelacionDao;
import com.pe.cardiac.app.model.Relacion;
import com.pe.cardiac.app.model.Usuario;

public class RelacionServiceImpl implements IRelacionService{

	@Autowired
	private IRelacionDao relacionDao;
	
	public Relacion findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Relacion relacion) {
		// TODO Auto-generated method stub
		
	}

	public Iterable<Relacion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Relacion> findByDoctor(int id) {
		return relacionDao.findByUsuarioDoctorId(id);
	}
	
}
