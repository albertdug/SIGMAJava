package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.TipoTratamiento;
import org.ucla.sigma.modelo.Tratamiento;

public interface IServicioTratamiento {
	public void guardarTratamiento(Tratamiento obj);

	public void borrarTratamiento(Tratamiento obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Tratamiento buscarUno(String valor, char... estatus);
	
	public List buscarCoincidenciasTipo(TipoTratamiento valor, char... estatus);
	
}
