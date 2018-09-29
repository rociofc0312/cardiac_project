package com.example.easynotes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition="char(8)")
	@NotEmpty
	@NotNull
	@Length(min=8, max=8)
	private String dni;
	
	@NotEmpty
	@NotNull
	@Length(min=5, max=50)
	@Pattern(regexp="(?!^\\d+$)^.+$")
	private String nombre;
	
	@NotEmpty
	@NotNull
	@Length(min=5, max=50)
	@Pattern(regexp="(?!^\\d+$)^.+$")
	private String apellido;
	
	@NotEmpty
	@NotNull
	private String telefono;
	
	@NotEmpty
	@NotNull
	private String correo;
	
	@NotEmpty
	@NotNull
	private String contrasenia;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	@DateTimeFormat(pattern="yyyy-dd-MM")
	private Date fecha_nacimiento;
	
	@Column(columnDefinition="char(1)")
	@NotEmpty
	@NotNull
	private String sexo;
	
	@NotEmpty
	@NotNull
	private String rol;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
}
