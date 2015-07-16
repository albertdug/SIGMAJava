package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.AdjuntoDAO;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CardiologiaDAO;
import org.ucla.sigma.dao.DiaDeAtencionDAO;
import org.ucla.sigma.dao.HospitalDifusionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.dao.TipoImagenologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioAdjunto;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCardiologia;
import org.ucla.sigma.interfazservicio.IServicioDiaDeAtencion;
import org.ucla.sigma.interfazservicio.IServicioHospitalDifusion;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.interfazservicio.IServicioTipoImagenologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.TipoImagenologia;

public class ServicioTipoImagenologia implements IServicioTipoImagenologia, Serializable {

	private TipoImagenologiaDAO tipoImagenologiaDAO;

	public TipoImagenologiaDAO getTipoImagenologiaDAO() {
		return tipoImagenologiaDAO;
	}

	public void setTipoImagenologiaDAO(TipoImagenologiaDAO tipoImagenologiaDAO) {
		this.tipoImagenologiaDAO = tipoImagenologiaDAO;
	}

	@Override
	public void guardarTipoImagenologia(TipoImagenologia obj) {
		obj.setEstatus('A');
		tipoImagenologiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoImagenologia(TipoImagenologia obj) {
		obj.setEstatus('E');
		tipoImagenologiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioTipoImagenologia.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoImagenologiaDAO.findByCriterions(TipoImagenologia.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor,
				MatchMode.ANYWHERE));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoImagenologiaDAO.findByCriterions(TipoImagenologia.class, restricciones, orden);
	}

	@Override
	public TipoImagenologia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoImagenologiaDAO.findByCriterions(TipoImagenologia.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoImagenologia) busqueda.get(0);
		} else {
			return null;
		}
	}

}
