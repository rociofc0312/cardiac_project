package com.pe.cardiac.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IUsuarioDao;
import com.pe.cardiac.app.model.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private IUsuarioDao usuarioDao;

	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	public Usuario find(String dni, String contrasenia) {
		List<Usuario> users = findAll();
		for (Usuario oneuser : users) {
			if (oneuser.getDni().equalsIgnoreCase(dni) && oneuser.getContrasenia().equalsIgnoreCase(contrasenia)) {
				return oneuser;
			}
		}
		return null;
	}

	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

}
