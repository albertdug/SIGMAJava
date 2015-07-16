package org.ucla.sigma.componentszk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * Convierte un boolean en ACTIVO y NO ACTIVO, Ejemplo zk:
 * <p>
 * <listcell label=
 * "@{publicidad.activo,converter='org.ucla.sigma.componentszk.BooleanToStringConverter'}"
 * />
 */
public class BooleanToStringConverter implements TypeConverter {
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		return (Boolean) data?"ACTIVO":"NO ACTIVO";
	}

}
