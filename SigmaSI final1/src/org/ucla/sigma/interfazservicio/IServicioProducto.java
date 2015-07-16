package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.Producto;

public interface IServicioProducto {
	
	public void guardarProducto(Producto obj);

	public void borrarProducto(Producto obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public Producto buscarUno(String valor, char... estatus);

}
