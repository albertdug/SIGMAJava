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
 * Cojera generated by hbm2java
 */
@Entity
@Table(name = "Cojera", schema = "public")
public class Cojera implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus = 'A';
	private Set<Neurologia> neurologias = new HashSet<Neurologia>(0);

	public Cojera() {
	}

	public Cojera(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}
	
	public Cojera(int id, String nombre, char estatus, Set<Neurologia> neurologias) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.neurologias = neurologias;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cojera")
	public Set<Neurologia> getNeurologias() {
		return this.neurologias;
	}

	public void setNeurologias(Set<Neurologia> neurologias) {
		this.neurologias = neurologias;
	}

	@Id
	@SequenceGenerator(name = "SEQ_COJERA", sequenceName = "seq_cojera", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COJERA")
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

	@Override
	public boolean equals(Object obj) {
		Cojera comparacion = (Cojera) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return " Modelo Cojera, id: " + id;
	}

}
