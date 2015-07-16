package org.ucla.sigma.modelo;

// Generated 03/06/2012 11:28:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * Area generated by hbm2java
 */
@Entity
@Table(name = "area", schema = "public")
public class Area implements java.io.Serializable {

	private int id;
	private Hospital hospital;
	private String nombre;
	private String descripcion;
	private char estatus = 'A';

	public Area() {
	}

	public Area(int id, Hospital hospital, String nombre, char estatus) {
		this.id = id;
		this.hospital = hospital;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public Area(int id, Hospital hospital, String nombre, String descripcion,
			char estatus) {
		this.id = id;
		this.hospital = hospital;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	@Id
	@SequenceGenerator(name = "SEQ_AREA", sequenceName = "seq_area", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AREA")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospitalid", nullable = false)
	public Hospital getHospital() {
		return this.hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		Area comparacion = (Area) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Area, id: " + id;
	}

}
