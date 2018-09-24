package com.pe.cardiac.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pe.cardiac.app.model.Usuario;
import com.pe.cardiac.app.model.Wearable;

public interface IWearableDao extends CrudRepository<Wearable, Integer>{
	List<Wearable> findByUsuario(Usuario usuario);
}
