package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TexturaPiel;

public interface IServicioTexturaPiel {
	
	public void guardarTexturaPiel(TexturaPiel obj);

	public void borrarTexturaPiel(TexturaPiel obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TexturaPiel buscarUno(String valor, char... estatus);

}
