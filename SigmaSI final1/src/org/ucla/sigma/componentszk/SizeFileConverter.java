package org.ucla.sigma.componentszk;

import org.ucla.sigma.components.HelperFile;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * Retorna el tamaño de un archivo, Ejemplo zk:
 * <p>
 * <listcell label=
 * "@{imagen.bytes,converter='org.ucla.sigma.componentszk.SizeFileConverter'}"
 * />
 */
public class SizeFileConverter implements TypeConverter {
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		return HelperFile.getSize((byte[]) data);
	}

}
