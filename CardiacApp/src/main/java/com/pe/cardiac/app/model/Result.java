package com.pe.cardiac.app.model;

import java.util.Date;

public class Result {
	private float ritmo;

	private float oxigenacion;
	private boolean valor;

	private Date fecha;

	public float getRitmo() {
		return ritmo;
	}

	public void setRitmo(float ritmo) {
		this.ritmo = ritmo;
	}

	public float getOxigenacion() {
		return oxigenacion;
	}

	public void setOxigenacion(float oxigenacion) {
		this.oxigenacion = oxigenacion;
	}

	public boolean getValor() {
		return valor;
	}

	public void setEstres(boolean valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
