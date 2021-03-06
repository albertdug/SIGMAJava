package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sexo generated by hbm2java
 */
@Entity
@Table(name = "sexo", schema = "public")
public class Sexo implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus;
	private Set<Paciente> pacientes = new HashSet<Paciente>(0);

	public Sexo() {
	}

	public Sexo(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public Sexo(int id, String nombre, char estatus, Set<Paciente> pacientes) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.pacientes = pacientes;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sexo")
	public Set<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
