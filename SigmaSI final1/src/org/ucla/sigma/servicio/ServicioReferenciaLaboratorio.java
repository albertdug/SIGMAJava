package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.ReferenciaLaboratorioDAO;
import org.ucla.sigma.interfazservicio.IServicioReferenciaLaboratorio;
import org.ucla.sigma.modelo.Referencia;

public class ServicioReferenciaLaboratorio implements IServicioReferenciaLaboratorio, Serializable {

	private ReferenciaLaboratorioDAO referenciaLaboratorioDAO;

	public ReferenciaLaboratorioDAO getReferenciaLaboratorioDAO() {
		return referenciaLaboratorioDAO;
	}

	public void setReferenciaLaboratorioDAO(
			ReferenciaLaboratorioDAO referenciaLaboratorioDAO) {
		this.referenciaLaboratorioDAO = referenciaLaboratorioDAO;
	}
	
	

}
