package com.pe.cardiac.app.model;

import javax.persistence.*;
@Entity
@Table(name="relacion")
public class Relacion {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="IDPropietario")
	private Usuario userA;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="IDTitular")
	private Usuario userB;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="IDProveedor")
	private Usuario userC;

	public Relacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUserA() {
		return userA;
	}

	public void setUserA(Usuario userA) {
		this.userA = userA;
	}

	public Usuario getUserB() {
		return userB;
	}

	public void setUserB(Usuario userB) {
		this.userB = userB;
	}

	public Usuario getUserC() {
		return userC;
	}

	public void setUserC(Usuario userC) {
		this.userC = userC;
	}
	
	


}
