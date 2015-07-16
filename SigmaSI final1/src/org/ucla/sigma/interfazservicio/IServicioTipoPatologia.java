package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TipoPatologia;

public interface IServicioTipoPatologia {
	
	public void guardarTipoPatologia(TipoPatologia obj);

	public void borrarTipoPatologia(TipoPatologia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoPatologia buscarUno(String valor, char... estatus);
	
}
