package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Area;

public interface IServicioArea {
	
	public void guardarArea(Area obj);

	public void borrarArea(Area obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Area buscarUno(String valor, char... estatus);
	
}
