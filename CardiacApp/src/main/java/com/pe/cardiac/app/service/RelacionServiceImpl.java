package com.pe.cardiac.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IRelacionDao;
import com.pe.cardiac.app.model.Relacion;
import com.pe.cardiac.app.model.Usuario;

@Service
public class RelacionServiceImpl implements IRelacionService{

	@Autowired
	private IRelacionDao relacionDao;
	
	public Relacion findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Relacion relacion) {
		// TODO Auto-generated method stub
		relacionDao.save(relacion);
	}

	public Iterable<Relacion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Relacion> findByDoctor(int id) {
		return relacionDao.findByUsuarioDoctorId(id);
	}

	public List<Relacion> findByPaciente(int id) {
		return relacionDao.findByUsuarioPacienteId(id);
	}

	public void delete(int id) {
		relacionDao.deleteById(id);
	}
	
}
