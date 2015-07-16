package org.ucla.sigma.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tipoalimentacion", schema = "public")
public class TipoAlimentacion implements java.io.Serializable {
	
	private int id;
	private String nombre;
	private String descripcion;
	private char estatus = 'A';
	private Set<ConsultaGeneral> consultaGenerals = new HashSet<ConsultaGeneral>(0);
	
	public TipoAlimentacion() {
		
	}

	public TipoAlimentacion(int id, char estatus) {
		this.id = id;
		this.estatus = estatus;
	}
	
	public TipoAlimentacion(int id, String nombre, String descripcion,
			char estatus, Set<ConsultaGeneral> consultaGenerals) {
	
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.consultaGenerals = consultaGenerals;
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_TIPOALIMENTACION", sequenceName = "seq_tipoalimentacion", allocationSize = 1, schema = "public")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPOALIMENTACION")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "estatus", nullable = false, length = 1)
	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoAlimentacion")
	public Set<ConsultaGeneral> getConsultaGenerals() {
		return consultaGenerals;
	}

	public void setConsultaGenerals(Set<ConsultaGeneral> consultaGenerals) {
		this.consultaGenerals = consultaGenerals;
	}

	@Override
	public boolean equals(Object obj) {
		TipoAlimentacion comparacion = (TipoAlimentacion) obj;
		return comparacion.getId() == id;
	}

	@Override
	public String toString() {
		return "Modelo TipoAlimentacion, id: " + id;
	}
}
