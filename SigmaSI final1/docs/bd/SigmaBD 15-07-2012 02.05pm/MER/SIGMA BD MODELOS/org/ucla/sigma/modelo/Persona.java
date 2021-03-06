package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Persona generated by hbm2java
 */
@Entity
@Table(name = "persona", schema = "public")
public class Persona implements java.io.Serializable {

	private int cedula;
	private Ciudad ciudad;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	private String correo;
	private char estatus;
	private Veterinario veterinario;
	private Usuario usuario;
	private Responsable responsable;
	private Recepcionista recepcionista;

	public Persona() {
	}

	public Persona(int cedula, Ciudad ciudad, String nombre, String apellido,
			char estatus) {
		this.cedula = cedula;
		this.ciudad = ciudad;
		this.nombre = nombre;
		this.apellido = apellido;
		this.estatus = estatus;
	}

	public Persona(int cedula, Ciudad ciudad, String nombre, String apellido,
			String direccion, String telefono, String correo, char estatus,
			Veterinario veterinario, Usuario usuario, Responsable responsable,
			Recepcionista recepcionista) {
		this.cedula = cedula;
		this.ciudad = ciudad;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
		this.estatus = estatus;
		this.veterinario = veterinario;
		this.usuario = usuario;
		this.responsable = responsable;
		this.recepcionista = recepcionista;
	}

	@Id
	@Column(name = "cedula", unique = true, nullable = false)
	public int getCedula() {
		return this.cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ciudadid", nullable = false)
	public Ciudad getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido", nullable = false)
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Column(name = "direccion")
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", length = 12)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "correo")
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "persona")
	public Veterinario getVeterinario() {
		return this.veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "persona")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "persona")
	public Responsable getResponsable() {
		return this.responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "persona")
	public Recepcionista getRecepcionista() {
		return this.recepcionista;
	}

	public void setRecepcionista(Recepcionista recepcionista) {
		this.recepcionista = recepcionista;
	}

}
