package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.TipoExamen;

public interface IServicioTipoExamen {
	
	public void guardarTipoExamen(TipoExamen obj);

	public void borrarTipoExamen(TipoExamen obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoExamen buscarUno(String valor, char... estatus);
	
}
