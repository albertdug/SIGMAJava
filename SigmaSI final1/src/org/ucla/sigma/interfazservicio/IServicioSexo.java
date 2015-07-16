package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Sexo;

public interface IServicioSexo {
	
	public void guardarSexo(Sexo obj);

	public void borrarSexo(Sexo obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Sexo buscarUno(String valor, char... estatus);

}
