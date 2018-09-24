package com.pe.cardiac.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IUsuarioDao;
import com.pe.cardiac.app.dao.IWearableDao;
import com.pe.cardiac.app.model.Usuario;
import com.pe.cardiac.app.model.Wearable;

@Service
public class WearableServiceImpl implements IWearableService {

	@Autowired
	private IWearableDao wearableDao;
	
	public List<Wearable> findByUsuario(Usuario usuario) {
		return wearableDao.findByUsuario(usuario);
	}

}
