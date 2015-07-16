package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TipoEntidadExterna generated by hbm2java
 */
@Entity
@Table(name = "tipo_entidad_externa", schema = "public")
public class TipoEntidadExterna implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus;
	private Set<EntidadExterna> entidadExternas = new HashSet<EntidadExterna>(0);

	public TipoEntidadExterna() {
	}

	public TipoEntidadExterna(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public TipoEntidadExterna(int id, String nombre, char estatus,
			Set<EntidadExterna> entidadExternas) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.entidadExternas = entidadExternas;
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

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoEntidadExterna")
	public Set<EntidadExterna> getEntidadExternas() {
		return this.entidadExternas;
	}

	public void setEntidadExternas(Set<EntidadExterna> entidadExternas) {
		this.entidadExternas = entidadExternas;
	}

}
