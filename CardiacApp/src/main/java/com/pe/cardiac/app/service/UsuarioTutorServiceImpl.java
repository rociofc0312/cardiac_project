package com.pe.cardiac.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IUsuarioTutorDao;

@Service
public class UsuarioTutorServiceImpl implements IUsuarioTutorService {
	
	@Autowired
	private IUsuarioTutorDao usuarioTutorDao;

}
