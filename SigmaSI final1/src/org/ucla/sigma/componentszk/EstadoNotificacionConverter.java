package org.ucla.sigma.componentszk;

import org.ucla.sigma.modelo.Notificacion;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * Convierte un boolean en ACTIVO y NO ACTIVO, Ejemplo zk:
 * <p>
 * <listcell label=
 * "@{publicidad.activo,converter='org.ucla.sigma.componentszk.BooleanToStringConverter'}"
 * />
 */
public class EstadoNotificacionConverter implements TypeConverter {
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		String a = "/sigma/imagenes/botones-basicos/email.png";
		String b = "/sigma/imagenes/botones-basicos/email-abierto.png";
		String c = "/sigma/imagenes/botones-basicos/respuesta-email.png";

		Notificacion notificacion = (Notificacion) data;

		if (notificacion.getRespuestas().isEmpty()) {
			if (notificacion.getEstatus() == 'A')
				return a;
			else
				return b;
		}

		return c;
	}

}
