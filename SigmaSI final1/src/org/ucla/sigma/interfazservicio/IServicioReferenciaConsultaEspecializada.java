package org.ucla.sigma.interfazservicio;

import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;

public interface IServicioReferenciaConsultaEspecializada {

	public void guardarReferenciaConsultaEspecializada(
			ReferenciaConsultaEspecializada obj);

	public int countDisponibilidad(String fechaCita, String tipoServicio);

}
