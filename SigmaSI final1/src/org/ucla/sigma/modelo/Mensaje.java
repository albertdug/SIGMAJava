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
 * Mensaje generated by hbm2java
 */
@Entity
@Table(name = "mensaje", schema = "public")
public class Mensaje implements java.io.Serializable {

	private String id;
	private TipoMensaje tipoMensaje;
	private String texto;

	public Mensaje() {
	}

	public Mensaje(String id, TipoMensaje tipoMensaje, String texto) {
		this.id = id;
		this.tipoMensaje = tipoMensaje;
		this.texto = texto;
	}

	@Id
	@SequenceGenerator(name = "SEQ_MENSAJE", sequenceName = "seq_mensaje", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MENSAJE")
	@Column(name = "id", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_mensajeid", nullable = false)
	public TipoMensaje getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	@Column(name = "texto", nullable = false)
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public boolean equals(Object obj) {
		Mensaje comparacion = (Mensaje) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Mensaje, id: " + id;
	}

}
