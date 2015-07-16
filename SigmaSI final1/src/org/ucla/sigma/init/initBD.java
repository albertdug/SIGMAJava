package org.ucla.sigma.init;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperFile;
import org.ucla.sigma.components.HelperFileRW;
import org.ucla.sigma.components.HelperFileStream;
import org.ucla.sigma.components.HelperImage;
import org.ucla.sigma.modelo.Adjunto;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioAdjunto;
import org.ucla.sigma.servicio.ServicioImagen;
import org.ucla.sigma.servicio.ServicioNoModel;

/**
 * Ejecutar para inicializar la base de datos, colocar el nombre de la ultima
 * base de datos
 */
public class initBD {

	public static void main(String[] args)  {
		System.out.println("*** Iniciando ***");
		//ELIMINANDO BD SI EXISTE POSTGRES DEBE ESTAR CERRADO
		System.out.println("*** Eliminando base de datos previa, NO deben existir clientes conectados, ej: pgadmin, SIGMA ***");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "1234");
			Statement st = connection.createStatement();
			//SI NO SE DESEA BORRAR COMENTAR LA LINEA SIGUIENTE A ESTA
			st.executeUpdate("DROP DATABASE IF EXISTS sigmabd; CREATE DATABASE sigmabd;");
			//
			connection.close();
		} catch (SQLException e1) {
			System.out.println("Error en conexion para eliminar sigmabd");
			e1.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			System.out.println("Error en conseguir driver para eliminar sigmabd");
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("*** sigmabd creada ***");
		
		// CONTEXT
		// --------------------------------------------------------------------------------
		System.out.println("Iniciando ApplicationContext");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		// VARIABLES
		// --------------------------------------------------------------------------------
		System.out.println("Iniciando variables");
		String ruta = "./docs/bd/SigmaBD 15-07-2012 02.05pm/";

		// GUARDAR IMAGENES
		// --------------------------------------------------------------------------------
		System.out.println("Iniciando guardar imagenes");
		File dir = new File(ruta + "IMAGENES/");
		File[] archivos = dir.listFiles();
		ServicioImagen servicioImagen = ctx.getBean(ServicioImagen.class);

		System.out.println("Ruta a la que se quiere acceder: "
				+ dir.getAbsolutePath());
		if (archivos == null) {
			System.out
					.println("*** No hay ficheros en el directorio especificado ***");
			System.exit(0);
		} else {
			System.out.println("Cantidad imagenes: " + archivos.length);
			for (int x = 0; x < archivos.length; x++) {

				if (HelperFile.isFileType(archivos[x], "png", "jpg", "gif","jpeg")) {
					System.out.println("\t" + archivos[x].getPath());
					try {
						Imagen tempImg = new Imagen();
						tempImg.setBytes(HelperImage.bufferedImageToByteArray(
								HelperImage.readImage(archivos[x]),
								HelperFile.getExtension(archivos[x])));
						tempImg.setNombre(HelperFile.getOnlyName(archivos[x]));
						tempImg.setCreacion(HelperDate.now());
						tempImg.setExtension(HelperFile
								.getExtension(archivos[x]));
						servicioImagen.guardarImagen(tempImg);
					} catch (Exception ex) {
						System.out.println("No se pudo leer la imagen");
					}
				}
			}
		}

		// GUARDAR IMAGENES
		// --------------------------------------------------------------------------------
		System.out.println("Iniciando guardar adjuntos");
		File dirAd = new File(ruta + "ADJUNTOS/");
		File[] archivosAd = dirAd.listFiles();
		ServicioAdjunto servicioAdjunto = ctx.getBean(ServicioAdjunto.class);

		System.out.println("Ruta a la que se quiere acceder: "
				+ dirAd.getAbsolutePath());
		if (archivos == null) {
			System.out
					.println("*** No hay archivos para adjuntar en el directorio especificado ***");
			System.exit(0);
		} else {
			System.out.println("Cantidad de archivos: " + archivosAd.length);
			for (int x = 0; x < archivosAd.length; x++) {
				System.out.println("\t" + archivosAd[x].getPath());
				try {
					Adjunto tempAdj = new Adjunto();
					tempAdj.setBytes(HelperFileStream.readFile(archivosAd[x]));
					tempAdj.setNombre(HelperFile.getOnlyName(archivosAd[x]));
					tempAdj.setCreacion(HelperDate.now());
					tempAdj.setExtension(HelperFile.getExtension(archivosAd[x]));
					servicioAdjunto.guardarAdjunto(tempAdj);
				} catch (Exception ex) {
					System.out.println("No se pudo leer archivo para adjuntar");
				}

			}
		}

		// LEER SCRIPT SQL UTF-8, EL ARCHIVO DEBE ESTAR ESCRITO EN UTF-8 SIN BOM
		// --------------------------------------------------------------------------------
		System.out.println("Iniciando leer script sql");
		String archivoSQL = "SQL/SIGMA BD DATOS INICIALES.sql";
		String scriptSQL = "";
		try {
			scriptSQL = HelperFileRW.readFile(ruta + archivoSQL);
		} catch (Exception e) {
			System.out.println("*** Error al leer script sql ***");
			e.printStackTrace();
			System.exit(0);
		}

		// INICIALIZAR BD
		// --------------------------------------------------------------------------------
		System.out.println("Iniciando datos sql");
		ServicioNoModel init = ctx.getBean(ServicioNoModel.class);
		try {
			init.ejecutarSQL(scriptSQL);
		} catch (HibernateException e) {
			System.out.println("*** Error al ejecutar sql inicial ***");
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("*** Se ejecuto satisfactoriamente ***");
		System.exit(0);
	}
}
