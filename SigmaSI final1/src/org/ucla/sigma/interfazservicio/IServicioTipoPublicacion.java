package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TipoPublicacion;

public interface IServicioTipoPublicacion {
	
	public void guardarTipoPublicacion(TipoPublicacion obj);

	public void borrarTipoPublicacion(TipoPublicacion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoPublicacion buscarUno(String valor, char... estatus);

	TipoPublicacion buscarUnoPorUri(String valor, char[] estatus);

	List buscarEn(List valores, char[] estatus);
}
