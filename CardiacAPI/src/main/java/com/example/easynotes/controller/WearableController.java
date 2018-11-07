package com.example.easynotes.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.model.Notificacion;
import com.example.easynotes.model.Relacion;
import com.example.easynotes.model.Usuario;
import com.example.easynotes.model.Wearable;
import com.example.easynotes.repository.INotificacionDao;
import com.example.easynotes.repository.IRelacionDao;
import com.example.easynotes.repository.IUsuarioDao;
import com.example.easynotes.repository.IWearableDao;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

@RestController
@RequestMapping("/api")
public class WearableController {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	IWearableDao wearableDao;
	@Autowired
	IUsuarioDao usuarioDao;
	@Autowired
	INotificacionDao notificacionDao;
	@Autowired
	IRelacionDao relacionDao;

	@GetMapping
	public String sayHello() {
		return "Hello and Welcome to the EasyNotes application. You can create a new Note by making a POST request to /api/notes endpoint.";
	}

	@GetMapping("/usuarios/{dni}/{contrasenia}")
	public Usuario obtenerUsuario(@PathVariable String dni, @PathVariable String contrasenia) {
		Usuario usuario = usuarioDao.findByDniAndContrasenia(dni, contrasenia);
		return usuario;
	}

	@PostMapping("/usuarios/{id}/medidas")
	public Wearable crearMedida(@PathVariable String id, @Valid @RequestBody Wearable wearable) throws ParseException {
		Usuario usuario = usuarioDao.findById(Integer.parseInt(id));
		List<Relacion> listaDeDoctores = relacionDao.findByUsuarioPacienteId(Integer.parseInt(id));

		/*
		 * SimpleDateFormat sdfDate = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date now = new Date();
		 * String strDate = sdfDate.format(now);
		 * wearable.setFecha(sdfDate.parse(strDate));
		 */
		SimpleMailMessage message = new SimpleMailMessage();
		wearable.setUsuario(usuario);

		//if(wearable.getRitmoCardiaco()-USUARIOS.PROMEDIO>20 ||wearable.getRitmoCardiaco()-USUARIOS.PROMEDIO<-20) {
		if ((wearable.getRitmoCardiaco()-usuario.getFrecuencia() > 20)|| (wearable.getRitmoCardiaco()-usuario.getFrecuencia()-usuario.getFrecuencia()<-20)) {
			for (Relacion doctor : listaDeDoctores) {
				message.setText("El usuario" + wearable.getUsuario().getNombre() + " "
						+ wearable.getUsuario().getApellido() + "Presenta una presion alta");
				message.setTo(doctor.getUsuarioDoctor().getCorreo());
				message.setFrom("Cardiac Alert");
				message.setSubject("Alerta de presion alta");
				mailSender.send(message);
			}
		}
		// usuario = usuarioDao.findById(wearable.getUsuario().getId()).get();
		// wearable.setUsuario(usuario);
		return wearableDao.save(wearable);
	}

	@GetMapping("/usuarios/{id}/medidas")
	public List<Wearable> verMedidas(@PathVariable String id) {
		Usuario usuario = usuarioDao.findById(Integer.parseInt(id));
		return wearableDao.findByUsuario(usuario);
	}

	@GetMapping("/medidas")
	public List<Wearable> getAllWearables() {
		return (List<Wearable>) wearableDao.findAll();
	}

	@PostMapping("/usuarios/{id}/notificaciones")
	public Notificacion crearNotificacion(@PathVariable String id, @Valid @RequestBody Notificacion notificacion)
			throws ParseException {
		Usuario usuarioPaciente = usuarioDao.findById(Integer.parseInt(id));
		notificacion.setUsuarioPaciente(usuarioPaciente);
		return notificacionDao.save(notificacion);
	}
}
