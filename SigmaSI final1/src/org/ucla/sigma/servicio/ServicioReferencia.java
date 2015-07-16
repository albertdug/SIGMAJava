package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.dao.ReferenciaDAO;
import org.ucla.sigma.interfazservicio.IServicioReferencia;
import org.ucla.sigma.modelo.Area;
import org.ucla.sigma.modelo.Historial;
import org.ucla.sigma.modelo.Referencia;
import org.ucla.sigma.modelo.ReferenciaConsultaEspecializada;
import org.ucla.sigma.modelo.Responsable;


public class ServicioReferencia implements IServicioReferencia, Serializable {

	private ReferenciaDAO referenciaDAO;
	private Date fechaActual;
	private Date fechaUltimos;
	
	

	public ReferenciaDAO getReferenciaDAO() {
		return referenciaDAO;
	}

	public void setReferenciaDAO(ReferenciaDAO referenciaDAO) {
		this.referenciaDAO = referenciaDAO;
	}
	
	@Override
	public List buscarTodos(String ordenado, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.desc(ordenado));
		return referenciaDAO.findByCriterions(Referencia.class, restricciones, orden);
	}
	
	
//	@Override
//	public List buscarTodosCitas(char... estatus) {
//		List restricciones = new ArrayList();
//		if (estatus != null && estatus.length > 0) {
//			restricciones.add(Restrictions.eq("estatus", estatus[0]));
//		}
//		List orden = new ArrayList();
//		orden.add(Order.asc("fechaCita"));
//		return referenciaDAO.findByCriterions(Referencia.class, restricciones, orden);
//	}
	
	@Override
	public List buscarUltimos(char... estatus) {
		List restricciones = new ArrayList();
		fechaActual = HelperDate.now();
		fechaUltimos = HelperDate.future(HelperDate.MES, -3);
		restricciones.add(Restrictions.between("fechaExpedicion", fechaUltimos, fechaActual));
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.desc("fechaExpedicion"));
		return referenciaDAO.findByCriterions(Referencia.class, restricciones, orden);
	}
	
	@Override
	public List buscarRango(String ordenado,String filtrado,Date inicio, Date fin, char... estatus) {
		List restricciones = new ArrayList();
		restricciones.add(Restrictions.between(filtrado, inicio, fin));
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.desc(ordenado));
		return referenciaDAO.findByCriterions(Referencia.class, restricciones, orden);
	}
	
	@Override
	public List buscarHoy(String ordenado, char... estatus) {
		List restricciones = new ArrayList();
		restricciones.add(Restrictions.eq("fechaCita", HelperDate.now()));
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc(ordenado));
		return referenciaDAO.findByCriterions(Referencia.class, restricciones, orden);
	}
	
	
	
	@Override
	public int countDisponibilidad(String fechaCita, String tipoServicio){
		String sql = "select count(*) from referencia where fecha_cita = '%s' and tipo_servicioid = '%s'";
		return Integer.parseInt(referenciaDAO.findBySQLQuery(String.format(sql,fechaCita,tipoServicio)).get(0).toString());
	}
		

	@Override
	public void guardarReferencia(Referencia obj) {
		obj.setEstatus('C');
		referenciaDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarReferencia(Referencia obj) {
		obj.setEstatus('E');
		referenciaDAO.saveOrUpdate(obj);
	}
	
	public List<Referencia> buscarReferenciaHQL(String condicion) {

		String hql = "select distinct r from Referencia r join r.tipoServicio ts"
				+ condicion;

		System.out.println(hql);
		return referenciaDAO.findByHQLQuery(hql);
	}
	
	
}
