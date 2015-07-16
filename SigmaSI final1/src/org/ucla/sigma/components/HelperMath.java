package org.ucla.sigma.components;

/**
 * Clase para el manejo de numeros
 */
public abstract class HelperMath {

	/**
	 * Funcion que retorna un valor a leatorio
	 * 
	 * @param max
	 *            Maximo numero a obtener
	 * @param min
	 *            Minimo numero a obtener
	 * @return Un valor aleatorio entre un min u max numero
	 */
	public static int aleatorio(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}
}
