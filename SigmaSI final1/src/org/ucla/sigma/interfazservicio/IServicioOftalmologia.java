package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Oftalmologia;

public interface IServicioOftalmologia {
	
	public void guardarOftalmologia(Oftalmologia obj);

	public void borrarOftalmologia(Oftalmologia obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Oftalmologia buscarUno(String valor, char... estatus);

	
}
