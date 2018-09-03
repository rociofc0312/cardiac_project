package com.pe.cardiac.app.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pe.cardiac.app.model.*;
import com.pe.cardiac.app.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "redirect:/usuario/login";
	}

	@RequestMapping(value = "/sesion", method = RequestMethod.GET)
	public String sesion() {
		return "/usuario/sesion";
	}

	@RequestMapping(value = "/login")
	public String login(Model model, HttpSession session) {
		if (session.getAttribute("UserSession") != null) {
			model.addAttribute("usuario", new Usuario());
			return "usuario/sesion";
		} else {
			return "usuario/login";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Usuario usuario, HttpSession session, RedirectAttributes flash) {
		Usuario user = usuarioService.find(usuario.getDni(), usuario.getContrasenia());
		if (user == null) {
			flash.addFlashAttribute("failed", "Los datos son incorrectos");
			return "redirect:/usuario/login";
		} else {
			session.setAttribute("UserSession", user);
			return "redirect:/usuario/sesion";
		}
	}

	@RequestMapping(value = "/registro")
	public String crearDoctor(Map<String, Object> model) {
		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		model.put("titulo", "Registro de Usuario");
		return "usuario/registro";
	}

	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public String guardarDoctor(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registro de Usuario");
			model.addAttribute("correcto", "No se guardó correctamente");
			return "usuario/registro";
		}
		try {
			if (usuario.getRol().equals("Paciente")) {
				UsuarioPaciente usuarioPaciente = new UsuarioPaciente();
				usuario.setUsuarioPaciente(usuarioPaciente);
				usuarioPaciente.setUsuario(usuario);
			} else if (usuario.getRol().equals("Doctor")) {
				UsuarioDoctor usuarioDoctor = new UsuarioDoctor();
				usuario.setUsuarioDoctor(usuarioDoctor);
				usuarioDoctor.setUsuario(usuario);
			} else if (usuario.getRol().equals("Tutor")) {
				UsuarioTutor usuarioTutor = new UsuarioTutor();
				usuario.setUsuarioTutor(usuarioTutor);
				usuarioTutor.setUsuario(usuario);
			}
			usuarioService.save(usuario);
			status.setComplete();
			flash.addFlashAttribute("correcto", "El usuario se guardó correctamente");
			return "redirect:/usuario/registro";
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("titulo", "Registro de Tutor");
			model.addAttribute("correcto", "El registro de esta persona ya existe");
			return "usuario/registro";
		}
	}

}