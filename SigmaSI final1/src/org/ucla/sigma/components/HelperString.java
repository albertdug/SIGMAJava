package org.ucla.sigma.components;

import java.text.Normalizer;

/**
 * Clase para el manejo de String
 */
public abstract class HelperString {

	/**
	 * Permiter extraer resumenes de parrafos
	 * 
	 * @param string
	 *            Parrafo a recortar
	 * @param words
	 *            Cantidad de palabras permitidas
	 * @return Extrato del parrafo
	 */
	public static String wordsLimiter(String string, int words) {
		String[] palabras = string.split("\\s+");

		StringBuffer tempString = new StringBuffer();

		if (words >= palabras.length) {
			return string;
		} else {
			for (int i = 0; i < words; i++) {
				tempString.append(palabras[i] + " ");
			}
		}

		return tempString.toString().trim() + "...";
	}

	/**
	 * Esta funcion aplica string.trim().isEmpty()
	 * 
	 * @param string
	 *            String a validar
	 * @return Verdadero si el String esta vacio
	 */
	public static boolean isEmpty(String string) {
		return string.trim().isEmpty();
	}

	/**
	 * Esta funcion capitaliza una palabra
	 * 
	 * @param string
	 *            Palabra a capitalizar
	 * @return Palabra capitalizada
	 */
	public static String capitalizeWord(String string) {
		if (string.length() == 0)
			return string;
		return string.substring(0, 1).toUpperCase()
				+ string.substring(1).toLowerCase();
	}

	/**
	 * Esta funcion capitaliza una oracion
	 * 
	 * @param string
	 *            Oracion a capitalizar
	 * @return Oracion capitalizada
	 */
	public static String capitalizeWords(String string) {
		if (string.length() == 0)
			return string;
		StringBuffer sb = new StringBuffer();
		String[] words = string.split("\\s+");
		for (String s : words) {
			sb.append(capitalizeWord(s) + " ");
		}
		return sb.toString().trim();
	}

	/**
	 * Esta funcion se utiliza para crear uris
	 * 
	 * @param title
	 *            Texto a tranformar en uri
	 * @return Uri sin caracteres especiales y en minuscula
	 */
	public static String urlTitle(String title) {
		return Normalizer.normalize(title, Normalizer.Form.NFD).toLowerCase()
				.replace(' ', '-').replaceAll("[^a-zA-Z0-9-]+", "");
	}

	/**
	 * Esta funcion aplica un patron
	 * "^([a-zA-Z0-9_-].){2,15}@[a-zA-Z0-9_-]{2,15}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$"
	 * 
	 * @param email
	 *            Correo a validar
	 * @return Verdadero si es un correo
	 * @see HelperString.match(String pattern, String string)
	 */
	public static boolean isEmail(String email) {
		return match("^([a-zA-Z0-9_-].){2,15}@[a-zA-Z0-9_-]{2,15}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$",
				email);
	}

	/**
	 * Esta funcion aplica un patron
	 * "^(https?\\://)([a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,6}([/\\?][/\\?=a-zA-Z0-9-\\+\\.]*)*$"
	 * 
	 * @param web
	 *            Web a validar
	 * @return Verdadero si en una web
	 * @see HelperString.match(String pattern, String string)
	 */
	public static boolean isWeb(String web) {
		return match(
				"^(https?\\://)([a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,6}([/\\?][/\\?=a-zA-Z0-9-\\+\\.]*)*$",
				web);
	}

	/**
	 * Esta funcion verifica si un String contiene un numero
	 * 
	 * @param string
	 *            String a verificar
	 * @return Verdadero si contiene un numero
	 */
	public static boolean containsNumber(String string) {
		for (int i = 0; i < 9; i++) {
			if (string.contains("" + i))
				return true;
		}
		return false;
	}

	/**
	 * Esta funcion utiliza matches(String) de la clase String Otra
	 * implementacion
	 * <p>
	 * Pattern patron = Pattern.compile(pattern); Matcher m =
	 * patron.matcher(string); return m.find();
	 * 
	 * @param pattern
	 *            Patron a seguir
	 * @param string
	 *            Texto a comparar
	 * @return Verdadero si concuerda el String
	 * @see http ://www.vogella.com/articles/JavaRegularExpressions/article.html
	 * @see http 
	 *      ://docs.oracle.com/javase/1.4.2/docs/api/java/util/regex/Pattern.
	 *      html
	 */
	public static boolean match(String pattern, String string) {
		return string.matches(pattern);
	}
}
