package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.EstadoHidratacion;

public interface IServicioEstadoHidratacion {
	
	public void guardarEstadoHidratacion(EstadoHidratacion obj);

	public void borrarEstadoHidratacion(EstadoHidratacion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public EstadoHidratacion buscarUno(String valor, char... estatus);

}
