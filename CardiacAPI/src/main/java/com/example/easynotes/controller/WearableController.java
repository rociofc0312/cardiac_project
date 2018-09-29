package com.example.easynotes.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.model.Wearable;
import com.example.easynotes.repository.IWearableDao;

@RestController
@RequestMapping("/api")
public class WearableController {
		
		@Autowired
		IWearableDao wearableDao;
		
		 @GetMapping
		    public String sayHello() {
		        return "Hello and Welcome to the EasyNotes application. You can create a new Note by making a POST request to /api/notes endpoint.";
		    }
		
		@PostMapping("/medidas")
		public Wearable crearMedida(@Valid @RequestBody Wearable wearable) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
			Date fechaActual = new Date();
			//wearable.setFecha(dateFormat.format(fechaActual));
			return wearableDao.save(wearable);
		}
		
		@GetMapping("/medidas")
		public  List<Wearable> getAllWearables(){
			return (List<Wearable>) wearableDao.findAll();
		}
}
