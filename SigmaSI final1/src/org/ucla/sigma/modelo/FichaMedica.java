package org.ucla.sigma.modelo;

// Generated 03/06/2012 11:28:14 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * FichaMedica generated by hbm2java
 */
@Entity
@Table(name = "ficha_medica", schema = "public")
public class FichaMedica extends Historial implements java.io.Serializable {

	private String caracteristicas;
	private String alergias;
	private String observaciones;
	
	public FichaMedica() {
		super.setTipoServicio(new TipoServicio("FME"));
	}

	public FichaMedica(int id, Paciente paciente, Veterinario veterinario,
			Date fecha, Date hora) {
		super.setId(id);
		super.setPaciente(paciente);
		super.setVeterinario(veterinario);
		super.setFecha(fecha);
		super.setHora(hora);
		super.setTipoServicio(new TipoServicio("FME"));
	}

	@Column(name = "caracteristicas", nullable = false)	
	public String getCaracteristicas() {
		return this.caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
	@Column(name = "alergias", nullable = false)	
	public String getAlergias() {
		return this.alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	@Column(name = "observaciones", nullable = false)	
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	@Override
	public String toString() {
		return "Modelo FichaMedica,  id: " + super.getId();
	}

}