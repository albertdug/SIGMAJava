package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperMath;
import org.ucla.sigma.dao.BoletinInformativoDAO;
import org.ucla.sigma.dao.BotonDAO;
import org.ucla.sigma.dao.CarnetDAO;
import org.ucla.sigma.dao.DifusionDAO;
import org.ucla.sigma.dao.ImagenologiaDAO;
import org.ucla.sigma.dao.NeurologiaDAO;
import org.ucla.sigma.dao.NotificacionDAO;
import org.ucla.sigma.dao.PatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioBoletinInformativo;
import org.ucla.sigma.interfazservicio.IServicioBoton;
import org.ucla.sigma.interfazservicio.IServicioCarnet;
import org.ucla.sigma.interfazservicio.IServicioDifusion;
import org.ucla.sigma.interfazservicio.IServicioImagenologia;
import org.ucla.sigma.interfazservicio.IServicioNeurologia;
import org.ucla.sigma.interfazservicio.IServicioNotificacion;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.interfazservicio.IServicioPatologia;
import org.ucla.sigma.modelo.BoletinInformativo;
import org.ucla.sigma.modelo.Boton;
import org.ucla.sigma.modelo.Paciente;


public class ServicioBoletinInformativo implements IServicioBoletinInformativo, Serializable {

	private BoletinInformativoDAO boletinInformativoDAO;

	public BoletinInformativoDAO getBoletinInformativoDAO() {
		return boletinInformativoDAO;
	}

	public void setBoletinInformativoDAO(BoletinInformativoDAO boletinInformativoDAO) {
		this.boletinInformativoDAO = boletinInformativoDAO;
	}
	@Override
	public void guardarBoletinInformativo(BoletinInformativo obj) {
		obj.setEstatus('A');
		boletinInformativoDAO.saveOrUpdate(obj);
	}

	@Override
	public BoletinInformativo getRand(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}

		restricciones.add(Restrictions.eq("si", true));
		restricciones.add(Restrictions.ge("envio", HelperDate.now()));
		List busqueda = boletinInformativoDAO.findByCriterions(BoletinInformativo.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (BoletinInformativo) busqueda.get(HelperMath.aleatorio(0,
					busqueda.size()-1));
		} else {
			return null;
		}
	}
	
	@Override
	public BoletinInformativo buscarUnoPorId(int id, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", id));
		List busqueda = boletinInformativoDAO.findByCriterions(BoletinInformativo.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (BoletinInformativo) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void borrarBoletinInformativo(BoletinInformativo obj) {
		obj.setEstatus('E');
		boletinInformativoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioBoletinInformativo.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("titulo"));
		return boletinInformativoDAO.findByCriterions(BoletinInformativo.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("titulo", valor,
				MatchMode.ANYWHERE));
		List orden = new ArrayList();
		orden.add(Order.asc("titulo"));
		return boletinInformativoDAO.findByCriterions(BoletinInformativo.class, restricciones, orden);
	}

	@Override
	public BoletinInformativo buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("titulo", valor));
		List busqueda = boletinInformativoDAO.findByCriterions(BoletinInformativo.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (BoletinInformativo) busqueda.get(0);
		} else {
			return null;
		}
	}

}
