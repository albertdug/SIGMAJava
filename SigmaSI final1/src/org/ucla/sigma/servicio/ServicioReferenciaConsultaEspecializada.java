package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.ReferenciaConsultaEspecializadaDAO;
import org.ucla.sigma.interfazservicio.IServicioReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ConsultaGeneral;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;

public class ServicioReferenciaConsultaEspecializada implements IServicioReferenciaConsultaEspecializada, Serializable {

	private ReferenciaConsultaEspecializadaDAO referenciaConsultaEspecializadaDAO;

	public ReferenciaConsultaEspecializadaDAO getReferenciaConsultaEspecializadaDAO() {
		return referenciaConsultaEspecializadaDAO;
	}

	public void setReferenciaConsultaEspecializadaDAO(
			ReferenciaConsultaEspecializadaDAO referenciaConsultaEspecializadaDAO) {
		this.referenciaConsultaEspecializadaDAO = referenciaConsultaEspecializadaDAO;
	}
	
	@Override
	public void guardarReferenciaConsultaEspecializada(ReferenciaConsultaEspecializada obj) {
		obj.setEstatus('R');
		referenciaConsultaEspecializadaDAO.saveOrUpdate(obj);
	}

	@Override
	public int countDisponibilidad(String fechaCita, String tipoServicio) {
		// TODO Auto-generated method stub
		return 0;
	}

}
