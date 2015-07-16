package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Configuracion;

public interface IServicioConfiguracion {
	
	public void guardarConfiguracion(Configuracion obj);

	public void borrarConfiguracion(Configuracion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Configuracion buscarUno(String valor, char... estatus);

}

