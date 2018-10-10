package com.example.easynotes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="medida")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Wearable implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private float ritmoCardiaco;
	
	@NotNull
	private float oxigenacion;
	
	@Column(columnDefinition="char(2)")
	@NotEmpty
	@NotNull
	private String estresCardiaco;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getRitmoCardiaco() {
		return ritmoCardiaco;
	}
	public void setRitmoCardiaco(float ritmoCardiaco) {
		this.ritmoCardiaco = ritmoCardiaco;
	}
	public float getOxigenacion() {
		return oxigenacion;
	}
	public void setOxigenacion(float oxigenacion) {
		this.oxigenacion = oxigenacion;
	}
	public String getEstresCardiaco() {
		return estresCardiaco;
	}
	public void setEstresCardiaco(String estresCardiaco) {
		this.estresCardiaco = estresCardiaco;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Wearable(int id, float ritmoCardiaco, float oxigenacion, String estresCardiaco, Date fecha) {
		super();
		this.id = id;
		this.ritmoCardiaco = ritmoCardiaco;
		this.oxigenacion = oxigenacion;
		this.estresCardiaco = estresCardiaco;
		this.fecha = fecha;
	}
	public Wearable() {
		super();
	}

}
