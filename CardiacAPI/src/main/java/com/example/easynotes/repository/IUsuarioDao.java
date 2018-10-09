package com.example.easynotes.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.easynotes.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{
	Usuario findByDniAndContrasenia(String dni, String contrasenia);
	
	Usuario findById(int id);
}
