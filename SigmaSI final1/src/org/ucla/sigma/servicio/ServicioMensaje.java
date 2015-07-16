package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.MensajeDAO;
import org.ucla.sigma.interfazservicio.IServicioMensaje;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Mensaje;

public class ServicioMensaje implements IServicioMensaje, Serializable {

	private MensajeDAO mensajeDAO;

	public MensajeDAO getMensajeDAO() {
		return mensajeDAO;
	}

	public void setMensajeDAO(MensajeDAO mensajeDAO) {
		this.mensajeDAO = mensajeDAO;
	}

	@Override
	public Mensaje buscarUno(String valor) {
		Mensaje msj=null;
		List orden = new ArrayList();
		List busqueda = new ArrayList();
		orden.add(Restrictions.eq("id", valor));
		busqueda = mensajeDAO.findByCriterions(Mensaje.class, orden);
		if (!busqueda.isEmpty()) {
			msj = (Mensaje) busqueda.get(0);
		} 
		return msj;
	}

}
