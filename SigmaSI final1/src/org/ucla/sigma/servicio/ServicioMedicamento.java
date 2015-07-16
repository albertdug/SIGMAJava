package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.MedicamentoDAO;
import org.ucla.sigma.interfazservicio.IServicioMedicamento;
import org.ucla.sigma.modelo.Medicamento;

public class ServicioMedicamento implements IServicioMedicamento, Serializable {

	private MedicamentoDAO medicamentoDAO;

	public MedicamentoDAO getMedicamentoDAO() {
		return medicamentoDAO;
	}

	public void setMedicamentoDAO(MedicamentoDAO medicamentoDAO) {
		this.medicamentoDAO = medicamentoDAO;
	}

	@Override
	public void guardarMedicamento(Medicamento obj) {
		obj.setEstatus('A');
		medicamentoDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarMedicamento(Medicamento obj) {
		obj.setEstatus('E');
		medicamentoDAO.saveOrUpdate(obj);
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
		return medicamentoDAO.findByCriterions(Medicamento.class, restricciones, orden);
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
		return medicamentoDAO.findByCriterions(Medicamento.class, restricciones, orden);
	}

	@Override
	public Medicamento buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("nombre", valor));
		List busqueda = medicamentoDAO.findByCriterions(Medicamento.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Medicamento) busqueda.get(0);
		} else {
			return null;
		}
	}

}
