package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Especie;

public interface IServicioEspecie {
	
	public void guardarEspecie(Especie obj);

	public void borrarEspecie(Especie obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Especie buscarUno(String valor, char... estatus);

}
