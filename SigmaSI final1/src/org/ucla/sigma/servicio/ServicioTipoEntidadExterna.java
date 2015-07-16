package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoEntidadExternaDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoEntidadExterna;
import org.ucla.sigma.modelo.TipoEntidadExterna;

public class ServicioTipoEntidadExterna implements IServicioTipoEntidadExterna, Serializable {

	private TipoEntidadExternaDAO tipoEntidadExternaDAO;

	public TipoEntidadExternaDAO getTipoEntidadExternaDAO() {
		return tipoEntidadExternaDAO;
	}

	public void setTipoEntidadExternaDAO(TipoEntidadExternaDAO tipoEntidadExternaDAO) {
		this.tipoEntidadExternaDAO = tipoEntidadExternaDAO;
	}

	@Override
	public void guardarTipoEntidadExterna(TipoEntidadExterna obj) {
		obj.setEstatus('A');
		tipoEntidadExternaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoEntidadExterna(TipoEntidadExterna obj) {
		obj.setEstatus('E');
		tipoEntidadExternaDAO.saveOrUpdate(obj);
	}

	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List<Order> orden = new ArrayList<Order>();
		orden.add(Order.asc("nombre"));
		return tipoEntidadExternaDAO.findByCriterions(TipoEntidadExterna.class, restricciones, orden);
	}

	@Override
	public List buscarCoincidencias(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor,
				MatchMode.ANYWHERE));
		List<Order> orden = new ArrayList<Order>();
		orden.add(Order.asc("nombre"));
		return tipoEntidadExternaDAO.findByCriterions(TipoEntidadExterna.class, restricciones, orden);
	}

	@Override
	public TipoEntidadExterna buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List<?> busqueda = tipoEntidadExternaDAO.findByCriterions(TipoEntidadExterna.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoEntidadExterna) busqueda.get(0);
		} else {
			return null;
		}
	}

}
