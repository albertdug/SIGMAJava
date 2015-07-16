/**
 * 
 */
package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.TexturaPiel;
import org.ucla.sigma.servicio.ServicioTexturaPiel;
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
public class ctrlWinEditTexturaPiel extends GenericForwardComposer {

	private Window winEditTexturaPiel;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioTexturaPiel servicioTexturaPiel;
	private ctrlWinTexturaPiel ctrlwintexturapiel;
	private TexturaPiel texturapiel;
	
	// ----------------------------------------------------------------------------------------------------

	
	public Window getWinEditTexturaPiel() {
		return winEditTexturaPiel;
	}

	public void setWinEditTexturaPiel(Window winEditTexturaPiel) {
		this.winEditTexturaPiel = winEditTexturaPiel;
	}

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public ServicioTexturaPiel getServicioTexturaPiel() {
		return servicioTexturaPiel;
	}

	public void setServicioTexturaPiel(ServicioTexturaPiel servicioTexturaPiel) {
		this.servicioTexturaPiel = servicioTexturaPiel;
	}

	public ctrlWinTexturaPiel getCtrlwintexturapiel() {
		return ctrlwintexturapiel;
	}

	public void setCtrlwintexturapiel(ctrlWinTexturaPiel ctrlwintexturapiel) {
		this.ctrlwintexturapiel = ctrlwintexturapiel;
	}

	public TexturaPiel getTexturapiel() {
		return texturapiel;
	}

	public void setTexturapiel(TexturaPiel texturapiel) {
		this.texturapiel = texturapiel;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditTexturaPiel.setAttribute(comp.getId() + "ctrl", this);
		servicioTexturaPiel = (ServicioTexturaPiel) SpringUtil
				.getBean("beanServicioTexturaPiel");
		texturapiel = new TexturaPiel();
		ctrlwintexturapiel = (ctrlWinTexturaPiel) arg.get("ctrlWinTexturaPiel");
		if (!(arg.get("objeto") == null)) {
			texturapiel = (TexturaPiel) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwintexturapiel.recargar();
		ctrlwintexturapiel.apagarBotones();
		this.winEditTexturaPiel.detach();
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
			TexturaPiel texturapielTemp = servicioTexturaPiel.buscarUno(texturapiel.getNombre());
			if (texturapielTemp != null) {
				texturapiel.setId(texturapielTemp.getId());
			}

			try {
				servicioTexturaPiel.guardarTexturaPiel(texturapiel);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwintexturapiel.recargar();
						ctrlwintexturapiel.apagarBotones();
						winEditTexturaPiel.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditTexturaPiel() {
		ctrlwintexturapiel.apagarBotones();
		this.winEditTexturaPiel.detach();
	}



}
