package org.ucla.sigma.modelo;

// Generated 03/06/2012 11:28:14 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;


/**
 * Pulpejos generated by hbm2java
 */
@Entity
@Table(name = "pulpejos", schema = "public")
public class Pulpejos implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus = 'A';
//	private Set<Paciente> pacientes = new HashSet<Paciente>(0);

	public Pulpejos() {
	}

	public Pulpejos(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

//	public Pulpejos(int id, String nombre, char estatus, Set<Paciente> pacientes) {
//		this.id = id;
//		this.nombre = nombre;
//		this.estatus = estatus;
//		this.pacientes = pacientes;
//	}

	@Id
	@SequenceGenerator(name = "SEQ_PULPEJOS", sequenceName = "seq_pulpejos", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PULPEJOS")
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

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pulpejos")
//	public Set<Paciente> getPacientes() {
//		return this.pacientes;
//	}
//
//	public void setPacientes(Set<Paciente> pacientes) {
//		this.pacientes = pacientes;
//	}
	
	@Override
	public boolean equals(Object obj) {
		Pulpejos comparacion = (Pulpejos) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Pulpejos, id: " + id;
	}

}
