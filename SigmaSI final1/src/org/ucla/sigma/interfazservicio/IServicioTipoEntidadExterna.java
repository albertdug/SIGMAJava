package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TipoEntidadExterna;

public interface IServicioTipoEntidadExterna {
	
	public void guardarTipoEntidadExterna(TipoEntidadExterna obj);

	public void borrarTipoEntidadExterna(TipoEntidadExterna obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoEntidadExterna buscarUno(String valor, char... estatus);

}
