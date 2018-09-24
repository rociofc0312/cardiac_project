package com.pe.cardiac.app.service;

import java.util.List;

import com.pe.cardiac.app.model.Relacion;
import com.pe.cardiac.app.model.Usuario;
import com.pe.cardiac.app.model.Wearable;

public interface IWearableService {
	public List<Wearable> findByUsuario(Usuario usuario);
}
