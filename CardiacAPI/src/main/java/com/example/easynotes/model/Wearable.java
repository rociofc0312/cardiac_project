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
@Table(name = "medida")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Wearable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private float ritmo;

	@NotNull
	private float oxigenacion;

	@Column(columnDefinition = "char(2)")
	@NotEmpty
	@NotNull
	private String estres;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	private boolean valor;

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
		return ritmo;
	}

	public void setRitmoCardiaco(float ritmoCardiaco) {
		this.ritmo = ritmoCardiaco;
	}

	public float getOxigenacion() {
		return oxigenacion;
	}

	public void setOxigenacion(float oxigenacion) {
		this.oxigenacion = oxigenacion;
	}

	public String getEstresCardiaco() {
		return estres;
	}

	public void setEstresCardiaco(String estresCardiaco) {
		this.estres = estresCardiaco;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isValor() {
		return valor;
	}

	public void setValor(boolean valor) {
		this.valor = valor;
	}

	public Wearable(int id, float ritmoCardiaco, float oxigenacion, String estresCardiaco, Date fecha) {
		super();
		this.id = id;
		this.ritmo = ritmoCardiaco;
		this.oxigenacion = oxigenacion;
		this.estres = estresCardiaco;
		this.fecha = fecha;
	}

	public Wearable() {
		super();
	}

}
