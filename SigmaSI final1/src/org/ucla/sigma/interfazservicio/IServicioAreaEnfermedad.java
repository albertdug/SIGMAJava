package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.AreaEnfermedad;

public interface IServicioAreaEnfermedad {
	
	public void guardarAreaEnfermedad(AreaEnfermedad obj);

	public void borrarAreaEnfermedad(AreaEnfermedad obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public AreaEnfermedad buscarUno(String valor, char... estatus);

}
