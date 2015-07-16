package org.ucla.sigma.servicio;

import java.io.Serializable;
import org.ucla.sigma.dao.DiaDAO;
import org.ucla.sigma.interfazservicio.IServicioDia;

public class ServicioDia implements IServicioDia, Serializable {

	private DiaDAO diaDAO;

	public DiaDAO getDiaDAO() {
		return diaDAO;
	}

	public void setDiaDAO(DiaDAO diaDAO) {
		this.diaDAO = diaDAO;
	}


}
