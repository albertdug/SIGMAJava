package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TipoTratamiento;

public interface IServicioTipoTratamiento {
	
	public void guardarTipoTratamiento(TipoTratamiento obj);

	public void borrarTipoTratamiento(TipoTratamiento obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoTratamiento buscarUno(String valor, char... estatus);

}
