package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Auscultacion;
import org.ucla.sigma.servicio.ServicioAuscultacion;
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
public class ctrlWinEditAuscultacion extends GenericForwardComposer {

	private Window winEditAuscultacion;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioAuscultacion servicioAuscultacion;
	private ctrlWinAuscultacion ctrlwinauscultacion;

	// ----------------------------------------------------------------------------------------------------

	private Auscultacion auscultacion;

	// ----------------------------------------------------------------------------------------------------

	public ServicioAuscultacion getServicioAuscultacion() {
		return servicioAuscultacion;
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

	public void setServicioAuscultacion(
			ServicioAuscultacion servicioAuscultacion) {
		this.servicioAuscultacion = servicioAuscultacion;
	}

	public ctrlWinAuscultacion getCtrlwinauscultacion() {
		return ctrlwinauscultacion;
	}

	public void setCtrlwinauscultacion(ctrlWinAuscultacion ctrlwinauscultacion) {
		this.ctrlwinauscultacion = ctrlwinauscultacion;
	}

	public Window getWinEditAuscultacion() {
		return winEditAuscultacion;
	}

	public void setWinEditAuscultacion(Window winEditAuscultacion) {
		this.winEditAuscultacion = winEditAuscultacion;
	}

	public Auscultacion getAuscultacion() {
		return auscultacion;
	}

	public void setAuscultacion(Auscultacion auscultacion) {
		this.auscultacion = auscultacion;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditAuscultacion.setAttribute(comp.getId() + "ctrl", this);
		servicioAuscultacion = (ServicioAuscultacion) SpringUtil
				.getBean("beanServicioAuscultacion");
		auscultacion = new Auscultacion();
		ctrlwinauscultacion = (ctrlWinAuscultacion) arg
				.get("ctrlWinAuscultacion");
		if (!(arg.get("objeto") == null)) {
			auscultacion = (Auscultacion) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinauscultacion.apagarBotones();
		ctrlwinauscultacion.recargar();
		this.winEditAuscultacion.detach();
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
			Auscultacion auscultacionTemp = servicioAuscultacion
					.buscarUno(auscultacion.getNombre());
			if (auscultacionTemp != null) {
				auscultacion.setId(auscultacionTemp.getId());
			}

			try {
				servicioAuscultacion.guardarAuscultacion(auscultacion);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinauscultacion.recargar();
						ctrlwinauscultacion.apagarBotones();
						winEditAuscultacion.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}

		}

	}

	public void onClose$winEditEspecie() {
		ctrlwinauscultacion.apagarBotones();
		this.winEditAuscultacion.detach();
	}

}
