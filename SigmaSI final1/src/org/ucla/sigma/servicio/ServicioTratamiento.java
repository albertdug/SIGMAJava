package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TratamientoDAO;
import org.ucla.sigma.interfazservicio.IServicioTratamiento;
import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;

public class ServicioTratamiento implements IServicioTratamiento, Serializable {

	private TratamientoDAO tratamientoDAO;

	public TratamientoDAO getTratamientoDAO() {
		return tratamientoDAO;
	}

	public void setTratamientoDAO(TratamientoDAO tratamientoDAO) {
		this.tratamientoDAO = tratamientoDAO;
	}
	
	@Override
	public void guardarTratamiento(Tratamiento obj) {
		obj.setEstatus('A');
		tratamientoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarTratamiento(Tratamiento obj) {
		obj.setEstatus('E');
		tratamientoDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioTratamiento.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tratamientoDAO.findByCriterions(Tratamiento.class, restricciones, orden);
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
		return tratamientoDAO.findByCriterions(Tratamiento.class, restricciones, orden);
	}

	@Override
	public Tratamiento buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = tratamientoDAO.findByCriterions(Tratamiento.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Tratamiento) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	public List buscarCoincidenciasTipo(TipoTratamiento valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("tipoTratamiento", valor));
		List orden = new ArrayList();
		orden.add(Order.asc("nombre"));
		return tratamientoDAO.findByCriterions(Tratamiento.class, restricciones, orden);
	}
}
