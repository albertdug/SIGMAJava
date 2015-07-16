package org.ucla.sigma.components;

import java.util.Calendar;
import java.util.Date;

/**
 * Clase para el calculo de la edad
 */
public abstract class HelperDateAge {
	private static String amy;
	private static String month = "Month";
	private static String months = "Months";
	private static String year = "Year";
	private static String years = "Years";
	private static String mes = "Mes";
	private static String meses = "Meses";
	private static String anno = "Año";
	private static String annos = "Años";
	public static final char ENG = 'E';
	public static final char SPA = 'S';
	
	/**
	 * Esta funcion calcula la edad en base a la fecha actual
	 * 
	 * @param birth
	 *            Fecha de nacimiento
	 * @param language
	 *            Idioma del String tetornado
	 * @return Edad como String en meses y annos
	 */
	public static String age(Date birth, char language) {
		String temp1 = "";
		String temp2 = "";
		String temp3 = "";
		String temp4 = "";	
		if (language == ' ' || language == 'S'){
			temp1 = mes;
			temp2 = meses;
			temp3 = anno;
			temp4 = annos;	
		} else
			if (language == 'E'){
				temp1 = month;
				temp2 = months;
				temp3 = year;
				temp4 = years;	
			}
		float lapse = (Calendar.getInstance().getTimeInMillis() - birth.getTime());
		lapse = (((((lapse / 1000) / 60) / 60) / 24) / 365);
		int auxYear = (int)(lapse);
		int auxMonth = (int)(lapse * 12);
		if (auxMonth < 11){
			if (auxMonth == 1){
				return amy = String.valueOf(auxMonth) + " " + temp1;
			} else {
				return amy = String.valueOf(auxMonth) + " " + temp2;
			}
		} else {
			if (auxYear == 1){
				return amy = String.valueOf(auxYear) + " " + temp3;
			} else {
				return amy = String.valueOf(auxYear) + " " + temp4;
			}
		}
	}	
	
	/**
	 * Esta funcion calcula la edad en base a fecha inicial y final
	 * 
	 * @param birth
	 *            Fecha de nacimiento
	 * @param death
	 *            Fecha de muerte
	 * @param language
	 *            Idioma del String tetornado (S: Espannol - E:Ingles)
	 * @return Edad como String en meses y annos
	 */
	public static String age(Date birth, Date death, char language) {
		String temp1 = "";
		String temp2 = "";
		String temp3 = "";
		String temp4 = "";	
		if (language == ' ' || language == 'S'){
			temp1 = mes;
			temp2 = meses;
			temp3 = anno;
			temp4 = annos;	
		} else
			if (language == 'E'){
				temp1 = month;
				temp2 = months;
				temp3 = year;
				temp4 = years;	
			}
		float lapse = (death.getTime() - birth.getTime());
		lapse = (((((lapse / 1000) / 60) / 60) / 24) / 365);
		int auxYear = (int)(lapse);
		int auxMonth = (int)(lapse * 12);
		if (auxMonth < 11){
			if (auxMonth == 1){
				return amy = String.valueOf(auxMonth) + " " + temp1;
			} else {
				return amy = String.valueOf(auxMonth) + " " + temp2;
			}
		} else {
			if (auxYear == 1){
				return amy = String.valueOf(auxYear) + " " + temp3;
			} else {
				return amy = String.valueOf(auxYear) + " " + temp4;
			}
		}
	}	
	
	/**
	 * Esta funcion calcula los meses en base a fecha inicial y final
	 * 
	 * @param birth
	 *            Fecha de nacimiento
	 * @param death
	 *            Fecha de muerte
	 * @return Meses como Int
	 */
	public static int ageMonth(Date birth, Date death) {
		float lapse = (death.getTime() - birth.getTime());
		lapse = (((((lapse / 1000) / 60) / 60) / 24) / 365);
		int auxMonth = (int)(lapse * 12);
		return auxMonth;
	}	
	
	/**
	 * Esta funcion calcula los annos en base a fecha inicial y final
	 * 
	 * @param birth
	 *            Fecha de nacimiento
	 * @param death
	 *            Fecha de muerte
	 * @return Annos como Int
	 */
	public static int ageYear(Date birth, Date death) {
		float lapse = (death.getTime() - birth.getTime());
		lapse = (((((lapse / 1000) / 60) / 60) / 24) / 365);
		int auxYear = (int)(lapse);
		return auxYear;
	}	
}
