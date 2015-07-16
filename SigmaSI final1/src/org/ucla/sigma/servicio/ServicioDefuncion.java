package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.DefuncionDAO;
import org.ucla.sigma.dao.DermatologiaDAO;
import org.ucla.sigma.dao.HospitalDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioDefuncion;
import org.ucla.sigma.interfazservicio.IServicioDermatologia;
import org.ucla.sigma.interfazservicio.IServicioHospital;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Defuncion;

public class ServicioDefuncion implements IServicioDefuncion, Serializable {

	private DefuncionDAO defuncionDAO;

	public DefuncionDAO getDefuncionDAO() {
		return defuncionDAO;
	}

	public void setDefuncionDAO(DefuncionDAO defuncionDAO) {
		this.defuncionDAO = defuncionDAO;
	}

	@Override
	public void guardarDefuncion(Defuncion obj) {
		defuncionDAO.saveOrUpdate(obj);
		
	}

	
}
