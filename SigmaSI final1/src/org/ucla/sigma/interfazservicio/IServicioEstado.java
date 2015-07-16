package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Estado;

public interface IServicioEstado {
	
	public void guardarEstado(Estado obj);

	public void borrarEstado(Estado obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Estado buscarUno(String valor, char... estatus);

}
