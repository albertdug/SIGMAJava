package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.SiluetaDAO;
import org.ucla.sigma.interfazservicio.IServicioSilueta;
import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Silueta;

public class ServicioSilueta implements IServicioSilueta, Serializable {

	private SiluetaDAO siluetaDAO;

	public SiluetaDAO getSiluetaDAO() {
		return siluetaDAO;
	}

	public void setSiluetaDAO(SiluetaDAO siluetaDAO) {
		this.siluetaDAO = siluetaDAO;
	}
	
	@Override
	public void guardarSilueta(Silueta obj) {
		obj.setEstatus('A');
		siluetaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarSilueta(Silueta obj) {
		obj.setEstatus('E');
		siluetaDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioEspecie.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return siluetaDAO.findByCriterions(Silueta.class, restricciones, orden);
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
		return siluetaDAO.findByCriterions(Silueta.class, restricciones, orden);
	}

	@Override
	public Silueta buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = siluetaDAO.findByCriterions(Silueta.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Silueta) busqueda.get(0);
		} else {
			return null;
		}
	}


}
