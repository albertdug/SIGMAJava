package org.ucla.sigma.modelo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name = "ganglio", schema = "public")
public class Ganglio implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus = 'A';
	private Set<ConsultaGeneral> consultaGenerals = new HashSet<ConsultaGeneral>(0);
	
	public Ganglio() {
	}

	public Ganglio(int id, char estatus) {
		this.id = id;
		this.estatus = estatus;
	}

	public Ganglio(int id, String nombre, char estatus, Set<ConsultaGeneral> consultaGenerals) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.consultaGenerals = consultaGenerals;
	}

	@Id
	@SequenceGenerator(name = "SEQ_GANGLIO", sequenceName = "seq_ganglio", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GANGLIO")
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "consultageneral_ganglio", schema = "public", joinColumns = { @JoinColumn(name = "ganglioid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "consultageneralid", nullable = false, updatable = false) })
	public Set<ConsultaGeneral> getConsultaGenerals() {
		return consultaGenerals;
	}

	public void setConsultaGenerals(Set<ConsultaGeneral> consultaGenerals) {
		this.consultaGenerals = consultaGenerals;
	}

	@Override
	public boolean equals(Object obj) {
		Ganglio comparacion = (Ganglio) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Ganglio, id: " + id;
	}

}
