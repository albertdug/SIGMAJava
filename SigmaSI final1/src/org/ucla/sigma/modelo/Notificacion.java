package org.ucla.sigma.modelo;

// Generated 03/06/2012 11:28:14 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Notificacion generated by hbm2java
 */
@Entity
@Table(name = "notificacion", schema = "public")
public class Notificacion implements java.io.Serializable {

	private int id;
	private String nombre;
	private String apellido;
	private String correo;
	private String telefono;
	private String texto;
	private Date creacion;
	private char estatus = 'A';
	private Set<Respuesta> respuestas = new HashSet<Respuesta>(0);

	public Notificacion() {
	}

	public Notificacion(int id, String nombre, String apellido, String correo,
			String texto, Date creacion, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.texto = texto;
		this.creacion = creacion;
		this.estatus = estatus;
	}

	public Notificacion(int id, String nombre, String apellido, String correo,
			String telefono, String texto, Date creacion, char estatus,
			Set<Respuesta> respuestas) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.telefono = telefono;
		this.texto = texto;
		this.creacion = creacion;
		this.estatus = estatus;
		this.respuestas = respuestas;
	}

	@Id
	@SequenceGenerator(name = "SEQ_NOTIFICACION", sequenceName = "seq_notificacion", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTIFICACION")
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

	@Column(name = "apellido", nullable = false)
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Column(name = "correo", nullable = false)
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Column(name = "telefono", length = 12)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "texto", nullable = false, columnDefinition = "text")
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "creacion", nullable = false, length = 13)
	public Date getCreacion() {
		return this.creacion;
	}

	public void setCreacion(Date creacion) {
		this.creacion = creacion;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "notificacion")
	@Fetch(FetchMode.SUBSELECT)
	public Set<Respuesta> getRespuestas() {
		return this.respuestas;
	}

	public void setRespuestas(Set<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	@Override
	public boolean equals(Object obj) {
		Notificacion comparacion = (Notificacion) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Notificacion, id: " + id;
	}

}
