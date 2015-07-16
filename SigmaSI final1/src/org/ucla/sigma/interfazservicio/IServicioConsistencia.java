package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Consistencia;

public interface IServicioConsistencia {
	
	public void guardarConsistencia(Consistencia obj);

	public void borrarConsistencia(Consistencia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Consistencia buscarUno(String valor, char... estatus);

}
