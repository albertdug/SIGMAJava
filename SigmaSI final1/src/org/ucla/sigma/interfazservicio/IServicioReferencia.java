package org.ucla.sigma.interfazservicio;

import java.util.Date;
import java.util.List;

import org.ucla.sigma.modelo.Referencia;

public interface IServicioReferencia {
	
	public List buscarTodos(String ordenado, char... estatus);
	
	public List buscarUltimos(char... estatus);
	
	public void guardarReferencia(Referencia obj);

	public List buscarRango(String ordenado,String filtrado,Date inicio, Date fin, char... estatus);

	public int countDisponibilidad(String fechaCita, String tipoServicio);

	public void borrarReferencia(Referencia obj);

	public List buscarHoy(String ordenado, char... estatus);

}
