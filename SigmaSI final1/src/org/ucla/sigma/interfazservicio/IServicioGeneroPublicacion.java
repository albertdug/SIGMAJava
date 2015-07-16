package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.GeneroPublicacion;

public interface IServicioGeneroPublicacion {
	
	public void guardarGeneroPublicacion(GeneroPublicacion obj);

	public void borrarGeneroPublicacion(GeneroPublicacion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public GeneroPublicacion buscarUno(String valor, char... estatus);
}
