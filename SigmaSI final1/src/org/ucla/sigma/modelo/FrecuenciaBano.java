package org.ucla.sigma.modelo;

// Generated 09/06/2012 03:00:53 PM by Hibernate Tools 3.4.0.CR1

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


@Entity
@Table(name = "frecuencia_bano", schema = "public")
public class FrecuenciaBano implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus ='A';
	private Set<Dermatologia> dermatologias = new HashSet<Dermatologia>(0);
	
	public FrecuenciaBano() {
	}

	public FrecuenciaBano(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	@Id
	@SequenceGenerator(name = "SEQ_FRECUENCIA_BANO", sequenceName = "seq_frecuencia_bano", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FRECUENCIA_BANO")
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frecuenciaBano")
	public Set<Dermatologia> getDermatologias() {
		return dermatologias;
	}

	public void setDermatologias(Set<Dermatologia> dermatologias) {
		this.dermatologias = dermatologias;
	}

	@Override
	public boolean equals(Object obj) {
		FrecuenciaBano comparacion = (FrecuenciaBano) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo FrecuenciaBano, id: " + id;
	}

}
