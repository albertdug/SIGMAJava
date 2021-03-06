package org.ucla.sigma.modelo;

// Generated 09/06/2012 11:00:56 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ReferenciaConsultaEspecializada generated by hbm2java
 */
@Entity
@Table(name = "referencia_consulta_especializada", schema = "public")
public class ReferenciaConsultaEspecializada extends Referencia implements
		java.io.Serializable {

	public ReferenciaConsultaEspecializada() {
		super.setTipoReferencia(new TipoReferencia("RCE"));
	}

	public ReferenciaConsultaEspecializada(Referencia referencia) {
		super.setTipoReferencia(new TipoReferencia("RCE"));
	}

	public ReferenciaConsultaEspecializada(int id, Historial historial,
			TipoReferencia tipoReferencia, TipoServicio tipoServicio,
			Date fechaExpedicion) {
		super.setId(id);
		super.setHistorial(historial);
		super.setTipoReferencia(tipoReferencia);
		super.setTipoServicio(tipoServicio);
		super.setFechaExpedicion(fechaExpedicion);
		super.setTipoReferencia(new TipoReferencia("RCE"));
	}

	@Override
	public String toString() {
		return "Modelo ReferenciaConsultaEspecializada, id: " + super.getId();
	}

}
