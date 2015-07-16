package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.ReaccionPostular;

public interface IServicioReaccionPostular {
	
	public void guardarReaccionPostular(ReaccionPostular obj);

	public void borrarReaccionPostular(ReaccionPostular obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public ReaccionPostular buscarUno(String valor, char... estatus);

}
