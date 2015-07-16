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
 * Responsable generated by hbm2java
 */
@Entity
@Table(name = "responsable", schema = "public")
public class Responsable implements java.io.Serializable {

	private int cedula;
	private Persona persona;
	private char estatus;
	private Set<Paciente> pacientes = new HashSet<Paciente>(0);

	public Responsable() {
	}

	public Responsable(Persona persona, char estatus) {
		this.persona = persona;
		this.estatus = estatus;
	}

	public Responsable(Persona persona, char estatus, Set<Paciente> pacientes) {
		this.persona = persona;
		this.estatus = estatus;
		this.pacientes = pacientes;
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

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "responsable")
	public Set<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
