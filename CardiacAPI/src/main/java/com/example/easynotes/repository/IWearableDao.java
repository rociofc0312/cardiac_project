package com.example.easynotes.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.easynotes.model.Usuario;
import com.example.easynotes.model.Wearable;


public interface IWearableDao extends CrudRepository<Wearable, Integer>{
	List<Wearable> findByUsuario(Usuario usuario);
}
