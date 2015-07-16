package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.SintomaDAO;
import org.ucla.sigma.interfazservicio.IServicioSintoma;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Sintoma;

public class ServicioSintoma implements IServicioSintoma, Serializable {

	private SintomaDAO sintomaDAO;

	public SintomaDAO getSintomaDAO() {
		return sintomaDAO;
	}

	public void setSintomaDAO(SintomaDAO sintomaDAO) {
		this.sintomaDAO = sintomaDAO;
	}
	
	@Override
	public void guardarSintoma(Sintoma obj) {
		obj.setEstatus('A');
		sintomaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarSintoma(Sintoma obj) {
		obj.setEstatus('E');
		sintomaDAO.saveOrUpdate(obj);
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
		return sintomaDAO.findByCriterions(Sintoma.class, restricciones, orden);
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
		return sintomaDAO.findByCriterions(Sintoma.class, restricciones, orden);
	}

	@Override
	public Sintoma buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = sintomaDAO.findByCriterions(Sintoma.class,
				restricciones);
		if (!busqueda.isEmpty()) {
			return (Sintoma) busqueda.get(0);
		} else {
			return null;
		}
	}


}
