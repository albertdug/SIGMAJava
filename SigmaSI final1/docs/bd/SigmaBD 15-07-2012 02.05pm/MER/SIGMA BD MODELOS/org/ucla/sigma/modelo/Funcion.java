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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Funcion generated by hbm2java
 */
@Entity
@Table(name = "funcion", schema = "public")
public class Funcion implements java.io.Serializable {

	private String id;
	private Funcion funcion;
	private String nombre;
	private int orden;
	private String url;
	private boolean needVeterinario;
	private char estatus;
	private Set<Funcion> funcions = new HashSet<Funcion>(0);
	private Set<Perfil> perfils = new HashSet<Perfil>(0);

	public Funcion() {
	}

	public Funcion(String id, String nombre, int orden,
			boolean needVeterinario, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.orden = orden;
		this.needVeterinario = needVeterinario;
		this.estatus = estatus;
	}

	public Funcion(String id, Funcion funcion, String nombre, int orden,
			String url, boolean needVeterinario, char estatus,
			Set<Funcion> funcions, Set<Perfil> perfils) {
		this.id = id;
		this.funcion = funcion;
		this.nombre = nombre;
		this.orden = orden;
		this.url = url;
		this.needVeterinario = needVeterinario;
		this.estatus = estatus;
		this.funcions = funcions;
		this.perfils = perfils;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 5)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionid")
	public Funcion getFuncion() {
		return this.funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "need_veterinario", nullable = false)
	public boolean isNeedVeterinario() {
		return this.needVeterinario;
	}

	public void setNeedVeterinario(boolean needVeterinario) {
		this.needVeterinario = needVeterinario;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcion")
	public Set<Funcion> getFuncions() {
		return this.funcions;
	}

	public void setFuncions(Set<Funcion> funcions) {
		this.funcions = funcions;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "perfil_funcion", schema = "public", joinColumns = { @JoinColumn(name = "funcionid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "perfilid", nullable = false, updatable = false) })
	public Set<Perfil> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(Set<Perfil> perfils) {
		this.perfils = perfils;
	}

}
