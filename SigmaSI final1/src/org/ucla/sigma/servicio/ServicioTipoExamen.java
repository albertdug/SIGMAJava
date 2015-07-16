package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoExamenDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoExamen;
import org.ucla.sigma.modelo.TipoExamen;

public class ServicioTipoExamen implements IServicioTipoExamen, Serializable {

	private TipoExamenDAO tipoExamenDAO;

	public TipoExamenDAO getTipoExamenDAO() {
		return tipoExamenDAO;
	}

	public void setTipoExamenDAO(TipoExamenDAO tipoExamenDAO) {
		this.tipoExamenDAO = tipoExamenDAO;
	}
	
	@Override
	public void guardarTipoExamen(TipoExamen obj) {
		obj.setEstatus('A');
		tipoExamenDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoExamen(TipoExamen obj) {
		obj.setEstatus('E');
		tipoExamenDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioTipoExamen.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoExamenDAO.findByCriterions(TipoExamen.class, restricciones, orden);
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
		return tipoExamenDAO.findByCriterions(TipoExamen.class, restricciones, orden);
	}

	@Override
	public TipoExamen buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoExamenDAO.findByCriterions(TipoExamen.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoExamen) busqueda.get(0);
		} else {
			return null;
		}
	}

}

