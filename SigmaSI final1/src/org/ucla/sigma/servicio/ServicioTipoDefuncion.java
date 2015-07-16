package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoDefuncionDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoDefuncion;
import org.ucla.sigma.modelo.TipoDefuncion;

public class ServicioTipoDefuncion implements IServicioTipoDefuncion, Serializable {

	private TipoDefuncionDAO tipoDefuncionDAO;

	public TipoDefuncionDAO getTipoDefuncionDAO() {
		return tipoDefuncionDAO;
	}

	public void setTipoDefuncionDAO(TipoDefuncionDAO tipoDefuncionDAO) {
		this.tipoDefuncionDAO = tipoDefuncionDAO;
	}
	
	@Override
	public void guardarTipoDefuncion(TipoDefuncion obj) {
		obj.setEstatus('A');
		tipoDefuncionDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTipoDefuncion(TipoDefuncion obj) {
		obj.setEstatus('E');
		tipoDefuncionDAO.saveOrUpdate(obj);
	}

	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tipoDefuncionDAO.findByCriterions(TipoDefuncion.class, restricciones, orden);
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
		return tipoDefuncionDAO.findByCriterions(TipoDefuncion.class, restricciones, orden);
	}

	@Override
	public TipoDefuncion buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tipoDefuncionDAO.findByCriterions(TipoDefuncion.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (TipoDefuncion) busqueda.get(0);
		} else {
			return null;
		}
	}


}
