package org.ucla.sigma.componentszk;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.ucla.sigma.components.HelperImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * Redimensiona una imagen, Ejemplo zk:
 * <p>
 * <listcell imageContent=
 * "@{imagen.bytes,converter='org.ucla.sigma.componentszk.ImageConverter'}" />
 */
public class ImageConverter implements TypeConverter {
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object data, Component comp) {
		try {
			BufferedImage imagenNueva = HelperImage.resizeMaxHeight(
					HelperImage.byteArrayToBufferedImage((byte[]) data), 30);
			return imagenNueva;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
