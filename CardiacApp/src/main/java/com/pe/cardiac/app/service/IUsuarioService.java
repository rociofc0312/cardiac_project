package com.pe.cardiac.app.service;

import java.util.List;

import com.pe.cardiac.app.model.Usuario;

public interface IUsuarioService {
	
	public void save(Usuario usuario);
	
	public List<Usuario> findAll();
	
	public Usuario find(String dni, String contrasenia);

}
