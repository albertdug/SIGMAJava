package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Profundidad;

public interface IServicioProfundidad {
	
	public void guardarProfundidad(Profundidad obj);

	public void borrarProfundidad(Profundidad obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Profundidad buscarUno(String valor, char... estatus);

}
