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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * TipoReferencia generated by hbm2java
 */
@Entity
@Table(name = "tipo_referencia", schema = "public")
public class TipoReferencia implements java.io.Serializable {

	private String id;
	private String nombre;
	private char estatus = 'A';
	private Set<TipoServicio> tipoServicios = new HashSet<TipoServicio>(0);
	private Set<Referencia> referencias = new HashSet<Referencia>(0);

	public TipoReferencia() {
	}

	public TipoReferencia(String id) {
		this.id = id;
	}

	public TipoReferencia(String id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public TipoReferencia(String id, String nombre, char estatus,
			Set<Referencia> referencias) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.referencias = referencias;
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "tipo_referencia_tipo_servicio", schema = "public", joinColumns = { @JoinColumn(name = "tipo_referenciaid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "tipo_servicioid", nullable = false, updatable = false) })
	public Set<TipoServicio> getTipoServicios() {
		return this.tipoServicios;
	}

	public void setTipoServicios(Set<TipoServicio> tipoServicios) {
		this.tipoServicios = tipoServicios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoReferencia")
	public Set<Referencia> getReferencias() {
		return this.referencias;
	}

	public void setReferencias(Set<Referencia> referencias) {
		this.referencias = referencias;
	}

	@Override
	public boolean equals(Object obj) {
		TipoReferencia comparacion = (TipoReferencia) obj;
		return comparacion.getId().equalsIgnoreCase(id);
	}

	@Override
	public String toString() {
		return "Modelo TipoReferencia, id: " + id;
	}

}