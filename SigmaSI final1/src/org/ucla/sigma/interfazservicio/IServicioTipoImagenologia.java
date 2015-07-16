package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.TipoImagenologia;

public interface IServicioTipoImagenologia {
	
	public void guardarTipoImagenologia(TipoImagenologia obj);

	public void borrarTipoImagenologia(TipoImagenologia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoImagenologia buscarUno(String valor, char... estatus);
	
}
