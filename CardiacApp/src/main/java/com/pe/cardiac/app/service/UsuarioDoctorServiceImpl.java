package com.pe.cardiac.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IUsuarioDoctorDao;

@Service
public class UsuarioDoctorServiceImpl implements IUsuarioDoctorService {
	
	@Autowired
	private IUsuarioDoctorDao usuarioDoctorDao;

}
