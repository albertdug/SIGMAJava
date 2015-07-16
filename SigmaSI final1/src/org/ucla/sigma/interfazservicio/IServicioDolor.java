package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Dolor;

public interface IServicioDolor {
	
	public void guardarDolor(Dolor obj);

	public void borrarDolor(Dolor obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Dolor buscarUno(String valor, char... estatus);

}
