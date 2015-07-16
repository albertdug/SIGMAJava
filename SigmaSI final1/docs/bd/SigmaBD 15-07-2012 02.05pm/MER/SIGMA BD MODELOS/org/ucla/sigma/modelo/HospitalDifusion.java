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
 * HospitalDifusion generated by hbm2java
 */
@Entity
@Table(name = "hospital_difusion", schema = "public")
public class HospitalDifusion implements java.io.Serializable {

	private int id;
	private Difusion difusion;
	private Hospital hospital;
	private String enlace;
	private char estatus;

	public HospitalDifusion() {
	}

	public HospitalDifusion(int id, Difusion difusion, Hospital hospital,
			String enlace, char estatus) {
		this.id = id;
		this.difusion = difusion;
		this.hospital = hospital;
		this.enlace = enlace;
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
	@JoinColumn(name = "difusionid", nullable = false)
	public Difusion getDifusion() {
		return this.difusion;
	}

	public void setDifusion(Difusion difusion) {
		this.difusion = difusion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospitalid", nullable = false)
	public Hospital getHospital() {
		return this.hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@Column(name = "enlace", nullable = false)
	public String getEnlace() {
		return this.enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

}