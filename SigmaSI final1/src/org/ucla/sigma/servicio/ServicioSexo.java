package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.SexoDAO;
import org.ucla.sigma.interfazservicio.IServicioSexo;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Sexo;

public class ServicioSexo implements IServicioSexo, Serializable {

	private SexoDAO sexoDAO;

	public SexoDAO getSexoDAO() {
		return sexoDAO;
	}

	public void setSexoDAO(SexoDAO sexoDAO) {
		this.sexoDAO = sexoDAO;
	}

	@Override
	public void guardarSexo(Sexo obj) {
		obj.setEstatus('A');
		sexoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarSexo(Sexo obj) {
		obj.setEstatus('E');
		sexoDAO.saveOrUpdate(obj);
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
		return sexoDAO.findByCriterions(Sexo.class, restricciones, orden);
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
		return sexoDAO.findByCriterions(Sexo.class, restricciones, orden);
	}

	@Override
	public Sexo buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = sexoDAO.findByCriterions(Sexo.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Sexo) busqueda.get(0);
		} else {
			return null;
		}
	}

}
