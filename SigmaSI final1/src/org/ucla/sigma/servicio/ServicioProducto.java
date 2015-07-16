package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.ProductoDAO;
import org.ucla.sigma.interfazservicio.IServicioProducto;
import org.ucla.sigma.modelo.Producto;

public class ServicioProducto implements IServicioProducto, Serializable {

	private ProductoDAO productoDAO;

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	@Override
	public void guardarProducto(Producto obj) {
		obj.setEstatus('A');
		productoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarProducto(Producto obj) {
		obj.setEstatus('E');
		productoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEstado.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return productoDAO.findByCriterions(Producto.class, restricciones, orden);
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
		return productoDAO.findByCriterions(Producto.class, restricciones, orden);
	}

	@Override
	public Producto buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = productoDAO.findByCriterions(Producto.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Producto) busqueda.get(0);
		} else {
			return null;
		}
	}

}
