package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TexturaPilosaDAO;
import org.ucla.sigma.interfazservicio.IServicioTexturaPilosa;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.TexturaPilosa;

public class ServicioTexturaPilosa implements IServicioTexturaPilosa, Serializable {

	private TexturaPilosaDAO texturapilosaDAO;

	public TexturaPilosaDAO getTexturaPilosaDAO() {
		return texturapilosaDAO;
	}

	public void setTexturaPilosaDAO(TexturaPilosaDAO texturapilosaDAO) {
		this.texturapilosaDAO = texturapilosaDAO;
	}

	@Override
	public void guardarTexturaPilosa(TexturaPilosa obj) {
		obj.setEstatus('A');
		texturapilosaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTexturaPilosa(TexturaPilosa obj) {
		obj.setEstatus('E');
		texturapilosaDAO.saveOrUpdate(obj);
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
		return texturapilosaDAO.findByCriterions(TexturaPilosa.class, restricciones, orden);
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
		return texturapilosaDAO.findByCriterions(TexturaPilosa.class, restricciones, orden);
	}

	@Override
	public TexturaPilosa buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = texturapilosaDAO.findByCriterions(TexturaPilosa.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (TexturaPilosa) busqueda.get(0);
		} else {
			return null;
		}
	}

}
