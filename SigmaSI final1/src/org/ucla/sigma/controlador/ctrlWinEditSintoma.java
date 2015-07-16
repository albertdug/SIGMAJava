package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Sintoma;
import org.ucla.sigma.servicio.ServicioSintoma;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * @author Albert
 * 
 */
public class ctrlWinEditSintoma extends GenericForwardComposer {

	private Window winEditSintoma;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioSintoma servicioSintoma;
	private ctrlWinSintoma ctrlwinsintoma;

	// ----------------------------------------------------------------------------------------------------

	private Sintoma sintoma;

	// ----------------------------------------------------------------------------------------------------

	public ServicioSintoma getServicioSintoma() {
		return servicioSintoma;
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

	public Textbox getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(Textbox txtNombre) {
		this.txtNombre = txtNombre;
	}

	public void setServicioSintoma(ServicioSintoma servicioSintoma) {
		this.servicioSintoma = servicioSintoma;
	}

	public ctrlWinSintoma getCtrlwinsintoma() {
		return ctrlwinsintoma;
	}

	public void setCtrlwinsintoma(ctrlWinSintoma ctrlwinsintoma) {
		this.ctrlwinsintoma = ctrlwinsintoma;
	}

	public Window getWinEditSintoma() {
		return winEditSintoma;
	}

	public void setWinEditSintoma(Window winEditSintoma) {
		this.winEditSintoma = winEditSintoma;
	}

	public Sintoma getSintoma() {
		return sintoma;
	}

	public void setSintoma(Sintoma sintoma) {
		this.sintoma = sintoma;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditSintoma.setAttribute(comp.getId() + "ctrl", this);
		servicioSintoma = (ServicioSintoma) SpringUtil
				.getBean("beanServicioSintoma");
		sintoma = new Sintoma();
		ctrlwinsintoma = (ctrlWinSintoma) arg.get("ctrlWinSintoma");
		if (!(arg.get("objeto") == null)) {
			sintoma = (Sintoma) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinsintoma.apagarBotones();
		this.winEditSintoma.detach();
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
			Sintoma sintomaTemp = servicioSintoma
					.buscarUno(sintoma.getNombre());
			if (sintomaTemp != null) {
				sintoma.setId(sintomaTemp.getId());
			}

			try {
				servicioSintoma.guardarSintoma(sintoma);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinsintoma.recargar();
						ctrlwinsintoma.apagarBotones();
						winEditSintoma.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinsintoma.apagarBotones();
		this.winEditSintoma.detach();
	}

}
