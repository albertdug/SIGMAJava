package org.ucla.sigma.modelo;

// Generated 09/06/2012 02:25:11 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Defuncion generated by hbm2java
 */
@Entity
@Table(name = "defuncion", schema = "public")
public class Defuncion extends Historial implements java.io.Serializable {

	private TipoDefuncion tipoDefuncion;
	private String comentarios;

	public Defuncion() {
		super.setTipoServicio(new TipoServicio("DEF"));
	}

	public Defuncion(TipoDefuncion tipoDefuncion) {
		this.tipoDefuncion = tipoDefuncion;
		super.setTipoServicio(new TipoServicio("DEF"));
	}

	public Defuncion(int id, Paciente paciente, Veterinario veterinario,
			Date fecha, Date hora, TipoDefuncion tipoDefuncion) {
		super.setId(id);
		super.setPaciente(paciente);
		super.setVeterinario(veterinario);
		super.setFecha(fecha);
		super.setHora(hora);
		this.tipoDefuncion = tipoDefuncion;
		super.setTipoServicio(new TipoServicio("DEF"));
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_defuncionid", nullable = false)
	public TipoDefuncion getTipoDefuncion() {
		return this.tipoDefuncion;
	}

	public void setTipoDefuncion(TipoDefuncion tipoDefuncion) {
		this.tipoDefuncion = tipoDefuncion;
	}
	
	@Column(name = "comentarios", nullable = false)
	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "Modelo Defuncion, id: " + super.getId();
	}

}
