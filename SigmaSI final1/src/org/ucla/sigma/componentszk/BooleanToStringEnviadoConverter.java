package org.ucla.sigma.componentszk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class BooleanToStringEnviadoConverter implements TypeConverter  {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		return (Boolean) data?"ENVIADO":"NO ENVIADO";
	}
}
