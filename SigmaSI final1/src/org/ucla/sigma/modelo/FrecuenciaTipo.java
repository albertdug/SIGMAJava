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
@Table(name = "frecuencia_tipo", schema = "public")
public class FrecuenciaTipo implements java.io.Serializable {

	private String id;
	private String nombre;
	private char estatus ='A';
	private Set<Frecuencia> frecuencias = new HashSet<Frecuencia>(0);
	
	public FrecuenciaTipo() {
	}

	public FrecuenciaTipo(String id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 5)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frecuenciaTipo")
	public Set<Frecuencia> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(Set<Frecuencia> frecuencias) {
		this.frecuencias = frecuencias;
	}

	@Override
	public boolean equals(Object obj) {
		FrecuenciaTipo comparacion = (FrecuenciaTipo) obj;
		return comparacion.getId().equalsIgnoreCase(id);
	}

	@Override
	public String toString() {
		return "Modelo FrecuenciaTipo, id: " + id;
	}

}
