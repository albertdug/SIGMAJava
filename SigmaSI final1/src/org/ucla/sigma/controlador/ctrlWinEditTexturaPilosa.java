/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TexturaPilosa;
import org.ucla.sigma.servicio.ServicioTexturaPilosa;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author jhoan
 *
 */
public class ctrlWinEditTexturaPilosa extends GenericForwardComposer {

	private Window winEditTexturaPilosa;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioTexturaPilosa servicioTexturaPilosa;
	private ctrlWinTexturaPilosa ctrlwintexturapilosa;
	private TexturaPilosa texturapilosa;

	// ----------------------------------------------------------------------------------------------------
	
	public Window getWinEditTexturaPilosa() {
		return winEditTexturaPilosa;
	}

	public void setWinEditTexturaPilosa(Window winEditTexturaPilosa) {
		this.winEditTexturaPilosa = winEditTexturaPilosa;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioTexturaPilosa getServicioTexturaPilosa() {
		return servicioTexturaPilosa;
	}

	public void setServicioTexturaPilosa(ServicioTexturaPilosa servicioTexturaPilosa) {
		this.servicioTexturaPilosa = servicioTexturaPilosa;
	}

	public ctrlWinTexturaPilosa getCtrlwintexturapilosa() {
		return ctrlwintexturapilosa;
	}

	public void setCtrlwintexturapilosa(ctrlWinTexturaPilosa ctrlwintexturapilosa) {
		this.ctrlwintexturapilosa = ctrlwintexturapilosa;
	}

	public TexturaPilosa getTexturapilosa() {
		return texturapilosa;
	}

	public void setTexturapilosa(TexturaPilosa texturapilosa) {
		this.texturapilosa = texturapilosa;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTexturaPilosa.setAttribute(comp.getId() + "ctrl", this);
		servicioTexturaPilosa = (ServicioTexturaPilosa) SpringUtil
				.getBean("beanServicioTexturaPilosa");
		texturapilosa = new TexturaPilosa();
		ctrlwintexturapilosa = (ctrlWinTexturaPilosa) arg.get("ctrlWinTexturaPilosa");
		if (!(arg.get("objeto") == null)) {
			texturapilosa = (TexturaPilosa) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintexturapilosa.recargar();
		ctrlwintexturapilosa.apagarBotones();
		this.winEditTexturaPilosa.detach();
	}

	public void onClick$btnGuardar() {

		if (txtNombre.getValue().trim().equalsIgnoreCase("")) {
			MensajeEmergente.mostrar("NOEMPTY", "\nNombre",
					new MensajeListener() {
						@Override
						public void alDestruir() {
							txtNombre.setFocus(true);
						}
					});
		} else {
			TexturaPilosa texturapilosaTemp = servicioTexturaPilosa.buscarUno(texturapilosa.getNombre());
			if (texturapilosaTemp != null) {
				texturapilosa.setId(texturapilosaTemp.getId());
			}

			try {
				servicioTexturaPilosa.guardarTexturaPilosa(texturapilosa);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintexturapilosa.recargar();
						ctrlwintexturapilosa.apagarBotones();
						winEditTexturaPilosa.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTexturaPilosa() {
		ctrlwintexturapilosa.apagarBotones();
		this.winEditTexturaPilosa.detach();
	}



}
