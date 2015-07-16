package org.ucla.sigma.servicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.ucla.sigma.dao.PacienteDAO;
import org.ucla.sigma.interfazservicio.IServicioPaciente;
import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Hospital;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.modelo.Sexo;
import org.ucla.sigma.modelo.TipoServicio;

public class ServicioPaciente implements IServicioPaciente, Serializable {

	private PacienteDAO pacienteDAO;

	public PacienteDAO getPacienteDAO() {
		return pacienteDAO;
	}

	public void setPacienteDAO(PacienteDAO pacienteDAO) {
		this.pacienteDAO = pacienteDAO;
	}
	
	@Override
	public void guardarPaciente(Paciente obj) {
		obj.setEstatus('A');
		pacienteDAO.saveOrUpdate(obj);
	}

	@Override
	public void borrarPaciente(Paciente obj) {
		obj.setEstatus('E');
		pacienteDAO.saveOrUpdate(obj);
	}

	/**
	 * @param estatus
	 *            el estatus por el cual se va a buscar 'A', 'E' si no se pasa
	 *            ningun estatus busca eliminados y activos por igual, ej:
	 *            servicioPaciente.buscarTodos();
	 */
	@Override
	public List buscarTodos(char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("historiaMedica"));
		return pacienteDAO.findByCriterions(Paciente.class, restricciones, orden);
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
		orden.add(Order.asc("historiaMedica"));
		return pacienteDAO.findByCriterions(Paciente.class, restricciones, orden);
	}

	@Override
	public Paciente buscarUno(String valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("historiaMedica", valor));
		List busqueda = pacienteDAO.findByCriterions(Paciente.class, restricciones);
		if (!busqueda.isEmpty()) {
			return (Paciente) busqueda.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List buscarPorResponsable(Responsable valor, char... estatus) {
		List restricciones = new ArrayList();
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		restricciones.add(Restrictions.eq("responsable", valor));
		return pacienteDAO.findByCriterions(Paciente.class, restricciones);

	}
	
	@Override
	public List buscarTodosRestricciones(List valor, char... estatus) {
		List restricciones = valor;
		if (estatus != null && estatus.length > 0) {
			restricciones.add(Restrictions.eq("estatus", estatus[0]));
		}
		List orden = new ArrayList();
		orden.add(Order.asc("historiaMedica"));
		return pacienteDAO.findByCriterions(Paciente.class, restricciones, orden);
	}
	
	public List<Paciente> buscarPacientesHQL(String hql){
		return pacienteDAO.findByHQLQuery(String.format(hql));
	}
	
	@Override
	public int countEnMes(String mes, String anno) {
		String sql = "select count(*) from paciente where extract(month from creacion)='%s' and extract(year from creacion)='%s'";
		return Integer.parseInt(pacienteDAO.findBySQLQuery(String.format(sql,mes,anno)).get(0).toString());
	}
	
	public List<Paciente> buscarPacientesHQL(Sexo sexo,Raza raza,TipoServicio tipoServicio){
//		String sql = "select distinct p from Paciente p join p.historials h join h.patologias pat where h.fecha between '%s' and '%s' and pat.tipoPatologia.id = %d";
		
		String hql ="select distinct p from Paciente p join p.sexo s join p.raza r join p.historials h join h.tipoServicio t where s.id = '%d' and r.id= '%d' and t.id= '%s'";
		return pacienteDAO.findByHQLQuery(String.format(hql, sexo.getId(),raza.getId(),tipoServicio.getId()));
	}
}
