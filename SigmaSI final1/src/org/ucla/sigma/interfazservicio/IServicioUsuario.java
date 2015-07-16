package org.ucla.sigma.interfazservicio;

import java.util.List;

import org.ucla.sigma.modelo.Usuario;

public interface IServicioUsuario {
	public void guardarUsuario(Usuario obj);

	public void borrarUsuario(Usuario obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(int valor, char... estatus);

	public Usuario buscarUno(int valor, char[] estatus);

	public Usuario Credenciales(String login, String password, char[] estatus);
}
