package com.pe.cardiac.app.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.pe.cardiac.app.model.Usuario;
import com.pe.cardiac.app.model.Wearable;

public interface IWearableService {
	public List<Wearable> findByUsuario(Usuario usuario);
	
	List<Wearable> getAverageLastTenDays(int user_id);
	
	List<Wearable> getMedidasOfDay(String fecha, int user_id);

	List<Wearable> getAveragePerDay(int user_id);
}
