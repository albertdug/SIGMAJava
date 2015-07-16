package org.ucla.sigma.icontrolador;

import org.ucla.sigma.modelo.Imagen;
import org.zkoss.image.AImage;
import org.zkoss.zul.Image;

public interface IUsarCatalogoImagen {

	public void setImagenToModel(Imagen imagen);
	public void setAImageToImageContent(AImage aImage);
	public Image getTagImage();
	
}
