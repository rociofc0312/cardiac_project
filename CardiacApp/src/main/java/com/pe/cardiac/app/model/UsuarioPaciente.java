package com.pe.cardiac.app.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuarios_paciente")
public class UsuarioPaciente implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private UsuarioDoctor usuario_doctor;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private UsuarioTutor usuario_tutor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioPaciente(int id, Usuario usuario) {
		super();
		this.id = id;
		this.usuario = usuario;
	}

	public UsuarioPaciente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioDoctor getUsuario_doctor() {
		return usuario_doctor;
	}

	public void setUsuario_doctor(UsuarioDoctor usuario_doctor) {
		this.usuario_doctor = usuario_doctor;
	}

	public UsuarioTutor getUsuario_tutor() {
		return usuario_tutor;
	}

	public void setUsuario_tutor(UsuarioTutor usuario_tutor) {
		this.usuario_tutor = usuario_tutor;
	}

}
