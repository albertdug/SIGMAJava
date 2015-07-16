package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Paciente;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Responsable;
import org.ucla.sigma.modelo.Sexo;

public interface IServicioPaciente {
	
	public void guardarPaciente(Paciente obj);

	public void borrarPaciente(Paciente obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Paciente buscarUno(String valor, char... estatus);

	public List buscarPorResponsable(Responsable valor, char[] estatus);
	
	public List buscarTodosRestricciones(List valor, char... estatus);

	public List<Paciente> buscarPacientesHQL(String hql);
	
	public int countEnMes(String mes, String anno);

}
