package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.ReferenciaImagenologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioReferenciaImagenologia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaImagenologia;

public class ServicioReferenciaImagenologia implements IServicioReferenciaImagenologia, Serializable {


	private ReferenciaImagenologiaDAO referenciaImagenologiaDAO;

	public ReferenciaImagenologiaDAO getReferenciaImagenologiaDAO() {
		return referenciaImagenologiaDAO;
	}

	public void setReferenciaImagenologiaDAO(
			ReferenciaImagenologiaDAO referenciaImagenologiaDAO) {
		this.referenciaImagenologiaDAO = referenciaImagenologiaDAO;
	}

	@Override
	public void guardarReferenciaImagenologia(ReferenciaImagenologia obj) {
		obj.setEstatus('R');
		referenciaImagenologiaDAO.saveOrUpdate(obj);
	}
	
}
