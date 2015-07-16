package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.CirugiaProcesarDAO;
import org.ucla.sigma.interfazservicio.IServicioCirugiaProcesar;
import org.ucla.sigma.modelo.CirugiaProcesar;

public class ServicioCirugiaProcesar implements IServicioCirugiaProcesar, Serializable {

	private CirugiaProcesarDAO cirugiaProcesarDAO;

	public CirugiaProcesarDAO getCirugiaProcesarDAO() {
		return cirugiaProcesarDAO;
	}

	public void setCirugiaProcesarDAO(CirugiaProcesarDAO cirugiaProcesarDAO) {
		this.cirugiaProcesarDAO = cirugiaProcesarDAO;
	}

	@Override
	public void guardarCirugiaProcesar(CirugiaProcesar obj) {
		cirugiaProcesarDAO.saveOrUpdate(obj);
		
	}
	
	
	
}
