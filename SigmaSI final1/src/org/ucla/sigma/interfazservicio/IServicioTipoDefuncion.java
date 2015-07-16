package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TipoDefuncion;

public interface IServicioTipoDefuncion {

	void guardarTipoDefuncion(TipoDefuncion obj);

	void borrarTipoDefuncion(TipoDefuncion obj);

	List buscarTodos(char... estatus);

	List buscarCoincidencias(String valor, char... estatus);

	TipoDefuncion buscarUno(String valor, char... estatus);

}
