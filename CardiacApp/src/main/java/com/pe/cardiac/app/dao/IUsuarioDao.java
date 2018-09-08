package com.pe.cardiac.app.dao;

import org.mockito.internal.matchers.Find;
import org.springframework.data.repository.CrudRepository;

import com.pe.cardiac.app.model.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer>{
}
