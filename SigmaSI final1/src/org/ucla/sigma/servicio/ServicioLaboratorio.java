package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.LaboratorioDAO;
import org.ucla.sigma.dao.OftalmologiaDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioLaboratorio;
import org.ucla.sigma.interfazservicio.IServicioOftalmologia;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;


public class ServicioLaboratorio implements IServicioLaboratorio, Serializable {

	private LaboratorioDAO laboratorioDAO;

	public LaboratorioDAO getLaboratorioDAO() {
		return laboratorioDAO;
	}

	public void setLaboratorioDAO(LaboratorioDAO laboratorioDAO) {
		this.laboratorioDAO = laboratorioDAO;
	}


	
}
