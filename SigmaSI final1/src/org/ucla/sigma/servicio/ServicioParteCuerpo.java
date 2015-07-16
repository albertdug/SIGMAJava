package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ParteCuerpoDAO;
import org.ucla.sigma.interfazservicio.IServicioParteCuerpo;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.ParteCuerpo;

public class ServicioParteCuerpo implements IServicioParteCuerpo, Serializable {

	private ParteCuerpoDAO partecuerpoDAO;

	public ParteCuerpoDAO getParteCuerpoDAO() {
		return partecuerpoDAO;
	}

	public void setParteCuerpoDAO(ParteCuerpoDAO partecuerpoDAO) {
		this.partecuerpoDAO = partecuerpoDAO;
	}

	@Override
	public void guardarParteCuerpo(ParteCuerpo obj) {
		obj.setEstatus('A');
		partecuerpoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarParteCuerpo(ParteCuerpo obj) {
		obj.setEstatus('E');
		partecuerpoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioCiudad.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return partecuerpoDAO.findByCriterions(ParteCuerpo.class, restricciones, orden);
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
		return partecuerpoDAO.findByCriterions(ParteCuerpo.class, restricciones, orden);
	}

	@Override
	public ParteCuerpo buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = partecuerpoDAO.findByCriterions(ParteCuerpo.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (ParteCuerpo) busqueda.get(0);
		} else {
			return null;
		}
	}

}
