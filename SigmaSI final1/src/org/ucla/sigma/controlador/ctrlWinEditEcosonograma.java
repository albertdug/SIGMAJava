package org.ucla.sigma.controlador;

import org.ucla.sigma.componentszk.MensajeEmergente;
import org.ucla.sigma.componentszk.MensajeListener;
import org.ucla.sigma.modelo.Ecosonograma;
import org.ucla.sigma.servicio.ServicioEcosonograma;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ctrlWinEditEcosonograma extends GenericForwardComposer {

	private Window winEditEcosonograma;
	private Button btnCancelar;
	private Button btnGuardar;
	private Textbox txtNombre;

	// ----------------------------------------------------------------------------------------------------

	private ServicioEcosonograma servicioEcosonograma;
	private ctrlWinEcosonograma ctrlwinecosonograma;

	// ----------------------------------------------------------------------------------------------------

	private Ecosonograma ecosonograma;

	// ----------------------------------------------------------------------------------------------------

	public ServicioEcosonograma getServicioEcosonograma() {
		return servicioEcosonograma;
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

	public void setServicioEcosonograma(
			ServicioEcosonograma servicioEcosonograma) {
		this.servicioEcosonograma = servicioEcosonograma;
	}

	public ctrlWinEcosonograma getCtrlwinecosonograma() {
		return ctrlwinecosonograma;
	}

	public void setCtrlwinecosonograma(ctrlWinEcosonograma ctrlwinecosonograma) {
		this.ctrlwinecosonograma = ctrlwinecosonograma;
	}

	public Window getWinEditEcosonograma() {
		return winEditEcosonograma;
	}

	public void setWinEditEcosonograma(Window winEditEcosonograma) {
		this.winEditEcosonograma = winEditEcosonograma;
	}

	public Ecosonograma getEcosonograma() {
		return ecosonograma;
	}

	public void setEcosonograma(Ecosonograma ecosonograma) {
		this.ecosonograma = ecosonograma;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		winEditEcosonograma.setAttribute(comp.getId() + "ctrl", this);
		servicioEcosonograma = (ServicioEcosonograma) SpringUtil
				.getBean("beanServicioEcosonograma");
		ecosonograma = new Ecosonograma();
		ctrlwinecosonograma = (ctrlWinEcosonograma) arg
				.get("ctrlWinEcosonograma");
		if (!(arg.get("objeto") == null)) {
			ecosonograma = (Ecosonograma) arg.get("objeto");
		}
	}

	public void onClick$btnCancelar() {
		ctrlwinecosonograma.recargar();
		ctrlwinecosonograma.apagarBotones();
		this.winEditEcosonograma.detach();
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
			Ecosonograma ecosonogramaTemp = servicioEcosonograma
					.buscarUno(ecosonograma.getNombre());
			if (ecosonogramaTemp != null) {
				ecosonograma.setId(ecosonogramaTemp.getId());
			}

			try {
				servicioEcosonograma.guardarEcosonograma(ecosonograma);
				MensajeEmergente.mostrar("REGINSERT", new MensajeListener() {
					@Override
					public void alAceptar() {
						ctrlwinecosonograma.recargar();
						ctrlwinecosonograma.apagarBotones();
						winEditEcosonograma.detach();
					}
				});
			} catch (Exception e) {
				MensajeEmergente.mostrar("ERRDB");
				e.printStackTrace();
			}
		}
	}

	public void onClose$winEditEcosonograma() {
		ctrlwinecosonograma.apagarBotones();
		this.winEditEcosonograma.detach();
	}
}
