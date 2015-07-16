package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.TipoServicioWeb;

public interface IServicioTipoServicioWeb {	
	public List buscarTodos(char... estatus);

	TipoServicioWeb buscarUnoPorUri(String uri, char[] estatus);
	
}
