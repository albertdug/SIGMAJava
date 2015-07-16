package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.TexturaPilosa;

public interface IServicioTexturaPilosa {
	
	public void guardarTexturaPilosa(TexturaPilosa obj);

	public void borrarTexturaPilosa(TexturaPilosa obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public TexturaPilosa buscarUno(String valor, char... estatus);

}
