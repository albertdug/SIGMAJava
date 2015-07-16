package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TexturaPielDAO;
import org.ucla.sigma.interfazservicio.IServicioTexturaPiel;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.TexturaPiel;

public class ServicioTexturaPiel implements IServicioTexturaPiel, Serializable {

	private TexturaPielDAO texturapielDAO;

	public TexturaPielDAO getTexturaPielDAO() {
		return texturapielDAO;
	}

	public void setTexturaPielDAO(TexturaPielDAO texturapielDAO) {
		this.texturapielDAO = texturapielDAO;
	}

	@Override
	public void guardarTexturaPiel(TexturaPiel obj) {
		obj.setEstatus('A');
		texturapielDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTexturaPiel(TexturaPiel obj) {
		obj.setEstatus('E');
		texturapielDAO.saveOrUpdate(obj);
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
		return texturapielDAO.findByCriterions(TexturaPiel.class, restricciones, orden);
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
		return texturapielDAO.findByCriterions(TexturaPiel.class, restricciones, orden);
	}

	@Override
	public TexturaPiel buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = texturapielDAO.findByCriterions(TexturaPiel.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (TexturaPiel) busqueda.get(0);
		} else {
			return null;
		}
	}

}
