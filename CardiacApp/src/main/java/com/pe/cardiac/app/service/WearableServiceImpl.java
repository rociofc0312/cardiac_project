package com.pe.cardiac.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.cardiac.app.dao.IWearableDao;
import com.pe.cardiac.app.model.Result;
import com.pe.cardiac.app.model.Usuario;
import com.pe.cardiac.app.model.Wearable;

@Service
public class WearableServiceImpl implements IWearableService {

	@Autowired
	private IWearableDao wearableDao;
	
	public List<Wearable> findByUsuario(Usuario usuario) {
		return wearableDao.findByUsuario(usuario);
	}

	public List<Wearable> getAverageLastTenDays(int user_id) {
		return wearableDao.getAverageLastTenDays(user_id);
	}

	public List<Wearable> getMedidasOfDay(String fecha, int user_id) {
		return wearableDao.getMedidasOfDay(fecha, user_id);
	}

	public List<Wearable> getAveragePerDay(int user_id) {
		return wearableDao.getAveragePerDay(user_id);
	}

}
