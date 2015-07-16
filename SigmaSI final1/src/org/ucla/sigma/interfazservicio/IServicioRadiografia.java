package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Radiografia;

public interface IServicioRadiografia {
	
	public void guardarRadiografia(Radiografia obj);

	public void borrarRadiografia(Radiografia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Radiografia buscarUno(String valor, char... estatus);

}
