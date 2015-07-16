package org.ucla.sigma.componentszk;

import java.util.Date;
import org.ucla.sigma.components.HelperDate;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * Convierte en fecha en el formato estandar, Ejemplo zk:
 * <p>
 * <listcell label=
 * "@{imagen.creacion,converter='org.ucla.sigma.componentszk.FormatDateConverter'}"
 * />
 */
public class FormatHourConverter implements TypeConverter {
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		return HelperDate.format((Date) data, HelperDate.FORMATO_HORA);
	}

}
