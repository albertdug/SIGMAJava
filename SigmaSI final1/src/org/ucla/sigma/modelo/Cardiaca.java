package org.ucla.sigma.modelo;

// Generated 09/06/2012 11:00:56 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * Cardiaca generated by hbm2java
 */
@Entity
@Table(name = "cardiaca", schema = "public")
public class Cardiaca implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus = 'A';
	private Set<Cardiologia> cardiologias= new HashSet<Cardiologia>(0);

	public Cardiaca() {
	}

	public Cardiaca(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public Cardiaca(int id, String nombre, char estatus, Set<Cardiologia> cardiologias) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.cardiologias = cardiologias;
	}

	@Id
	@SequenceGenerator(name = "SEQ_CARDIACA", sequenceName = "seq_cardiaca", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARDIACA")
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cardiaca")
	public Set<Cardiologia> getCardiologias() {
		return cardiologias;
	}

	public void setCardiologias(Set<Cardiologia> cardiologias) {
		this.cardiologias = cardiologias;
	}

	@Override
	public boolean equals(Object obj) {
		Cardiaca comparacion = (Cardiaca) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Cardiaca, id: " + id;
	}

}
