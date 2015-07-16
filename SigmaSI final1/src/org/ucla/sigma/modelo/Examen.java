package org.ucla.sigma.modelo;

// Generated 09/06/2012 03:00:53 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;


@Entity
@Table(name = "examen", schema = "public")
public class Examen implements java.io.Serializable {

	private int id;
	private TipoExamen tipoExamen;
	private String nombre;
	private char estatus = 'A';
	private Set<Historial> historials = new HashSet<Historial>(0);

	public Examen() {
	}

	public Examen(int id, TipoExamen tipoExamen, String nombre,
			char estatus) {
		this.id = id;
		this.tipoExamen = tipoExamen;
		this.nombre = nombre;
		this.estatus = estatus;
	}

	public Examen(int id, TipoExamen tipoExamen, String nombre,
			char estatus, Set<Historial> historials) {
		this.id = id;
		this.tipoExamen = tipoExamen;
		this.nombre = nombre;
		this.estatus = estatus;
		this.historials = historials;
	}

	@Id
	@SequenceGenerator(name = "SEQ_EXAMEN", sequenceName = "seq_examen", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXAMEN")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_examenid", nullable = false)
	public TipoExamen getTipoExamen() {
		return this.tipoExamen;
	}

	public void setTipoExamen(TipoExamen tipoExamen) {
		this.tipoExamen = tipoExamen;
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
	@JoinTable(name = "historial_examen", schema = "public", joinColumns = { @JoinColumn(name = "examenid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "historialid", nullable = false, updatable = false) })
	public Set<Historial> getHistorials() {
		return this.historials;
	}

	public void setHistorials(Set<Historial> historials) {
		this.historials = historials;
	}

	@Override
	public boolean equals(Object obj) {
		Examen comparacion = (Examen) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo Examen, id: " + id;
	}

}

