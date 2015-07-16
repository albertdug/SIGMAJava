package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Veterinario generated by hbm2java
 */
@Entity
@Table(name = "veterinario", schema = "public")
public class Veterinario implements java.io.Serializable {

	private int cedula;
	private Persona persona;
	private String numeroSanidad;
	private String numeroColegio;
	private String rif;
	private char estatus;
	private Set<Historial> historials = new HashSet<Historial>(0);

	public Veterinario() {
	}

	public Veterinario(Persona persona, char estatus) {
		this.persona = persona;
		this.estatus = estatus;
	}

	public Veterinario(Persona persona, String numeroSanidad,
			String numeroColegio, String rif, char estatus,
			Set<Historial> historials) {
		this.persona = persona;
		this.numeroSanidad = numeroSanidad;
		this.numeroColegio = numeroColegio;
		this.rif = rif;
		this.estatus = estatus;
		this.historials = historials;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "persona"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "cedula", unique = true, nullable = false)
	public int getCedula() {
		return this.cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Column(name = "numero_sanidad", length = 20)
	public String getNumeroSanidad() {
		return this.numeroSanidad;
	}

	public void setNumeroSanidad(String numeroSanidad) {
		this.numeroSanidad = numeroSanidad;
	}

	@Column(name = "numero_colegio", length = 20)
	public String getNumeroColegio() {
		return this.numeroColegio;
	}

	public void setNumeroColegio(String numeroColegio) {
		this.numeroColegio = numeroColegio;
	}

	@Column(name = "rif", length = 20)
	public String getRif() {
		return this.rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "veterinario")
	public Set<Historial> getHistorials() {
		return this.historials;
	}

	public void setHistorials(Set<Historial> historials) {
		this.historials = historials;
	}

}
