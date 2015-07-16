package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.RespuestaDAO;
import org.ucla.sigma.interfazservicio.IServicioRespuesta;
import org.ucla.sigma.modelo.Respuesta;

public class ServicioRespuesta implements IServicioRespuesta, Serializable {

	private RespuestaDAO respuestaDAO;

	public RespuestaDAO getRespuestaDAO() {
		return respuestaDAO;
	}

	public void setRespuestaDAO(RespuestaDAO respuestaDAO) {
		this.respuestaDAO = respuestaDAO;
	}
	@Override
	public void guardarRespuesta(Respuesta obj) {
		obj.setEstatus('A');
		respuestaDAO.saveOrUpdate(obj);
	}
	

}
