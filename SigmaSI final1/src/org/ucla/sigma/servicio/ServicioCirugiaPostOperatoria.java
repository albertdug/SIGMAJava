package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CirugiaDAO;
import org.ucla.sigma.dao.CirugiaPostOperatoriaDAO;
import org.ucla.sigma.dao.EntidadExternaDAO;
import org.ucla.sigma.dao.ImagenDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCirugia;
import org.ucla.sigma.interfazservicio.IServicioCirugiaPostOperatoria;
import org.ucla.sigma.interfazservicio.IServicioEntidadExterna;
import org.ucla.sigma.interfazservicio.IServicioImagen;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.CirugiaPostOperatoria;
import org.ucla.sigma.modelo.Paciente;

public class ServicioCirugiaPostOperatoria implements IServicioCirugiaPostOperatoria, Serializable {

	private CirugiaPostOperatoriaDAO cirugiaPostOperatoriaDAO;

	public CirugiaPostOperatoriaDAO getCirugiaPostOperatoriaDAO() {
		return cirugiaPostOperatoriaDAO;
	}

	public void setCirugiaPostOperatoriaDAO(
			CirugiaPostOperatoriaDAO cirugiaPostOperatoriaDAO) {
		this.cirugiaPostOperatoriaDAO = cirugiaPostOperatoriaDAO;
	}

	@Override
	public void guardarCirugiaPostOperatoria(CirugiaPostOperatoria obj) {
		cirugiaPostOperatoriaDAO.saveOrUpdate(obj);
	}

	
	
}
