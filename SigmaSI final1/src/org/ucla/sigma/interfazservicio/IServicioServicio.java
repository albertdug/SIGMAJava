package org.ucla.sigma.interfazservicio;

import java.util.List;
import java.util.Set;

import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoServicio;

	public interface IServicioServicio {
	
	public void guardarServicio(Servicio obj);

	public void borrarServicio(Servicio obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Servicio buscarUno(String valor, char... estatus);

	public List buscarCoincidenciasTipoServiciosVarias(TipoServicio valor,
			char... estatus);
	

}
