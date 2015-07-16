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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "frecuencia", schema = "public")
public class Frecuencia implements java.io.Serializable {

	private int id;
	private String nombre;
	private FrecuenciaTipo frecuenciaTipo;
	private char estatus = 'A';
	private Set<ConsultaGeneral> consultaGenerals = new HashSet<ConsultaGeneral>(0);
	private Set<Dermatologia> dermatologiasRasca = new HashSet<Dermatologia>(0);
	private Set<Dermatologia> dermatologiasBano = new HashSet<Dermatologia>(0);
	
	public Frecuencia() {
	}

	public Frecuencia(int id, char estatus) {
		this.id = id;
		this.estatus = estatus;
	}

	public Frecuencia(int id, String nombre, char estatus, Set<ConsultaGeneral> consultaGenerals) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.consultaGenerals = consultaGenerals;
	}

	@Id
	@SequenceGenerator(name = "SEQ_FRECUENCIA", sequenceName = "seq_frecuencia", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FRECUENCIA")
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "frecuenciatipoid", nullable = false)
	public FrecuenciaTipo getFrecuenciaTipo() {
		return frecuenciaTipo;
	}

	public void setFrecuenciaTipo(FrecuenciaTipo frecuenciaTipo) {
		this.frecuenciaTipo = frecuenciaTipo;
	}
	
	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return this.estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frecuenciaAlimento")
	public Set<ConsultaGeneral> getConsultaGenerals() {
		return this.consultaGenerals;
	}

	public void setConsultaGenerals(Set<ConsultaGeneral> consultaGenerals) {
		this.consultaGenerals = consultaGenerals;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frecuenciaRasca")
	public Set<Dermatologia> getDermatologiasRasca() {
		return dermatologiasRasca;
	}

	public void setDermatologiasRasca(Set<Dermatologia> dermatologiasRasca) {
		this.dermatologiasRasca = dermatologiasRasca;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frecuenciaBano")
	public Set<Dermatologia> getDermatologiasBano() {
		return dermatologiasBano;
	}

	public void setDermatologiasBano(Set<Dermatologia> dermatologiasBano) {
		this.dermatologiasBano = dermatologiasBano;
	}
	
	@Override
	public boolean equals(Object obj) {
		Frecuencia comparacion = (Frecuencia) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Frecuencia, id: " + id;
	}

}
