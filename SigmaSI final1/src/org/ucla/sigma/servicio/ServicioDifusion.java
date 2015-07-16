package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.DifusionDAO;
import org.ucla.sigma.dao.ImagenologiaDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.NotificacionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioDifusion;
import org.ucla.sigma.interfazservicio.IServicioImagenologia;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioNotificacion;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Difusion;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Servicio;

public class ServicioDifusion implements IServicioDifusion, Serializable {

	private DifusionDAO difusionDAO;

	public DifusionDAO getDifusionDAO() {
		return difusionDAO;
	}

	public void setDifusionDAO(DifusionDAO difusionDAO) {
		this.difusionDAO = difusionDAO;
	}

	@Override
	public void guardarDifusion(Difusion obj) {
		obj.setEstatus('A');
		difusionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarDifusion(Difusion obj) {
		obj.setEstatus('E');
		difusionDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioDifusion.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return difusionDAO.findByCriterions(Difusion.class, restricciones, orden);
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
		return difusionDAO.findByCriterions(Difusion.class, restricciones, orden);
	}

	@Override
	public Difusion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = difusionDAO.findByCriterions(Difusion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Difusion) busqueda.get(0);
		} else {
			return null;
		}
	}
}
