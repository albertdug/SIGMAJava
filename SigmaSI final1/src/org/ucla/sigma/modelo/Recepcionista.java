package org.ucla.sigma.modelo;

// Generated 04/06/2012 02:14:15 AM by Hibernate Tools 3.4.0.CR1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Recepcionista generated by hbm2java
 */
@Entity
@Table(name = "recepcionista", schema = "public")
public class Recepcionista implements java.io.Serializable {

	
	private int cedula;
	private Persona persona;
	private char estatus = 'A';

	public Recepcionista() {
	}

	public Recepcionista(Persona persona, char estatus) {
		this.persona = persona;
		this.estatus = estatus;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "persona"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "cedula", unique = true, nullable = false)
	public int getCedula() {
		return this.cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
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
		Recepcionista comparacion = (Recepcionista) obj;
		return comparacion.getCedula() == cedula;
	}
	
	@Override
	public String toString() {
		return "Modelo Recepcionista, cedula: " + cedula;
	}

}
