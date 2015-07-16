package org.ucla.sigma.modelo;

// Generated 03/06/2012 11:28:14 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * Boton generated by hbm2java
 */
@Entity
@Table(name = "boton", schema = "public")
public class Boton implements java.io.Serializable {

	private String id;
	private String label;
	private int valor;
	private Set<TipoMensaje> tipoMensajes = new HashSet<TipoMensaje>(0);

	public Boton() {
	}

	public Boton(String id, String label, int valor) {
		this.id = id;
		this.label = label;
		this.valor = valor;
	}

	public Boton(String id, String label, int valor,
			Set<TipoMensaje> tipoMensajes) {
		this.id = id;
		this.label = label;
		this.valor = valor;
		this.tipoMensajes = tipoMensajes;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BOTON", sequenceName = "seq_boton", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOTON")
	@Column(name = "id", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "label", nullable = false)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "valor", nullable = false)
	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tipo_mensaje_boton", schema = "public", joinColumns = { @JoinColumn(name = "botonid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "tipo_mensajeid", nullable = false, updatable = false) })
	public Set<TipoMensaje> getTipoMensajes() {
		return this.tipoMensajes;
	}

	public void setTipoMensajes(Set<TipoMensaje> tipoMensajes) {
		this.tipoMensajes = tipoMensajes;
	}

	@Override
	public boolean equals(Object obj) {
		Boton comparacion = (Boton) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Boton, id: " + id;
	}

}
