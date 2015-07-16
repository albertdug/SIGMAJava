package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Frecuencia;

public interface IServicioFrecuencia {
	
	public void guardarFrecuencia(Frecuencia obj);

	public void borrarFrecuencia(Frecuencia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Frecuencia buscarUno(String valor, char... estatus);
	
	public List buscarFrecuenciaTipo(String valor,char... estatus);
	
	public List buscarFrecuenciaAlimento(char... estatus);
	
	public List buscarFrecuenciaBano(char... estatus);
	
	public List buscarFrecuenciaRasca(char... estatus);

}
