package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.TraumatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioTraumatologia;

public class ServicioTraumatologia implements IServicioTraumatologia, Serializable {

	private TraumatologiaDAO traumatologiaDAO;

	public TraumatologiaDAO getTraumatologiaDAO() {
		return traumatologiaDAO;
	}

	public void setTraumatologiaDAO(TraumatologiaDAO traumatologiaDAO) {
		this.traumatologiaDAO = traumatologiaDAO;
	}
	

}
