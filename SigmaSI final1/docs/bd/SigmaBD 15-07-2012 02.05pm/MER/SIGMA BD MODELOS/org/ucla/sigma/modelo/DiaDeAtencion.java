package org.ucla.sigma.modelo;

// Generated 15/07/2012 02:32:55 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DiaDeAtencion generated by hbm2java
 */
@Entity
@Table(name = "dia_de_atencion", schema = "public")
public class DiaDeAtencion implements java.io.Serializable {

	private int id;
	private Dia dia;
	private TipoServicio tipoServicio;
	private int maxCitas;
	private char estatus;

	public DiaDeAtencion() {
	}

	public DiaDeAtencion(int id, Dia dia, TipoServicio tipoServicio,
			int maxCitas, char estatus) {
		this.id = id;
		this.dia = dia;
		this.tipoServicio = tipoServicio;
		this.maxCitas = maxCitas;
		this.estatus = estatus;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diaid", nullable = false)
	public Dia getDia() {
		return this.dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_servicioid", nullable = false)
	public TipoServicio getTipoServicio() {
		return this.tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	@Column(name = "max_citas", nullable = false)
	public int getMaxCitas() {
		return this.maxCitas;
	}

	public void setMaxCitas(int maxCitas) {
		this.maxCitas = maxCitas;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

}