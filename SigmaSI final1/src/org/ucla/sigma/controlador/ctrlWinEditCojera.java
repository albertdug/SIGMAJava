package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.Cojera;
import org.ucla.sigma.servicio.ServicioCojera;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditCojera extends GenericForwardComposer {

	private Window winEditCojera;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioCojera servicioCojera;
	private ctrlWinCojera ctrlwincojera;

	// ----------------------------------------------------------------------------------------------------

	private Cojera cojera;

	// ----------------------------------------------------------------------------------------------------

	public ServicioCojera getServicioCojera() {
		return servicioCojera;
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

	public void setServicioCojera(ServicioCojera servicioCojera) {
		this.servicioCojera = servicioCojera;
	}

	public ctrlWinCojera getCtrlwincojera() {
		return ctrlwincojera;
	}

	public void setCtrlwincojera(ctrlWinCojera ctrlwincojera) {
		this.ctrlwincojera = ctrlwincojera;
	}

	public Window getWinEditCojera() {
		return winEditCojera;
	}

	public void setWinEditCojera(Window winEditCojera) {
		this.winEditCojera = winEditCojera;
	}

	public Cojera getCojera() {
		return cojera;
	}

	public void setCojera(Cojera cojera) {
		this.cojera = cojera;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditCojera.setAttribute(comp.getId() + "ctrl", this);
		servicioCojera = (ServicioCojera) SpringUtil
				.getBean("beanServicioCojera");
		cojera = new Cojera();
		ctrlwincojera = (ctrlWinCojera) arg.get("ctrlWinCojera");
		if (!(arg.get("objeto") == null)) {
			cojera = (Cojera) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwincojera.apagarBotones();
		this.winEditCojera.detach();
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
			Cojera cojeraTemp = servicioCojera
					.buscarUno(cojera.getNombre());
			if (cojeraTemp != null) {
				cojera.setId(cojeraTemp.getId());
			}

			try {
				servicioCojera.guardarCojera(cojera);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwincojera.recargar();
						ctrlwincojera.apagarBotones();
						winEditCojera.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditCojera() {
		ctrlwincojera.apagarBotones();
		this.winEditCojera.detach();
	}
}
