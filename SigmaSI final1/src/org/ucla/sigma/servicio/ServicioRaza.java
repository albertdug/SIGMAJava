package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.RazaDAO;
import org.ucla.sigma.interfazservicio.IServicioRaza;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Estado;
import org.ucla.sigma.modelo.Raza;

public class ServicioRaza implements IServicioRaza, Serializable {

	private RazaDAO razaDAO;

	public RazaDAO getRazaDAO() {
		return razaDAO;
	}

	public void setRazaDAO(RazaDAO razaDAO) {
		this.razaDAO = razaDAO;
	}

	@Override
	public void guardarRaza(Raza obj) {
		obj.setEstatus('A');
		razaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarRaza(Raza obj) {
		obj.setEstatus('E');
		razaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioRaza.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return razaDAO.findByCriterions(Raza.class, restricciones, orden);
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
		return razaDAO.findByCriterions(Raza.class, restricciones, orden);
	}

	@Override
	public Raza buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = razaDAO.findByCriterions(Raza.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Raza) busqueda.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List buscarEspeciesAsociados(Especie valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("especie", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return razaDAO.findByCriterions(Raza.class, restricciones, orden);
	}
	
	@Override
	public List buscarCoincidenciasEspeciesVarias(Set<Especie> valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.in("especie", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return razaDAO.findByCriterions(Raza.class, restricciones, orden);
	}

}
