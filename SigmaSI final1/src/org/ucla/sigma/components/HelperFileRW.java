package org.ucla.sigma.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Clase para el manejo de la lectura y escritura de archivos de texto
 */
public abstract class HelperFileRW {

	public static final String CHARSET_UTF_8 = "UTF-8";
	public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";

	/**
	 * Lee un archivo de texto
	 * 
	 * @param path
	 *            Ruta del archivo
	 * @return Texto leido
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-
	 *      java/
	 */
	public static String readFile(String path) throws IOException {
		return readFile(new File(path), CHARSET_UTF_8);
	}

	/**
	 * Lee un archivo de texto
	 * 
	 * @param file
	 *            Archivo
	 * @return Texto leido
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-
	 *      java/
	 */
	public static String readFile(File file) throws IOException {
		return readFile(file, CHARSET_UTF_8);
	}

	/**
	 * Lee un archivo de texto
	 * 
	 * @param path
	 *            Ruta del archivo
	 * @param charset
	 *            Tipo de codificacion
	 * @return Texto leido
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-
	 *      java/
	 */
	public static String readFile(String path, String charset)
			throws IOException {
		return readFile(new File(path), charset);
	}

	/**
	 * Lee un archivo de texto
	 * 
	 * @param file
	 *            Archivo
	 * @param charset
	 *            Tipo de codificacion
	 * @return Texto leido
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-read-utf-8-encoded-data-from-a-file-
	 *      java/
	 */
	public static String readFile(File file, String charset) throws IOException {
		String linea = "";
		StringBuffer archivo = new StringBuffer();
		BufferedReader bf = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), charset));
		while (linea != null) {
			linea = bf.readLine();
			if (linea != null)
				archivo.append(linea + "\n");
		}
		bf.close();
		return archivo.toString();
	}

	/**
	 * Escribe un archivo de texto desde un String
	 * 
	 * @param texto
	 *            Texto a escribir
	 * @param file
	 *            Archivo
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-write-utf-8-encoded-data-into-a-file-
	 *      java/
	 */
	public static void writeFile(String texto, File file) throws IOException {
		writeFile(texto, file, CHARSET_UTF_8);
	}

	/**
	 * Escribe un archivo de texto desde un String
	 * 
	 * @param texto
	 *            Texto a escribir
	 * @param path
	 *            Ruta del archivo
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-write-utf-8-encoded-data-into-a-file-
	 *      java/
	 */
	public static void writeFile(String texto, String path) throws IOException {
		writeFile(texto, new File(path), CHARSET_UTF_8);
	}

	/**
	 * Escribe un archivo de texto desde un String
	 * 
	 * @param texto
	 *            Texto a escribir
	 * @param path
	 *            Ruta del archivo
	 * @param charset
	 *            Tipo de codificacion
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-write-utf-8-encoded-data-into-a-file-
	 *      java/
	 */
	public static void writeFile(String texto, String path, String charset)
			throws IOException {
		writeFile(texto, new File(path), charset);
	}

	/**
	 * Escribe un archivo de texto desde un String
	 * 
	 * @param texto
	 *            Texto a escribir
	 * @param file
	 *            Archivo
	 * @param charset
	 *            Tipo de codificacion
	 * @throws IOException
	 * @see http 
	 *      ://www.mkyong.com/java/how-to-write-utf-8-encoded-data-into-a-file-
	 *      java/
	 */
	public static void writeFile(String texto, File file, String charset)
			throws IOException {
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), charset));
		out.write(texto);
		out.flush();
		out.close();
	}
}
