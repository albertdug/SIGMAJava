package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.DiaDeAtencionDAO;
import org.ucla.sigma.interfazservicio.IServicioArea;
import org.ucla.sigma.interfazservicio.IServicioDiaAtencion;
import org.ucla.sigma.modelo.DiaDeAtencion;

public class ServicioDiaDeAtencion implements IServicioDiaAtencion, Serializable {

	private DiaDeAtencionDAO diaDeAtencionDAO;

	public DiaDeAtencionDAO getDiaDeAtencionDAO() {
		return diaDeAtencionDAO;
	}

	public void setDiaDeAtencionDAO(DiaDeAtencionDAO diaDeAtencionDAO) {
		this.diaDeAtencionDAO = diaDeAtencionDAO;
	}

	@Override
	public DiaDeAtencion buscarUno(String valor, char... estatus){
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.ilike("tipoServicio.id", valor));
		List busqueda = diaDeAtencionDAO.findByCriterions(DiaDeAtencion.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (DiaDeAtencion) busqueda.get(0);
		} else {
			return null;
		}

		
		
	}

	
}
