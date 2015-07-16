package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Publicacion;
import org.ucla.sigma.modelo.TipoPublicacion;

public interface IServicioPublicacion {

	List last(int offset, int limit, char[] estatus);

	Publicacion buscarUnoPorUri(String valor, char[] estatus);
	
	List<Publicacion> last(int i, int limit, TipoPublicacion tipoPublicacion,
			char[] estatus);

	int count(char[] estatus);

	int countPorTipo(TipoPublicacion tipoPublicacionActual, char[] estatus);

	Publicacion anterior(Publicacion publicacion, char[] estatus);

	Publicacion siguiente(Publicacion publicacion, char[] estatus);

	List<Publicacion> buscarTodos(TipoPublicacion tipoPublicacion,
			char[] estatus);

	List<Publicacion> buscarTodos(char[] estatus);

	Publicacion buscarUno(String valor, char[] estatus);

	List buscarCoincidencias(String valor, char[] estatus);

	void guardarPublicacion(Publicacion obj);

	void borrarPublicacion(Publicacion obj);


}
