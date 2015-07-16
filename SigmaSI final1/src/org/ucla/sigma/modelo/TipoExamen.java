package org.ucla.sigma.modelo;

// Generated 09/06/2012 03:00:53 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * TipoExamen generated by hbm2java
 */
@Entity
@Table(name = "tipo_examen", schema = "public")
public class TipoExamen implements java.io.Serializable {

	private int id;
	private String nombre;
	private char estatus ='A';
	private Set<Examen> examenes = new HashSet<Examen>(0);

	public TipoExamen() {
	}

	public TipoExamen(int id, String nombre, char estatus) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public TipoExamen(int id, String nombre, char estatus,
			Set<Examen> examenes) {
		this.id = id;
		this.nombre = nombre;
		this.estatus = estatus;
		this.examenes = examenes;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TIPO_EXAMEN", sequenceName = "seq_tipo_examen", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_EXAMEN")
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoExamen")
	public Set<Examen> getExamenes() {
		return this.examenes;
	}

	public void setExamenes(Set<Examen> examenes) {
		this.examenes = examenes;
	}
	
	@Override
	public boolean equals(Object obj) {
		TipoExamen comparacion = (TipoExamen) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo TipoExamen, id: " + id;
	}

}
