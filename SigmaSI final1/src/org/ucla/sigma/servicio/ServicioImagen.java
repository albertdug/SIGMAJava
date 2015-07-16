package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ImagenDAO;
import org.ucla.sigma.interfazservicio.IServicioImagen;
import org.ucla.sigma.modelo.Imagen;

public class ServicioImagen implements IServicioImagen, Serializable {

	private ImagenDAO imagenDAO;

	public ImagenDAO getImagenDAO() {
		return imagenDAO;
	}

	public void setImagenDAO(ImagenDAO imagenDAO) {
		this.imagenDAO = imagenDAO;
	}
	
	@Override
	public void guardarImagen(Imagen obj) {
		obj.setEstatus('A');
		imagenDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarImagen(Imagen obj) {
		obj.setEstatus('E');
		imagenDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioImagen.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return imagenDAO.findByCriterions(Imagen.class, restricciones, orden);
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
		return imagenDAO.findByCriterions(Imagen.class, restricciones, orden);
	}

	@Override
	public Imagen buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = imagenDAO.findByCriterions(Imagen.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Imagen) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Imagen buscarUnoPorId(int id, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("id", id));
		List busqueda = imagenDAO.findByCriterions(Imagen.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Imagen) busqueda.get(0);
		} else {
			return null;
		}
	}
	
}
