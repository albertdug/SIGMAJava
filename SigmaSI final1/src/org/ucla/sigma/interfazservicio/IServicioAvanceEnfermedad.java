package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.AvanceEnfermedad;

public interface IServicioAvanceEnfermedad {
	
	public void guardarAvanceEnfermedad(AvanceEnfermedad obj);

	public void borrarAvanceEnfermedad(AvanceEnfermedad obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public AvanceEnfermedad buscarUno(String valor, char... estatus);

}
