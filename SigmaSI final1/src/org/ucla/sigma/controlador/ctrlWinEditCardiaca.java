package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Cardiaca;
import org.ucla.sigma.servicio.ServicioCardiaca;
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
public class ctrlWinEditCardiaca extends GenericForwardComposer {

	private Window winEditCardiaca;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioCardiaca servicioCardiaca;
	private ctrlWinCardiaca ctrlwincardiaca;

	// ----------------------------------------------------------------------------------------------------

	private Cardiaca cardiaca;

	// ----------------------------------------------------------------------------------------------------

	public ServicioCardiaca getServicioCardiaca() {
		return servicioCardiaca;
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

	public void setServicioCardiaca(ServicioCardiaca servicioCardiaca) {
		this.servicioCardiaca = servicioCardiaca;
	}

	public ctrlWinCardiaca getCtrlwincardiaca() {
		return ctrlwincardiaca;
	}

	public void setCtrlwincardiaca(ctrlWinCardiaca ctrlwincardiaca) {
		this.ctrlwincardiaca = ctrlwincardiaca;
	}

	public Window getWinEditCardiaca() {
		return winEditCardiaca;
	}

	public void setWinEditCardiaca(Window winEditCardiaca) {
		this.winEditCardiaca = winEditCardiaca;
	}

	public Cardiaca getCardiaca() {
		return cardiaca;
	}

	public void setCardiaca(Cardiaca cardiaca) {
		this.cardiaca = cardiaca;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditCardiaca.setAttribute(comp.getId() + "ctrl", this);
		servicioCardiaca = (ServicioCardiaca) SpringUtil
				.getBean("beanServicioCardiaca");
		cardiaca = new Cardiaca();
		ctrlwincardiaca = (ctrlWinCardiaca) arg.get("ctrlWinCardiaca");
		if (!(arg.get("objeto") == null)) {
			cardiaca = (Cardiaca) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwincardiaca.apagarBotones();
		ctrlwincardiaca.recargar();
		this.winEditCardiaca.detach();
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
			Cardiaca cardiacaTemp = servicioCardiaca.buscarUno(cardiaca
					.getNombre());
			if (cardiacaTemp != null) {
				cardiaca.setId(cardiacaTemp.getId());
			}

			try {
				servicioCardiaca.guardarCardiaca(cardiaca);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwincardiaca.recargar();
						ctrlwincardiaca.apagarBotones();
						winEditCardiaca.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwincardiaca.apagarBotones();
		this.winEditCardiaca.detach();
	}

}
