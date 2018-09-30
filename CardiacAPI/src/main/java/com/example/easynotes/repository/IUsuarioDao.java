package com.example.easynotes.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.easynotes.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{
}
