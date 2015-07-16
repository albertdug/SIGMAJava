package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Veterinario;

public interface IServicioVeterinario {
	
	public void guardarVeterinario(Veterinario obj);

	public void borrarVeterinario(Veterinario obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(int valor, char... estatus);

	public Veterinario buscarUno(int valor, char... estatus);
}
