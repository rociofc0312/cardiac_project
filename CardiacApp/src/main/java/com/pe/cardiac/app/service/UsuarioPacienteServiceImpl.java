package com.pe.cardiac.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IUsuarioPacienteDao;

@Service
public class UsuarioPacienteServiceImpl implements IUsuarioPacienteService {

	@Autowired
	private IUsuarioPacienteDao usuarioPacienteDao;
}
