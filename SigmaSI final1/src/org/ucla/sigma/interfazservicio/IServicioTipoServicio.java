package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.TipoServicio;

public interface IServicioTipoServicio {
	
	public void guardarTipoServicio(TipoServicio obj);

	public void borrarTipoServicio(TipoServicio obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoServicio buscarUno(String valor, char... estatus);

	public TipoServicio buscarTipoServicio(String valor, char[] estatus);
	
}
