package org.ucla.sigma.modelo;

// Generated 09/06/2012 11:00:56 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * Carnet generated by hbm2java
 */
@Entity
@Table(name = "carnet", schema = "public")
public class Carnet implements java.io.Serializable {

	private int id;
	private Paciente paciente;
	private Date expedicion;
	private Date vencimiento;
	private char estatus = 'A';

	public Carnet() {
	}

	public Carnet(int id, Paciente paciente, Date expedicion, Date vencimiento,
			char estatus) {
		this.id = id;
		this.paciente = paciente;
		this.expedicion = expedicion;
		this.vencimiento = vencimiento;
		this.estatus = estatus;
	}

	@Id
	@SequenceGenerator(name = "SEQ_CARNET", sequenceName = "seq_carnet", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARNET")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pacientehistoria_medica", nullable = false)
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expedicion", nullable = false, length = 13)
	public Date getExpedicion() {
		return this.expedicion;
	}

	public void setExpedicion(Date expedicion) {
		this.expedicion = expedicion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "vencimiento", nullable = false, length = 13)
	public Date getVencimiento() {
		return this.vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@Override
	public boolean equals(Object obj) {
		Carnet comparacion = (Carnet) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Carnet, id: " + id;
	}

}