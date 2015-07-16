package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.ReferenciaCirugiaDAO;
import org.ucla.sigma.interfazservicio.IServicioReferenciaCirugia;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.ReferenciaCirugia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;

public class ServicioReferenciaCirugia implements IServicioReferenciaCirugia, Serializable {

	private ReferenciaCirugiaDAO referenciaCirugiaDAO;

	public ReferenciaCirugiaDAO getReferenciaCirugiaDAO() {
		return referenciaCirugiaDAO;
	}

	public void setReferenciaCirugiaDAO(ReferenciaCirugiaDAO referenciaCirugiaDAO) {
		this.referenciaCirugiaDAO = referenciaCirugiaDAO;
	}
	
	@Override
	public void guardarReferenciaCirugia(ReferenciaCirugia obj) {
		obj.setEstatus('R');
		referenciaCirugiaDAO.saveOrUpdate(obj);
	}
	

}
