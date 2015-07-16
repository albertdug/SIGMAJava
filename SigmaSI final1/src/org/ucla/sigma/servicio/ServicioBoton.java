package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.modelo.Boton;

public class ServicioBoton implements IServicioBoton, Serializable {

	private BotonDAO botonDAO;

	public BotonDAO getBotonDAO() {
		return botonDAO;
	}

	public void setBotonDAO(BotonDAO botonDAO) {
		this.botonDAO = botonDAO;
	}

	@Override
	public List buscar(String valor) {
		List orden = new ArrayList();
		orden.add(Restrictions.eq("id", valor));
		return botonDAO.findByCriterions(Boton.class, orden);
	}

}
