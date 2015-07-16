package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Aspecto;

public interface IServicioAspecto {
	
	public void guardarAspecto(Aspecto obj);

	public void borrarAspecto(Aspecto obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Aspecto buscarUno(String valor, char... estatus);

}
