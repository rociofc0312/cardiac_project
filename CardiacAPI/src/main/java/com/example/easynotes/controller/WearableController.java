package com.example.easynotes.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.model.Notificacion;
import com.example.easynotes.model.Usuario;
import com.example.easynotes.model.Wearable;
import com.example.easynotes.repository.INotificacionDao;
import com.example.easynotes.repository.IUsuarioDao;
import com.example.easynotes.repository.IWearableDao;

@RestController
@RequestMapping("/api")
public class WearableController {
		
		@Autowired
		IWearableDao wearableDao;
		@Autowired
		IUsuarioDao usuarioDao;
		@Autowired
		INotificacionDao notificacionDao;
		 @GetMapping
		    public String sayHello() {
		        return "Hello and Welcome to the EasyNotes application. You can create a new Note by making a POST request to /api/notes endpoint.";
		    }
		
		@PostMapping("/medidas")
		public Wearable crearMedida(@Valid @RequestBody Wearable wearable) throws ParseException {
			//Usuario usuario = new Usuario();
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			wearable.setFecha(sdfDate.parse(strDate));
			//usuario = usuarioDao.findById(wearable.getUsuario().getId()).get(); 
			//wearable.setUsuario(usuario);
			return wearableDao.save(wearable);
		}
		
		@GetMapping("/medidas")
		public  List<Wearable> getAllWearables(){
			return (List<Wearable>) wearableDao.findAll();
		}
}
