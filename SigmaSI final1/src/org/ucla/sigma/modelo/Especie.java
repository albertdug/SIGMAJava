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
 * Especie generated by hbm2java
 */
@Entity
@Table(name = "especie", schema = "public")
public class Especie implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus = 'A';
	private Set<Raza> razas = new HashSet<Raza>(0);

	public Especie() {
	}

	public Especie(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public Especie(int id, String nombre, char estatus, Set<Raza> razas) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.razas = razas;
	}

	@Id
	@SequenceGenerator(name = "SEQ_ESPECIE", sequenceName = "seq_especie", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPECIE")
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "especie")
	public Set<Raza> getRazas() {
		return this.razas;
	}

	public void setRazas(Set<Raza> razas) {
		this.razas = razas;
	}

	@Override
	public boolean equals(Object obj) {
		Especie comparacion = (Especie) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return " Modelo Especie, id: " + id;
	}

}
