package org.ucla.sigma.interfazservicio;

import java.util.List;
import org.ucla.sigma.modelo.OtrasCaracteristicas;

public interface IServicioOtrasCaracteristicas {
	
	public void guardarOtrasCaracteristicas(OtrasCaracteristicas obj);

	public void borrarOtrasCaracteristicas(OtrasCaracteristicas obj);

	public List buscarTodos(char... estatus);

	public List buscarCoincidencias(String valor, char... estatus);

	public OtrasCaracteristicas buscarUno(String valor, char... estatus);

}
