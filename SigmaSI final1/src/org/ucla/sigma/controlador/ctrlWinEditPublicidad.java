/**
 * 
 */
package org.ucla.sigma.controlador;

import java.util.HashMap;
import java.util.Map;

import org.ucla.sigma.components.HelperDate;
import org.ucla.sigma.components.HelperString;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.icontrolador.IUsarCatalogoImagen;
import org.ucla.sigma.modelo.Ciudad;
import org.ucla.sigma.modelo.Imagen;
import org.ucla.sigma.modelo.Publicidad;
import org.ucla.sigma.servicio.ServicioPublicidad;
import org.zkoss.image.AImage;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditPublicidad extends GenericForwardComposer implements
		IUsarCatalogoImagen {

	private Window winEditPublicidad;
	private Button btnCancelar;
	private Button btnGuardar;
	private Image imgImagen;
	private Button btnImg;
	private Textbox txtEnlace;
	private Textbox txtDescripcion;
	private Textbox txtTitulo;
	private Datebox dbExpiracion;
	private Checkbox cbEstado;

	private Imagen imagen;
	private AImage tmpImg;
	private Publicidad publicidad;

	private String catalogoImagen = "/sigma/vistas/portal/imagen/catalogoImagen.zul";
	private ServicioPublicidad servicioPublicidad;
	private ctrlWinPublicidad ctrlwinPublicidad;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditPublicidad.setAttribute(comp.getId() + "ctrl", this);
		servicioPublicidad = (ServicioPublicidad) SpringUtil
				.getBean("beanServicioPublicidad");

		publicidad = new Publicidad();
		publicidad.setCreacion(HelperDate.now());
		imagen = new Imagen();

		ctrlwinPublicidad = (ctrlWinPublicidad) arg.get("ctrlWinPublicidad");

		if (!(arg.get("objeto") == null)) {
			publicidad = (Publicidad) arg.get("objeto");
			cbEstado.setChecked(publicidad.isActivo());
			// NUEVA LINEA
			if (publicidad.getImagen() != null) {
				tmpImg = new AImage(null, publicidad.getImagen().getBytes());
				imgImagen.setContent(tmpImg);
			}
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinPublicidad.recargar();
		ctrlwinPublicidad.apagarBotones();
		this.winEditPublicidad.detach();
	}

	public void onClose$winEditPublicidad() {
		ctrlwinPublicidad.apagarBotones();
		this.winEditPublicidad.detach();
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Publicidad getPublicidad() {
		return publicidad;
	}

	public void setPublicidad(Publicidad publicidad) {
		this.publicidad = publicidad;
	}

	public void onClick$btnImg() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("beforeCtrl", this);
		Window win = (Window) Executions.createComponents(catalogoImagen, null,
				parametros);
		win.doHighlighted();
	}

	@Override
	public void setImagenToModel(Imagen imagen) {
		publicidad.setImagen(imagen);
	}

	@Override
	public void setAImageToImageContent(AImage aImage) {
		imgImagen.setContent(aImage);
	}

	@Override
	public Image getTagImage() {
		return imgImagen;
	}

	public void onClick$btnGuardar() {

		if (HelperString.isEmpty(txtTitulo.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nTitulo",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtTitulo.setFocus(true);
						}
					});
		} else if (HelperString.isEmpty(txtDescripcion.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nDescripcion",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtDescripcion.setFocus(true);
						}
					});
		} else if (HelperString.isEmpty(txtEnlace.getValue())) {
			MensajeEmergente.mostrar("NOEMPTY", "\nEnlace",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtEnlace.setFocus(true);
						}
					});
		} else if (dbExpiracion.getValue()==null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nFecha de expiración",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							dbExpiracion.setFocus(true);
						}
					});
		} else if (publicidad.getImagen()==null) {
			MensajeEmergente.mostrar("NOEMPTY", "\nImagen",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							btnImg.setFocus(true);
						}
					});
		} else {

			try {
				Publicidad temp = servicioPublicidad.buscarUno(publicidad
						.getTitulo());
				if (temp != null) {
					publicidad.setId(temp.getId());
				}
				publicidad.setActivo(cbEstado.isChecked());
				servicioPublicidad.guardarPublicidad(publicidad);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinPublicidad.recargar();
						ctrlwinPublicidad.apagarBotones();
						winEditPublicidad.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}
	}

}
