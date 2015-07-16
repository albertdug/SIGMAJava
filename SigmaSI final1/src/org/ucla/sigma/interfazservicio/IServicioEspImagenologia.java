package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.EspImagenologia;
import org.ucla.sigma.modelo.TipoImagenologia;

public interface IServicioEspImagenologia {
	
	public void guardarEspImagenologia(EspImagenologia obj);

	public void borrarEspImagenologia(EspImagenologia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public EspImagenologia buscarUno(String valor, char... estatus);

	public List buscarCoincidenciasTipo(TipoImagenologia valor, char[] estatus);

	public List buscarEstudiosAsociados(TipoImagenologia valor, char[] estatus);

}
