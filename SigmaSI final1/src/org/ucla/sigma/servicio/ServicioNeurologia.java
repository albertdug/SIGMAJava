package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Neurologia;

public class ServicioNeurologia implements IServicioNeurologia, Serializable {

	private NeurologiaDAO neurologiaDAO;

	public NeurologiaDAO getNeurologiaDAO() {
		return neurologiaDAO;
	}

	public void setNeurologiaDAO(NeurologiaDAO neurologiaDAO) {
		this.neurologiaDAO = neurologiaDAO;
	}
	
	@Override
	public void guardarNeurologia(Neurologia obj) {
		neurologiaDAO.saveOrUpdate(obj);
		
	}
	
}
