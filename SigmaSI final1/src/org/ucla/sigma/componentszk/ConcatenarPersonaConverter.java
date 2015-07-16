package org.ucla.sigma.componentszk;

import java.util.Date;
import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.modelo.Persona;
import org.ucla.sigma.modelo.Responsable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * Convierte en fecha en el formato estandar, Ejemplo zk:
 * <p>
 * <listcell label=
 * "@{imagen.creacion,converter='org.ucla.sigma.componentszk.FormatDateConverter'}"
 * />
 */
public class ConcatenarPersonaConverter implements TypeConverter {
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		Persona persona = (Persona) data;
		return persona.getNombre()+" "+persona.getApellido();
	}

}
