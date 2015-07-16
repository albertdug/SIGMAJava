package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoPatologiaDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoPatologia;
import org.ucla.sigma.modelo.TipoPatologia;

public class ServicioTipoPatologia implements IServicioTipoPatologia, Serializable {

	private TipoPatologiaDAO tipoPatologiaDAO;

	public TipoPatologiaDAO getTipoPatologiaDAO() {
		return tipoPatologiaDAO;
	}

	public void setTipoPatologiaDAO(TipoPatologiaDAO tipoPatologiaDAO) {
		this.tipoPatologiaDAO = tipoPatologiaDAO;
	}
	
	@Override
	public void guardarTipoPatologia(TipoPatologia obj) {
		obj.setEstatus('A');
		tipoPatologiaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoPatologia(TipoPatologia obj) {
		obj.setEstatus('E');
		tipoPatologiaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioTipoPatologia.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoPatologiaDAO.findByCriterions(TipoPatologia.class, restricciones, orden);
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
		return tipoPatologiaDAO.findByCriterions(TipoPatologia.class, restricciones, orden);
	}

	@Override
	public TipoPatologia buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoPatologiaDAO.findByCriterions(TipoPatologia.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoPatologia) busqueda.get(0);
		} else {
			return null;
		}
	}

	
	

}
