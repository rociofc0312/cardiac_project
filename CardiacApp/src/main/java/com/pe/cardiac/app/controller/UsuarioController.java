package com.pe.cardiac.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pe.cardiac.app.model.*;
import com.pe.cardiac.app.service.IRelacionService;
import com.pe.cardiac.app.service.IUsuarioService;
import com.pe.cardiac.app.service.IWearableService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRelacionService relacionService;

	@Autowired
	private IWearableService wearableService;

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
		model.addAttribute("usuario", new Usuario());
		return "usuario/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Usuario usuario, HttpSession session, RedirectAttributes flash, Model model) {
		Usuario user = usuarioService.find(usuario.getDni(), usuario.getContrasenia());
		if (user == null) {
			flash.addFlashAttribute("failed", "Los datos son incorrectos");
			return "redirect:/usuario/login";
		} else {
			if (user.getRol().equals("Doctor")) {
				session.setAttribute("UserSession", user);
				Iterable<Relacion> prueba = relacionService.findByDoctor(user.getId());
				model.addAttribute("listaPacientes", prueba);
				model.addAttribute("UserSession", user);
				return "doctor/main";
			} else if (user.getRol().equals("Paciente")) {
				session.setAttribute("UserSession", user);
				model.addAttribute("UserSession", user);
				return "paciente/estado";
			} else if (user.getRol().equals("Tutor")) {
				session.setAttribute("UserSession", user);
				model.addAttribute("UserSession", user);
				return "tutor/main";
			} else {
				session.setAttribute("UserSession", user);
				return "redirect:/usuario/sesion";
			}
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

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		session.removeAttribute("UserSession");
		request.getSession().invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "paciente/estado", method = RequestMethod.GET)
	public String estadoPaciente(Model model, HttpSession session) {
		return "paciente/estado";
	}

	@RequestMapping(value = "paciente/editarPerfil", method = RequestMethod.GET)
	public String editarPerfilPaciente() {
		return "paciente/editarPerfil";
	}

	@RequestMapping(value = "paciente/misTutores", method = RequestMethod.GET)
	public String tutoresPaciente(Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		Iterable<Usuario> tutores = usuarioService.listUsuarioByRol("Tutor");
		Iterable<Relacion> misTutores = relacionService.findByPaciente(usuarioPaciente.getId());
		ArrayList<Usuario> tutoresFinal = new ArrayList<Usuario>();

		model.addAttribute("listaTutores", tutores);
		model.addAttribute("listaMisTutores", misTutores);
		model.addAttribute("usuario", new Usuario());
		return "paciente/misTutores";
	}

	@RequestMapping(value = "paciente/misDoctores", method = RequestMethod.GET)
	public String doctoresPaciente(Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		Iterable<Usuario> doctores = usuarioService.listUsuarioByRol("Doctor");
		Iterable<Relacion> misDoctores = relacionService.findByPaciente(usuarioPaciente.getId());
		model.addAttribute("listaDoctores", doctores);
		model.addAttribute("listaMisDoctores", misDoctores);
		model.addAttribute("usuario", new Usuario());
		return "paciente/misDoctores";
	}

	@RequestMapping(value = "paciente/addTutorP{id}")
	public String addTutorPacienteRelacion(@PathVariable Integer id, Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		Usuario usuarioTutor = usuarioService.findByID(id);
		Relacion relacion = new Relacion();
		relacion.setUsuarioPaciente(usuarioPaciente);
		relacion.setUsuarioTutor(usuarioTutor);
		relacionService.save(relacion);
		return "redirect:/usuario/paciente/misTutores";
	}

	@RequestMapping(value = "paciente/addDoctorP{id}")
	public String addDoctorPacienteRelacion(@PathVariable Integer id, Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		Usuario usuarioDoctor = usuarioService.findByID(id);
		Relacion relacion = new Relacion();
		relacion.setUsuarioPaciente(usuarioPaciente);
		relacion.setUsuarioDoctor(usuarioDoctor);
		relacionService.save(relacion);
		return "redirect:/usuario/paciente/misDoctores";
	}

	@RequestMapping(value = "paciente/deleteDoctorP/{id}")
	public String eliminarEncargado(@PathVariable Integer id, Model model, HttpSession session) {
		relacionService.delete(id);
		return "redirect:/usuario/paciente/misDoctores";
	}

	@RequestMapping(value = "doctor/misPacientes", method = RequestMethod.GET)
	public String doctorPacientes() {
		return "doctor/main";
	}

	@RequestMapping(value = "doctor/editarPerfil", method = RequestMethod.GET)
	public String doctorEditar() {
		return "doctor/perfil";
	}

	@RequestMapping(value = "tutor/misAsociados", method = RequestMethod.GET)
	public String tutorAsociados() {
		return "tutor/main";
	}

	@RequestMapping(value = "tutor/editarPerfil", method = RequestMethod.GET)
	public String tutorEditar() {
		return "tutor/perfil";
	}

	@RequestMapping(value = "paciente/estadoGrafico", method = RequestMethod.GET)
	public String graficoPaciente(Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
		List<Integer> listPrueba = new ArrayList<Integer>();
		List<Integer> listOficial = new ArrayList<Integer>();

		for (Wearable wearable : wearablesxPaciente) {
			listPrueba.add(Integer.parseInt(wearable.getEstresCardiaco()));
		}
		/*
		 * int j = 0; for (int i = listPrueba.size(); j < 10; j--) { int aux =
		 * i-10; listOficial.add(listPrueba.get(aux)); aux = aux+11; j++; }
		 */

		// Collections.sort(wearablesxPaciente, Collections.reverseOrder());
		model.addAttribute("listita", listPrueba);
		return "paciente/graphics";
	}

	@RequestMapping(value = "paciente/estadoOxigenacion", method = RequestMethod.GET)
	public String graficoOxigenacionPaciente(Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
		List<Float> listaOxigenación = new ArrayList<Float>();

		for (Wearable wearable : wearablesxPaciente) {
			listaOxigenación.add(wearable.getOxigenacion());
		}

		model.addAttribute("listaOxigenacion", listaOxigenación);
		return "paciente/oxigenation";
	}
	@RequestMapping(value = "paciente/estadoGraficoPaciente{id}", method = RequestMethod.GET)
	public String graficoRitmoCardiacoPacienteByDoctor(@PathVariable Integer id, Model model, HttpSession session) {
		Usuario usuarioPaciente = usuarioService.findByID(id);
		List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
		List<Integer> listPrueba = new ArrayList<Integer>();
		List<Integer> listOficial = new ArrayList<Integer>();

		for (Wearable wearable : wearablesxPaciente) {
			listPrueba.add(Integer.parseInt(wearable.getEstresCardiaco()));
		}
		model.addAttribute("listita", listPrueba);
		return "paciente/graphics";
	}
	
	@RequestMapping(value = "paciente/estadoOxigenacionPaciente{id}", method = RequestMethod.GET)
	public String graficoOxigenacionPacienteByDoctor(@PathVariable Integer id ,Model model, HttpSession session) {
		Usuario usuarioPaciente = usuarioService.findByID(id);
		List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
		List<Float> listaOxigenación = new ArrayList<Float>();

		for (Wearable wearable : wearablesxPaciente) {
			listaOxigenación.add(wearable.getOxigenacion());
		}

		model.addAttribute("listaOxigenacion", listaOxigenación);
		return "paciente/oxigenation";
	}

}
