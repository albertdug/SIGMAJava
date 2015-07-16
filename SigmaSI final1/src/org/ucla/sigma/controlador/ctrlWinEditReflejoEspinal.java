package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.modelo.ReflejoEspinal;
import org.ucla.sigma.servicio.ServicioReflejoEspinal;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditReflejoEspinal extends GenericForwardComposer {

	private Window winEditReflejoEspinal;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioReflejoEspinal servicioReflejoEspinal;
	private ctrlWinReflejoEspinal ctrlwinreflejoEspinal;

	// ----------------------------------------------------------------------------------------------------

	private ReflejoEspinal reflejoEspinal;

	// ----------------------------------------------------------------------------------------------------

	public ServicioReflejoEspinal getServicioReflejoEspinal() {
		return servicioReflejoEspinal;
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

	public void setServicioReflejoEspinal(ServicioReflejoEspinal servicioReflejoEspinal) {
		this.servicioReflejoEspinal = servicioReflejoEspinal;
	}

	public ctrlWinReflejoEspinal getCtrlwinReflejoEspinal() {
		return ctrlwinreflejoEspinal;
	}

	public void setCtrlwinreflejoEspinal(ctrlWinReflejoEspinal ctrlwinreflejoEspinal) {
		this.ctrlwinreflejoEspinal = ctrlwinreflejoEspinal;
	}

	public Window getWinEditReflejoEspinal() {
		return winEditReflejoEspinal;
	}

	public void setWinEditReflejoEspinal(Window winEditReflejoEspinal) {
		this.winEditReflejoEspinal = winEditReflejoEspinal;
	}

	public ReflejoEspinal getReflejoEspinal() {
		return reflejoEspinal;
	}

	public void setReflejoEspinal(ReflejoEspinal reflejoEspinal) {
		this.reflejoEspinal = reflejoEspinal;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditReflejoEspinal.setAttribute(comp.getId() + "ctrl", this);
		servicioReflejoEspinal = (ServicioReflejoEspinal) SpringUtil
				.getBean("beanServicioReflejoEspinal");
		reflejoEspinal = new ReflejoEspinal();
		ctrlwinreflejoEspinal = (ctrlWinReflejoEspinal) arg.get("ctrlWinReflejoEspinal");
		if (!(arg.get("objeto") == null)) {
			reflejoEspinal = (ReflejoEspinal) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinreflejoEspinal.apagarBotones();
		this.winEditReflejoEspinal.detach();
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
			ReflejoEspinal reflejoEspinalTemp = servicioReflejoEspinal
					.buscarUno(reflejoEspinal.getNombre());
			if (reflejoEspinalTemp != null) {
				reflejoEspinal.setId(reflejoEspinalTemp.getId());
			}

			try {
				servicioReflejoEspinal.guardarReflejoEspinal(reflejoEspinal);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinreflejoEspinal.recargar();
						ctrlwinreflejoEspinal.apagarBotones();
						winEditReflejoEspinal.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditReflejoEspinal() {
		ctrlwinreflejoEspinal.apagarBotones();
		this.winEditReflejoEspinal.detach();
	}
}
