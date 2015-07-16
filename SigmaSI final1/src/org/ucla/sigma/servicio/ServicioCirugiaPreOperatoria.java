package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CirugiaDAO;
import org.ucla.sigma.dao.CirugiaPreOperatoriaDAO;
import org.ucla.sigma.dao.EntidadExternaDAO;
import org.ucla.sigma.dao.ImagenDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCirugia;
import org.ucla.sigma.interfazservicio.IServicioEntidadExterna;
import org.ucla.sigma.interfazservicio.IServicioImagen;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.interfazservicio.IServicioCirugiaPreOperatoria;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.CirugiaPreOperatoria;
import org.ucla.sigma.modelo.Paciente;

public class ServicioCirugiaPreOperatoria implements IServicioCirugiaPreOperatoria, Serializable {

	private CirugiaPreOperatoriaDAO cirugiaPreOperatoriaDAO;

	public CirugiaPreOperatoriaDAO getCirugiaPreOperatoriaDAO() {
		return cirugiaPreOperatoriaDAO;
	}

	public void setCirugiaPreOperatoriaDAO(
			CirugiaPreOperatoriaDAO cirugiaPreOperatoriaDAO) {
		this.cirugiaPreOperatoriaDAO = cirugiaPreOperatoriaDAO;
	}

	@Override
	public void guardarCirugiaPreOperatoria(CirugiaPreOperatoria obj) {
		cirugiaPreOperatoriaDAO.saveOrUpdate(obj);
		
	}


	
}
