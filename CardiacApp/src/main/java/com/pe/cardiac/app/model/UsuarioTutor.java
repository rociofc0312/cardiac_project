package com.pe.cardiac.app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuarios_tutor")
public class UsuarioTutor implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	@OneToMany(mappedBy="usuario_tutor",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<UsuarioPaciente> pacientes;

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

	public UsuarioTutor(int id, Usuario usuario) {
		super();
		this.id = id;
		this.usuario = usuario;
	}

	public UsuarioTutor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<UsuarioPaciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<UsuarioPaciente> pacientes) {
		this.pacientes = pacientes;
	}


}
