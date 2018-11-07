package com.pe.cardiac.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

//import org.mockito.exceptions.verification.NeverWantedButInvoked;
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
import com.pe.cardiac.app.service.INotificacionService;
import com.pe.cardiac.app.service.IRelacionService;
import com.pe.cardiac.app.service.IUsuarioService;
import com.pe.cardiac.app.service.IWearableService;
import com.pe.cardiac.app.util.ListPDT;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRelacionService relacionService;

	@Autowired
	private IWearableService wearableService;

	@Autowired
	private INotificacionService notificacionService;

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
				Usuario paciente = new Usuario();
				model.addAttribute("paciente", paciente);
				List<Relacion> pacientes = relacionService.findByDoctor(user.getId());
				List<Notificacion> notificaciones = (List<Notificacion>) notificacionService.findByDoctor(user);
				List<Wearable> historial = null;
				Notificacion notificacion = new Notificacion();
				for (int i = 0; i < pacientes.size(); i++) {
					historial = wearableService.findByUsuario(pacientes.get(i).getUsuarioPaciente());
					for (int j = 0; j < historial.size(); j++) {
						if (historial.get(j).getRitmoCardiaco() > 90.0) {
							notificacion.setUsuarioDoctor(user);
							notificacion.setUsuarioPaciente(pacientes.get(i).getUsuarioPaciente());
							notificacion.setFecha_notificacion(historial.get(j).getFecha());
							notificacion.setDetalle("Presion alta");
							notificacion.setEstado("Sin leer");
							notificacionService.save(notificacion);
						}
					}
				}
				model.addAttribute("listaPacientes", pacientes);
				model.addAttribute("notificaciones", notificaciones);
				model.addAttribute("UserSession", user);
				return "doctor/main";
			} else if (user.getRol().equals("Paciente")) {
				session.setAttribute("UserSession", user);
				model.addAttribute("UserSession", user);
				return "paciente/main";
			} else if (user.getRol().equals("Tutor")) {
				session.setAttribute("UserSession", user);
				Iterable<Relacion> asociados = relacionService.findByTutor(user.getId());
				model.addAttribute("listaAsociados", asociados);
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String actUsuario(@Valid Usuario paciente, BindingResult result, Model model) {
		try {
			Usuario pacienteMod = usuarioService.findByID(paciente.getId());
			pacienteMod.setFrecuencia(paciente.getFrecuencia());
			usuarioService.save(pacienteMod);
			return "redirect:/usuario/doctor/misPacientes";
		} catch (DataIntegrityViolationException e) {
			return "redirect:/usuario/doctor/misPacientes";
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
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Wearable> medidas = wearableService.getAveragePerDay(usuarioPaciente.getId());
		for (Wearable wearable : medidas) {
			System.out.println("oxigenacion: " + wearable.getOxigenacion());
			System.out.println("ritmo: " + wearable.getRitmoCardiaco());
			System.out.println("fecha: " + wearable.getFecha());
			System.out.println("=====================");
		}
		

		return "paciente/estado";
	}

	@RequestMapping(value = "paciente/editarPerfil", method = RequestMethod.GET)
	public String editarPerfilPaciente() {
		return "paciente/editarPerfil";
	}

	// @SuppressWarnings("null")
	@RequestMapping(value = "paciente/misTutores", method = RequestMethod.GET)
	public String tutoresPaciente(Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Usuario> tutores = usuarioService.listUsuarioByRol("Tutor");
		List<Relacion> misTutoresAux = relacionService.findByPaciente(usuarioPaciente.getId());
		model.addAttribute("listaTutores", tutores);

		List<ListPDT> misTutores = new ArrayList<ListPDT>();

		for (int i = 0; i < misTutoresAux.size(); i++) {
			if (misTutoresAux.get(i).getUsuarioDoctor() == null) {
				ListPDT tutor = new ListPDT();
				tutor.setId(misTutoresAux.get(i).getId());
				tutor.setDni(misTutoresAux.get(i).getUsuarioTutor().getDni());
				tutor.setNombre(misTutoresAux.get(i).getUsuarioTutor().getNombre());

				misTutores.add(tutor);
			}
		}

		model.addAttribute("listaMisTutores", misTutores);
		model.addAttribute("usuario", new Usuario());
		return "paciente/misTutores";
	}

	@RequestMapping(value = "paciente/misDoctores", method = RequestMethod.GET)
	public String doctoresPaciente(Model model, HttpSession session) {
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Usuario> doctores = usuarioService.listUsuarioByRol("Doctor");
		List<Relacion> misDoctoresAux = relacionService.findByPaciente(usuarioPaciente.getId());
		model.addAttribute("listaDoctores", doctores);

		List<ListPDT> misDoctores = new ArrayList<ListPDT>();

		for (int i = 0; i < misDoctoresAux.size(); i++) {
			if (misDoctoresAux.get(i).getUsuarioTutor() == null) {
				ListPDT doctor = new ListPDT();
				doctor.setId(misDoctoresAux.get(i).getId());
				doctor.setDni(misDoctoresAux.get(i).getUsuarioDoctor().getDni());
				doctor.setNombre(misDoctoresAux.get(i).getUsuarioDoctor().getNombre());

				misDoctores.add(doctor);
			}
		}
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
	public String eliminarDoctorRelacion(@PathVariable Integer id, Model model, HttpSession session) {
		relacionService.delete(id);
		return "redirect:/usuario/paciente/misDoctores";
	}

	@RequestMapping(value = "paciente/deleteTutorP/{id}")
	public String eliminarTutorRelacion(@PathVariable Integer id, Model model, HttpSession session) {
		relacionService.delete(id);
		return "redirect:/usuario/paciente/misTutores";
	}

	@RequestMapping(value = "doctor/misPacientes", method = RequestMethod.GET)
	public String doctorPacientes(Model model, HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("UserSession");
		List<Relacion> pacientes = relacionService.findByDoctor(user.getId());
		List<Notificacion> notificaciones = (List<Notificacion>) notificacionService.findByDoctor(user);
		Usuario paciente = new Usuario();
		model.addAttribute("paciente", paciente);
		model.addAttribute("listaPacientes", pacientes);
		model.addAttribute("notificaciones", notificaciones);
		return "doctor/main";
	}

	@RequestMapping(value = "doctor/editarPerfil", method = RequestMethod.GET)
	public String doctorEditar() {
		return "doctor/perfil";
	}

	@RequestMapping(value = "doctor/verPacienteDetalle{id}", method = RequestMethod.GET)
	public String doctorPacienteDetalle(Model model, @PathVariable Integer id) {
		try {
			List<Wearable> medidasDay = wearableService.getMedidasOfDay("2018-09-20", id);
			model.addAttribute("listaMedidasPaciente", medidasDay);
			return "doctor/pacienteDetallesFecha";
		} catch (Exception e) {
			model.addAttribute("listaMedidasPaciente", "error");
			return "doctor/pacienteDetallesFecha";
		}
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
		int val;
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
		List<Float> listPrueba = new ArrayList<Float>();
		// List<Integer> listOficial = new ArrayList<Integer>();
		Collections.sort(wearablesxPaciente, new Comparator<Wearable>() {
			public int compare(Wearable o1, Wearable o2) {
				// TODO Auto-generated method stub
				return o2.getFecha().compareTo(o1.getFecha());
			}
		});

		if (wearablesxPaciente.size() < 10) {
			val = 0;
		} else {
			val = wearablesxPaciente.size() - 10;
		}
		for (int i = wearablesxPaciente.size() - val; i > 0; i--) {
			listPrueba.add((float) wearablesxPaciente.get(i - 1).getRitmoCardiaco());
		}
		model.addAttribute("listita", listPrueba);
		return "paciente/graphics";
	}

	@RequestMapping(value = "paciente/estadoOxigenacion", method = RequestMethod.GET)
	public String graficoOxigenacionPaciente(Model model, HttpSession session) {
		int val;
		Usuario usuarioPaciente = (Usuario) session.getAttribute("UserSession");
		List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
		List<Float> listaOxigenación = new ArrayList<Float>();

		Collections.sort(wearablesxPaciente, new Comparator<Wearable>() {
			public int compare(Wearable o1, Wearable o2) {
				// TODO Auto-generated method stub
				return o2.getFecha().compareTo(o1.getFecha());
			}
		});

		if (wearablesxPaciente.size() < 10) {
			val = 0;
		} else {
			val = wearablesxPaciente.size() - 10;
		}
		for (int i = wearablesxPaciente.size() - val; i > 0; i--) {
			listaOxigenación.add(wearablesxPaciente.get(i - 1).getOxigenacion());
		}

		model.addAttribute("listaOxigenacion", listaOxigenación);
		return "paciente/oxigenation";
	}

	@RequestMapping(value = "paciente/estadoGraficoPaciente{id}", method = RequestMethod.GET)
	public String graficoRitmoCardiacoPacienteByDoctor(@PathVariable Integer id, Model model, HttpSession session) {
		try {
			int val;
			Usuario usuarioPaciente = usuarioService.findByID(id);
			List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
			List<Float> listPrueba = new ArrayList<Float>();
			// List<Integer> listOficial = new ArrayList<Integer>();

			Collections.sort(wearablesxPaciente, new Comparator<Wearable>() {
				public int compare(Wearable o1, Wearable o2) {
					// TODO Auto-generated method stub
					return o2.getFecha().compareTo(o1.getFecha());
				}
			});

			if (wearablesxPaciente.size() < 10) {
				val = 0;
			} else {
				val = wearablesxPaciente.size() - 10;
			}
			for (int i = wearablesxPaciente.size() - val; i > 0; i--) {
				listPrueba.add((float) wearablesxPaciente.get(i - 1).getRitmoCardiaco());
			}
			model.addAttribute("listita", listPrueba);
			return "paciente/graphics";
		} catch (Exception e) {

			model.addAttribute("listita", "error");
			return "paciente/graphics";
		}
	}

	@RequestMapping(value = "paciente/estadoOxigenacionPaciente{id}", method = RequestMethod.GET)
	public String graficoOxigenacionPacienteByDoctor(@PathVariable Integer id, Model model, HttpSession session) {
		try {
			int val;
			Usuario usuarioPaciente = usuarioService.findByID(id);
			List<Wearable> wearablesxPaciente = wearableService.findByUsuario(usuarioPaciente);
			List<Float> listaOxigenación = new ArrayList<Float>();

			Collections.sort(wearablesxPaciente, new Comparator<Wearable>() {
				public int compare(Wearable o1, Wearable o2) {
					// TODO Auto-generated method stub
					return o2.getFecha().compareTo(o1.getFecha());
				}
			});

			if (wearablesxPaciente.size() < 10) {
				val = 0;
			} else {
				val = wearablesxPaciente.size() - 10;
			}
			for (int i = wearablesxPaciente.size() - val; i > 0; i--) {
				listaOxigenación.add(wearablesxPaciente.get(i - 1).getOxigenacion());
			}

			model.addAttribute("listaOxigenacion", listaOxigenación);
			return "paciente/oxigenation";
		} catch (Exception e) {

			model.addAttribute("listaOxigenacion", "error");
			return "paciente/oxigenation";
		}
	}

	@RequestMapping(value = "paciente/tableDatosCardiac{id}", method = RequestMethod.GET)
	public String tablePacientePrincipal(@PathVariable Integer id, Model model, HttpSession session) {
		try {
			List<Wearable> medidas = wearableService.getAverageLastTenDays(id);
			model.addAttribute("datosPrinc", medidas);
			return "paciente/tablePacientePr";
		} catch (Exception e) {
			model.addAttribute("datosPrinc", "error");
			return "paciente/tablePacientePr";
		}
	}

	@RequestMapping(value = "paciente/tableInterm{id}", method = RequestMethod.GET)
	public String tablePacienteInterm(@PathVariable Integer id, Model model, HttpSession session) {
		try {
			List<Wearable> medidas = wearableService.getAveragePerDay(id);
			model.addAttribute("datosInterm", medidas);
			return "paciente/tableIntermPac";
		} catch (Exception e) {
			model.addAttribute("datosInterm", "error");
			return "paciente/tableIntermPac";
		}
	}

}
