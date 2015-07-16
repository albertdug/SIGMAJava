package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.TipoReferenciaDAO;
import org.ucla.sigma.interfazservicio.IServicioTipoReferencia;
import org.ucla.sigma.modelo.Mensaje;
import org.ucla.sigma.modelo.TipoReferencia;

public class ServicioTipoReferencia implements IServicioTipoReferencia, Serializable {

	private TipoReferenciaDAO tipoReferenciaDAO;

	public TipoReferenciaDAO getTipoReferenciaDAO() {
		return tipoReferenciaDAO;
	}

	public void setTipoReferenciaDAO(TipoReferenciaDAO tipoReferenciaDAO) {
		this.tipoReferenciaDAO = tipoReferenciaDAO;
	}

	public TipoReferencia buscarUno(String string) {
		TipoReferencia tr=null;
		List orden = new ArrayList();
		List busqueda = new ArrayList();
		orden.add(Restrictions.eq("id", string));
		busqueda = tipoReferenciaDAO.findByCriterions(TipoReferencia.class, orden);
		if (!busqueda.isEmpty()) {
			tr = (TipoReferencia) busqueda.get(0);
		} 
		return tr;
	}
	
	

}
