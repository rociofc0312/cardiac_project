package com.pe.cardiac.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pe.cardiac.app.model.Usuario;
import com.pe.cardiac.app.model.Wearable;

public interface IWearableDao extends CrudRepository<Wearable, Integer>{
	List<Wearable> findByUsuario(Usuario usuario);
	
	@Query(value="{call getAverageLastTendays(:user_id)}", nativeQuery = true)
	List<Wearable> getAverageLastTenDays( @Param("user_id") int user_id);
	
	@Query(value="{call getMedidasOfDay(:fecha, :user_id)}", nativeQuery = true)
	List<Wearable> getMedidasOfDay( @Param("fecha") String fecha, @Param("user_id") int user_id);	

	@Query(value="{call getAveragePerDay(:user_id)}", nativeQuery = true)
	List<Wearable> getAveragePerDay(@Param("user_id") int user_id);
	
}
