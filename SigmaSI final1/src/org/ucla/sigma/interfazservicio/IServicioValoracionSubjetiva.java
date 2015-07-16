package org.ucla.sigma.interfazservicio;


import java.util.List;
import org.ucla.sigma.modelo.ValoracionSubjetiva;

public interface IServicioValoracionSubjetiva {

	public void guardarValoracionSubjetiva(ValoracionSubjetiva obj);

	public void borrarValoracionSubjetiva(ValoracionSubjetiva obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public ValoracionSubjetiva buscarUno(String valor, char... estatus);
}

