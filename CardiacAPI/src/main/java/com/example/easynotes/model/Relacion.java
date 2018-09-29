package com.example.easynotes.model;

import javax.persistence.*;
@Entity
@Table(name="relacion")
public class Relacion {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="paciente_id")
	private Usuario usuarioPaciente;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="doctor_id")
	private Usuario usuarioDoctor;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tutor_id")
	private Usuario usuarioTutor;

	public Relacion() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuarioPaciente() {
		return usuarioPaciente;
	}

	public void setUsuarioPaciente(Usuario usuarioPaciente) {
		this.usuarioPaciente = usuarioPaciente;
	}

	public Usuario getUsuarioDoctor() {
		return usuarioDoctor;
	}

	public void setUsuarioDoctor(Usuario usuarioDoctor) {
		this.usuarioDoctor = usuarioDoctor;
	}

	public Usuario getUsuarioTutor() {
		return usuarioTutor;
	}

	public void setUsuarioTutor(Usuario usuarioTutor) {
		this.usuarioTutor = usuarioTutor;
	}
	
	
}
