/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.servicio.ServicioImagen;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author rafael
 * 
 */
public class ctrlWinEditImagen extends GenericForwardComposer {

	private Window winEditImagen;
	private Button btnCancelar;
	private Button btnGuardar;
	private Button upImagen;
	private Textbox txtNombre;
	private Datebox dbCreacion;
	private Image imgImagen;
	private AImage tmpImg;
	// ----------------------------------------------------------------------------------------------------

	private ServicioImagen servicioImagen;
	private ctrlWinImagen ctrlwinimagen;

	// ----------------------------------------------------------------------------------------------------

	private Imagen imagen;

	// ----------------------------------------------------------------------------------------------------

	public Window getWinEditImagen() {
		return winEditImagen;
	}

	public AImage getTmpImg() {
		return tmpImg;
	}

	public void setTmpImg(AImage tmpImg) {
		this.tmpImg = tmpImg;
	}

	public void setWinEditImagen(Window winEditImagen) {
		this.winEditImagen = winEditImagen;
	}

	public Button getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(Button btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(Button btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public Button getUpImagen() {
		return upImagen;
	}

	public void setUpImagen(Button upImagen) {
		this.upImagen = upImagen;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public Datebox getDbCreacion() {
		return dbCreacion;
	}

	public void setDbCreacion(Datebox dbCreacion) {
		this.dbCreacion = dbCreacion;
	}

	public ServicioImagen getServicioImagen() {
		return servicioImagen;
	}

	public void setServicioImagen(ServicioImagen servicioImagen) {
		this.servicioImagen = servicioImagen;
	}

	public ctrlWinImagen getCtrlwinimagen() {
		return ctrlwinimagen;
	}

	public void setCtrlwinimagen(ctrlWinImagen ctrlwinimagen) {
		this.ctrlwinimagen = ctrlwinimagen;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Image getImgImagen() {
		return imgImagen;
	}

	public void setImgImagen(Image imgImagen) {
		this.imgImagen = imgImagen;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditImagen.setAttribute(comp.getId() + "ctrl", this);
		servicioImagen = (ServicioImagen) SpringUtil
				.getBean("beanServicioImagen");
		imagen = new Imagen();
		imagen.setCreacion(HelperDate.now());
		ctrlwinimagen = (ctrlWinImagen) arg.get("ctrlWinImagen");
		if (!(arg.get("objeto") == null)) {
			imagen = (Imagen) arg.get("objeto");
			tmpImg = new AImage(null, imagen.getBytes());
			if (tmpImg.getHeight() > 300) {
				escalarimgImagen(tmpImg);
			}
			imgImagen.setContent(tmpImg);
		}
		txtNombre.setFocus(true);
	}

	public void onClick$btnCancelar() {
		ctrlwinimagen.recargar();
		ctrlwinimagen.apagarBotones();
		this.winEditImagen.detach();
	}

	public void onClick$btnGuardar() {

		if (HelperString.isEmpty(txtNombre.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else if (imagen.getBytes() == null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nImagen");
		} else {
			Imagen imagenTemp = servicioImagen.buscarUno(imagen.getNombre());
			if (imagenTemp != null) {
				imagen.setId(imagenTemp.getId());
			}

			try {
				imagen.setCreacion(dbCreacion.getValue());
				servicioImagen.guardarImagen(imagen);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinimagen.recargar();
						ctrlwinimagen.apagarBotones();
						winEditImagen.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditImagen() {
		ctrlwinimagen.apagarBotones();
		this.winEditImagen.detach();
	}

	public void escalarimgImagen(org.zkoss.image.Image media) {
		imgImagen.setHeight("300px");
		int w = (int) (media.getWidth() * (300 * 100 / media.getHeight()) / 100);
		imgImagen.setWidth(w + "px");
	}

	public void subir(Event event) {
		org.zkoss.util.media.Media media = ((org.zkoss.zk.ui.event.UploadEvent) event)
				.getMedia();
		if (media instanceof org.zkoss.image.Image) {
			imagen.setBytes(((org.zkoss.image.Image) media).getByteData());
			imagen.setExtension(((org.zkoss.image.Image) media).getFormat());
			if (((org.zkoss.image.Image) media).getHeight() > 300) {
				escalarimgImagen((org.zkoss.image.Image) media);
			}else{
				imgImagen.setWidth("auto");
				imgImagen.setHeight("auto");
			}
			imgImagen.setContent((org.zkoss.image.Image) media);
		} else {
			MensajeEmergente.mostrar("ERRFORMAT");
		}
	}
}
