package org.ucla.sigma.interfazservicio;

import java.util.List;
import java.util.Set;

import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.TipoPatologia;

public interface IServicioPatologia {
	
	public void guardarPatologia(Patologia obj);

	public void borrarPatologia(Patologia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Patologia buscarUno(String valor, char... estatus);
	
	public List buscarCoincidenciasTipo(TipoPatologia valor, char... estatus);

	public List buscarCoincidenciasTipoVarias(Set<TipoPatologia> tipoPatologias,
			char[] estatus);
	
}
