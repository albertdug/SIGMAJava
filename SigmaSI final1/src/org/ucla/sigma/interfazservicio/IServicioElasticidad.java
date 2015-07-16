package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Elasticidad;

public interface IServicioElasticidad {
	
	public void guardarElasticidad(Elasticidad obj);

	public void borrarElasticidad(Elasticidad obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Elasticidad buscarUno(String valor, char... estatus);

}


