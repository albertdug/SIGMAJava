package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Reflejo;

public interface IServicioReflejo {
	
	public void guardarReflejo(Reflejo obj);

	public void borrarReflejo(Reflejo obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Reflejo buscarUno(String valor, char... estatus);

}
