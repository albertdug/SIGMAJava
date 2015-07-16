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

@Entity
@Table(name = "tipo_imagenologia", schema = "public")
public class TipoImagenologia implements java.io.Serializable {

	private int id;
	private String nombre;
	private String descripcion;
	private char estatus = 'A';
	private Set<EspImagenologia> espImagenologias = new HashSet<EspImagenologia>(0);

	public TipoImagenologia() {

	}

	public TipoImagenologia(int id, String nombre, String descripcion,
			char estatus, Set<EspImagenologia> espImagenologias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.espImagenologias = espImagenologias;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TIPOIMAG", sequenceName = "seq_tipoimag", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPOIMAG")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoImagenologia")
	public Set<EspImagenologia> getEspImagenologias() {
		return espImagenologias;
	}

	public void setEspImagenologias(Set<EspImagenologia> espImagenologias) {
		this.espImagenologias = espImagenologias;
	}

	
	@Override
	public boolean equals(Object obj) {
		TipoImagenologia comparacion = (TipoImagenologia) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return " Modelo Tipo Patología, id: " + id;
	}

}
