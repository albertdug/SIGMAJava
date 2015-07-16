package org.ucla.sigma.servicio;

import java.io.Serializable;

import org.ucla.sigma.dao.ReferenciaConsultaEspecializadaDAO;
import org.ucla.sigma.interfazservicio.IServicioReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;

public class ServicioCitas implements IServicioReferenciaConsultaEspecializada, Serializable {

	
	private ReferenciaConsultaEspecializadaDAO referenciaConsultaEspecializadaDAO;

	public ReferenciaConsultaEspecializadaDAO getReferenciaConsultaEspecializadaDAO() {
		return referenciaConsultaEspecializadaDAO;
	}

	public void setReferenciaConsultaEspecializadaDAO(
			ReferenciaConsultaEspecializadaDAO referenciaConsultaEspecializadaDAO) {
		this.referenciaConsultaEspecializadaDAO = referenciaConsultaEspecializadaDAO;
	}
	
	@Override
	public void guardarReferenciaConsultaEspecializada(ReferenciaConsultaEspecializada obj) {
		obj.setEstatus('P');
		referenciaConsultaEspecializadaDAO.saveOrUpdate(obj);
	}
	
	@Override
	public int countDisponibilidad(String fechaCita, String tipoServicio){
		String sql = "select count(*) from referencia where fecha_cita = '%s' and tipo_servicioid = '%s'";
		return Integer.parseInt(referenciaConsultaEspecializadaDAO.findBySQLQuery(String.format(sql,fechaCita,tipoServicio)).get(0).toString());
	}
		
}
