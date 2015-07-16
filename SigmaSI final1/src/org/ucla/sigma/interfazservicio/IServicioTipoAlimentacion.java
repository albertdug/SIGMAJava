package org.ucla.sigma.interfazservicio;

import java.util.List;


import org.ucla.sigma.modelo.TipoAlimentacion;

public interface IServicioTipoAlimentacion {
	
	public void guardarTipoAlimentacion(TipoAlimentacion obj);

	public void borrarTipoAlimentacion(TipoAlimentacion obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TipoAlimentacion buscarUno(String valor, char... estatus);
}
