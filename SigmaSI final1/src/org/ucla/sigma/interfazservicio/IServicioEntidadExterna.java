package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.EntidadExterna;

public interface IServicioEntidadExterna {
	
	public void guardarEntidadExterna(EntidadExterna obj);

	public void borrarEntidadExterna(EntidadExterna obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public EntidadExterna buscarUno(String valor, char... estatus);
	
}
