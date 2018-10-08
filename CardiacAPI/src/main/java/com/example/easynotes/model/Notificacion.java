package com.example.easynotes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="notificaciones")
public class Notificacion implements Serializable{
	
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
	
	private String detalle;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	@DateTimeFormat(pattern="yyyy-dd-MM")
	private Date fecha_notificacion;
	
	private String estado;

	public Notificacion() {
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFecha_notificacion() {
		return fecha_notificacion;
	}

	public void setFecha_notificacion(Date fecha_notificacion) {
		this.fecha_notificacion = fecha_notificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}	

}
