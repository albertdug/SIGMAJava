package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.UniqueConstraint;

/**
 * TipoPublicacion generated by hbm2java
 */
@Entity
@Table(name = "tipo_publicacion", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "uri"))
public class TipoPublicacion implements java.io.Serializable {

	private int id;
	private String nombre;
	private String uri;
	private String descripcion;
	private char estatus;
	private Set<Suscripcion> suscripcions = new HashSet<Suscripcion>(0);
	private Set<Publicacion> publicacions = new HashSet<Publicacion>(0);

	public TipoPublicacion() {
	}

	public TipoPublicacion(int id, String nombre, String uri, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.uri = uri;
		this.estatus = estatus;
	}

	public TipoPublicacion(int id, String nombre, String uri,
			String descripcion, char estatus, Set<Suscripcion> suscripcions,
			Set<Publicacion> publicacions) {
		this.id = id;
		this.nombre = nombre;
		this.uri = uri;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.suscripcions = suscripcions;
		this.publicacions = publicacions;
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

	@Column(name = "uri", unique = true, nullable = false)
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "suscripcion_tipo_publicacion", schema = "public", joinColumns = { @JoinColumn(name = "tipo_publicacionid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "suscripcionid", nullable = false, updatable = false) })
	public Set<Suscripcion> getSuscripcions() {
		return this.suscripcions;
	}

	public void setSuscripcions(Set<Suscripcion> suscripcions) {
		this.suscripcions = suscripcions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoPublicacion")
	public Set<Publicacion> getPublicacions() {
		return this.publicacions;
	}

	public void setPublicacions(Set<Publicacion> publicacions) {
		this.publicacions = publicacions;
	}

}