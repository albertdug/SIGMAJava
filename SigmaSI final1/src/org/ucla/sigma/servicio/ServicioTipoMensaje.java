package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoMensajeDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoMensaje;

public class ServicioTipoMensaje implements IServicioTipoMensaje, Serializable {

	private TipoMensajeDAO tipoMensajeDAO;

	public TipoMensajeDAO getTipoMensajeDAO() {
		return tipoMensajeDAO;
	}

	public void setTipoMensajeDAO(TipoMensajeDAO tipoMensajeDAO) {
		this.tipoMensajeDAO = tipoMensajeDAO;
	}

	@Override
	public List buscar(String valor) {
		List orden = new ArrayList();
		orden.add(Restrictions.eq("id", valor));
		return tipoMensajeDAO.findByCriterions(TipoMensajeDAO.class, orden);
	}

}
