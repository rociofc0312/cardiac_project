package com.pe.cardiac.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pe.cardiac.app.model.Relacion;
import com.pe.cardiac.app.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{
	List<Usuario> findByRol(String rol);
}
