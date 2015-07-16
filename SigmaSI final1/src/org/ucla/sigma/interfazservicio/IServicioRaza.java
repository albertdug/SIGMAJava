package org.ucla.sigma.interfazservicio;

import java.util.List;
import java.util.Set;

import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Raza;

public interface IServicioRaza {
	
	public void guardarRaza(Raza obj);

	public void borrarRaza(Raza obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Raza buscarUno(String valor, char... estatus);
	
	public List buscarEspeciesAsociados(Especie valor, char... estatus);

	public List buscarCoincidenciasEspeciesVarias(Set<Especie> valor, char[] estatus);

}
